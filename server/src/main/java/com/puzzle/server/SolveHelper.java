package com.puzzle.server;

import com.puzzle.utils.entities.Piece;
import com.puzzle.utils.entities.Shape;

import javax.swing.text.html.parser.Parser;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class SolveHelper {
    private List<Piece> pieces;
    private Map<Shape, List<Integer>> existingShapesToIds;
    private Shape[] resultEnlarged;

    public SolveHelper(List<Piece> pieces) {
        this.pieces = pieces;
        existingShapesToIds = pieces.stream().collect(groupingBy(Piece::getShape, mapping(Piece::getId, toList())));
    }

    private Map<String, Map<Shape, Long>> createIndexOfShapesByLeftTopSides(){
        Map<String, Map<Shape, Long>> indexOfPieces = new HashMap<>();

        indexOfPieces = pieces.stream()
                .collect(Collectors.groupingBy(Piece::getLeftTopKey,
                        Collectors.groupingBy(Piece::getShape, Collectors.counting())));
        return indexOfPieces;
    }

    public Shape[] createPuzzleEnlargedByStraitPieces(int numOfLines) {

        int rows = numOfLines + 1;
        int columns = pieces.size() / numOfLines + 1;
        Shape[] bigPuzzle = new Shape[rows * columns];

        Shape allSidesStraits = new Shape(new int[]{0, 0, 0, 0});

        for(int i =0; i<rows*columns; i++){
            if(i<columns || i%columns==0){
                bigPuzzle[i] = allSidesStraits;
            }
        }
        return bigPuzzle;
    }

    public boolean tryToSolvePuzzleInShapes(int numOfLines) {

        resultEnlarged = createPuzzleEnlargedByStraitPieces(numOfLines);

        int newNumOfLines = numOfLines+1;
        int bigPuzzleSize = resultEnlarged.length;
        int col = bigPuzzleSize / newNumOfLines;

        //This map will be updated during finding solution process;
        //Taken Shape will be removed (while step forward) / added (while step backward)
        Map<String, Map<Shape, Long>> indexOfShapes = createIndexOfShapesByLeftTopSides();

        //Map of matching for place Shapes - will be used during "step backward" in case there is no possible option to continue
        Map<Integer, List<Shape>> placeToMatchingShapes = new HashMap<>();

        //Starting place is a second piece in the second line (since puzzle was enlarged by strait pieces)
        int place = col + 1;

        //First list of matching Shape
        List<Shape> matchingForCurrent = findMatchingShape(place, col, resultEnlarged, indexOfShapes);

        Shape currentShape = null;
        while (place <= bigPuzzleSize -1 || isNoAnyMatches(indexOfShapes)) {
            while (place <= bigPuzzleSize -1 && !matchingForCurrent.isEmpty()) {

                if(!matchingForCurrent.isEmpty()) {
                    currentShape = matchingForCurrent.get(0);

                    //currently used Piece should be removed from possible options and from free for selection list of Pieces.
                    matchingForCurrent.remove(currentShape);
                    removeFromIndex(currentShape, indexOfShapes);
                    resultEnlarged[place] = currentShape;

                    placeToMatchingShapes.put(place, matchingForCurrent);
                    place=nextPlace(place, col, bigPuzzleSize);
                }
                if(place<bigPuzzleSize) {//no need to find a match after last iteration
                    matchingForCurrent = findMatchingShape(place, col, resultEnlarged, indexOfShapes);
                }
            }
            if (place >= bigPuzzleSize) {
                return true; //Puzzle is solved in Shapes
            } else {
                while (matchingForCurrent.isEmpty() && place>col+1) {
                    place = previousPlace(place, col);
                    returnPreviousChoiceToIndex(resultEnlarged[place], indexOfShapes);
                    resultEnlarged[place] = null;

                    matchingForCurrent = placeToMatchingShapes.get(place);
                }
                /*if (isNoAnyMatches(indexOfShapes)) {
                    return false;
                }*/
                if (place==col + 1 && matchingForCurrent.isEmpty()) {
                    System.out.println("No solution");
                    return false;
                }
            }
        }
        return false;
    }

    private void returnPreviousChoiceToIndex(Shape shape, Map<String, Map<Shape, Long>> indexOfShapes) {
        String key = getKeyForShapesIndex(shape);

        Map<Shape, Long> shapePerAmount = indexOfShapes.get(key);
        if(shapePerAmount.get(shape)==null){
            shapePerAmount.put(shape, 1L);
        }else{
            Long newAmount = shapePerAmount.get(shape)+1;
            shapePerAmount.put(shape, newAmount);
        }
    }

    //need to avoid first column of added "strait" pieces
    private int previousPlace(int place, int col) {
        return (place - 1) % col != 0 ? place - 1 : place-2;
    }

    private int nextPlace(int place, int col, int puzzleSize) {
     if ((place + 1) % col != 0){
            return place + 1;
        }else {
            return place + 2;
        }
    }

    private void removeFromIndex(Shape currentShape, Map<String, Map<Shape, Long>> indexOfShapes) {
        String key = getKeyForShapesIndex(currentShape);

        Map<Shape, Long> shapePerAmount = indexOfShapes.get(key);
        Long newAmount = shapePerAmount.get(currentShape) - 1;

        if(newAmount!=0){
            shapePerAmount.put(currentShape, newAmount);
        }else{
            shapePerAmount.remove(currentShape);
        }
    }

    private String getKeyForShapesIndex(Shape currentShape) {
        return String.valueOf(currentShape.getSides()[0])+String.valueOf(currentShape.getSides()[1]);
    }

    private boolean isNoAnyMatches(Map<String, Map<Shape, Long>> indexOfShapes) {

       for(String str: indexOfShapes.keySet()){
           if(!indexOfShapes.get(str).isEmpty()){
               return false;
           }
       }
       return true;
    }

    private List<Shape> findMatchingShape(int place, int col, Shape[] resultEnlarged, Map<String, Map<Shape, Long>> indexOfShapes) {

        List<Shape> matchingShapes = new ArrayList<>();

        Shape rightNeigbour = resultEnlarged[place - 1];
        Shape topNeigbour = resultEnlarged[place - col];

        String key = String.valueOf(-rightNeigbour.getSides()[2])+String.valueOf(-topNeigbour.getSides()[3]);

        if(indexOfShapes.get(key)!=null){
            Set<Shape> options = indexOfShapes.get(key).keySet();
            matchingShapes.addAll(options);

            if(isPlaceNotOnEdges(place, col, resultEnlarged.length)){
                return matchingShapes;
            }else {
                return filterMatchingShapesAccordingToPlace(matchingShapes, place, col, resultEnlarged.length);
            }
        }
        else return matchingShapes;
    }

    private List<Shape> filterMatchingShapesAccordingToPlace(List<Shape> matchingShapes, int place, int col, int puzzleSize) {
        if(place==puzzleSize-1){ //BR corner
            return matchingShapes.stream().filter(shape -> shape.getSides()[2]==0 && shape.getSides()[3]==0)
                    .collect(Collectors.toList());
        }
        if(place > puzzleSize - col){//Bottom edge
            return matchingShapes.stream().filter(shape -> shape.getSides()[3]==0)
                    .collect(Collectors.toList());
        }
        if(place % col == col-1){//Right edge
            return matchingShapes.stream().filter(shape -> shape.getSides()[2]==0)
                    .collect(Collectors.toList());
        }
        return matchingShapes;
    }

    private boolean isPlaceNotOnEdges(int place, int col, int puzzleSize) {
        return place < puzzleSize - col && place % col != col-1;
    }

    public Shape[] getResultEnlarged() {
        return resultEnlarged;
    }

    public static void main(String[] args){
        List<Piece> testedPieces = new ArrayList<>();

        /*00001
1000-1
200-1-1
31011
4-1001
5001-1
6-1010
7-1001
80-110
9-1101
100100
110-1-1-1
121-1-11
13111-1
14-1011
15-1-100
160001
170-1-10
1810-1-1
1911-1-1
201-1-10
2111-11
221-101
23000-1
240-11-1
25-100-1
260100
270111
28-10-1-1
291-100
300-111
31-1100
3201-11
3311-10
341010
35-1-100
3601-10
371011
38-1-10-1
39000-1
400-101
41000-1
42000-1
43001-1
44-10-10
451-11-1
46-11-11
471100
480-100
490111
50-1111
51-110-1
5200-1-1
531101
540-101
550000
5600-10
571-100
580-100
590110
60-1100
610-100
620-110
63-1000*/


        Piece piece2 = new Piece(2, new int[]{0, 0, 1, 1});
        Piece piece1 = new Piece(1, new int[]{-1, 0, 1, -1});
        Piece piece4 = new Piece(4, new int[]{-1, 0, 0, 1});
        Piece piece3 = new Piece(3, new int[]{0, -1, 1, 1});
        Piece piece6 = new Piece(6, new int[]{-1, 1, -1, -1});
        Piece piece5 = new Piece(5, new int[]{1, -1, 0, -1});
        Piece piece8 = new Piece(8, new int[]{0, -1, 1, 0});
        Piece piece7 = new Piece(7, new int[]{-1, 1, -1, 0});
        Piece piece9 = new Piece(9, new int[]{1, 1, 0, 0});

        testedPieces.add(piece1);
        testedPieces.add(piece2);
        testedPieces.add(piece3);
        testedPieces.add(piece4);
        testedPieces.add(piece5);
        testedPieces.add(piece6);
        testedPieces.add(piece7);
        testedPieces.add(piece8);
        testedPieces.add(piece9);


        com.puzzle.server.Parser puzzleParser = new com.puzzle.server.Parser(Paths.get("C:\\Ella\\60elements.txt"));
        SolveHelper solveHelper = new SolveHelper(puzzleParser.parse());

        //SolveHelper solveHelper = new SolveHelper(testedPieces);
        solveHelper.tryToSolvePuzzleInShapes(12);
        printPuzzle(solveHelper.resultEnlarged, 13);


       /* SolveHelper solveHelper = new SolveHelper(testedPieces);
        Map<String, Map<Shape, Long>> index = solveHelper.indexOfPiecesByLeftTopSides();

        for(Map.Entry<String, Map<Shape, Long>> entry : index.entrySet()){
            System.out.print("left top pieces: ");
            System.out.println(entry.getKey());

            Map<Shape, Long> possibleOptions = entry.getValue();
            for(Map.Entry<Shape, Long> option: possibleOptions.entrySet()){
                System.out.println("Sides: " +option.getKey().getSides()[0] + " " + option.getKey().getSides()[1] + " " +
                        option.getKey().getSides()[2] + " " + option.getKey().getSides()[3]);

                System.out.println("Number of such pieces: " + option.getValue());
            }
        }*/
    }

    private static void printPuzzle(Shape[] shapes, int numOfLines) {
        StringBuffer outPut = new StringBuffer();
        outPut.append(String.format("Solution for %s lines:", numOfLines));
        outPut.append("\n\n");
        int col = shapes.length / numOfLines;
        for (int i = 0; i < shapes.length; i++) {
            outPut.append(shapes[i].toString() + " ");
            if ((i % col == col - 1 && col != 1) || col == shapes.length) {
                outPut.append("\n");
            }
        }
        System.out.println(outPut.toString());
        System.out.println("Number of pieces: " + shapes.length);
        System.out.println("Last piece: " + shapes[shapes.length-1].toString());
    }
}
