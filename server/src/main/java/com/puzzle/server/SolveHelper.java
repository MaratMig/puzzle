package com.puzzle.server;


import com.puzzle.common.entities.Piece;
import com.puzzle.common.entities.Shape;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class SolveHelper {
    private List<Piece> pieces;
    private Map<Shape, List<Piece>> existingShapesToPieces;

    public Map<Shape, List<Piece>> getExistingShapesToIds() {
        return existingShapesToPieces;
    }

    public SolveHelper(List<Piece> pieces) {
        this.pieces = pieces;
        existingShapesToPieces = pieces.stream().collect(groupingBy(Piece::getShape, mapping(Function.identity(), toList())));
    }

    public Map<String, Map<Shape, Long>> createIndexOfShapesByLeftTopSides(){
        Map<String, Map<Shape, Long>> indexOfPieces = new LinkedHashMap<>();

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

    /*public boolean isSolutionInShapesExists(int numOfLines) {

        resultEnlarged = createPuzzleEnlargedByStraitPieces(numOfLines);

        int newNumOfLines = numOfLines+1;
        int bigPuzzleSize = resultEnlarged.length;
        int col = bigPuzzleSize / newNumOfLines;

        //This map will be updated during finding solution process;
        //Taken Shape will be removed (while step forward) / added back(while step backward)
        Map<String, Map<Shape, Long>> indexOfShapes = createIndexOfShapesByLeftTopSides();

        //Map of matching for place Shapes - will be used during "step backward" in case there is no possible option to continue
        Map<Integer, List<Shape>> placeToMatchingShapes = new HashMap<>();

        //Starting place is a second piece in the second line (since puzzle was enlarged by strait pieces)
        int place = col + 1;

        //First list of matching Shape
        List<Shape> matchingForCurrent = findMatchingShape(place, col, resultEnlarged, indexOfShapes);

        Shape currentShape = null;
        int counter = 0;
        Map<Integer, Integer> placeToNumberOfBackSteps = new HashMap<>();
        while (place <= bigPuzzleSize - 1 || isNoAnyMatches(indexOfShapes)) {
            while (place <= bigPuzzleSize - 1 && !matchingForCurrent.isEmpty()) {

                if(!matchingForCurrent.isEmpty()) {
                    //currentShape = matchingForCurrent.get(0);

                    //Euristic: it's better to take a shape with max occurance from all possible options;
                    //It should decrease a chance for missing such shape at the next steps.
                    currentShape = findShapeWithMaxOccuranceInIndex(matchingForCurrent, indexOfShapes);

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
                    counter++;
                    //In case of continues forward-backward steps in the same place, return several steps backward
                    //to increase a chance of selecting different path
                    if(counter>3){
                        int numOfBackSteps = Math.min(5, place - (col + 1)); // it's impossible to step back more than first place in the original puzzle
                        for(int i=0; i<numOfBackSteps; i++){
                            place = previousPlace(place, col);
                            returnPreviousChoiceToIndex(resultEnlarged[place], indexOfShapes);
                            resultEnlarged[place] = null;
                            matchingForCurrent = placeToMatchingShapes.get(place);
                        }
                        counter=0;
                    }else {
                        place = previousPlace(place, col);
                        returnPreviousChoiceToIndex(resultEnlarged[place], indexOfShapes);
                        resultEnlarged[place] = null;
                        matchingForCurrent = placeToMatchingShapes.get(place);
                    }
                }
                *//*if (isNoAnyMatches(indexOfShapes)) {
                    return false;
                }*//*
                if (place==col + 1 && matchingForCurrent.isEmpty()) {
                    System.out.println("No solution");
                    return false;
                }
            }
        }
        return false;
    }*/

    public Shape findShapeWithMaxOccuranceInIndex(List<Shape> matchingForCurrent, Map<String, Map<Shape, Long>> indexOfShape){
        Long counter = 0L;
        Shape nextShape = null;
        for(Shape shape : matchingForCurrent){
            String leftTopKey = getKeyForShapesIndex(shape);
            Map<Shape, Long> shapesByKey = indexOfShape.get(leftTopKey);

            if(shapesByKey.get(shape)>counter){
                counter = shapesByKey.get(shape);
                nextShape = shape;
            }
        }
        return nextShape;
    }

    public void returnPreviousChoiceToIndex(Shape shape, Map<String, Map<Shape, Long>> indexOfShapes) {
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
    public int previousPlace(int place, int col) {
        return (place - 1) % col != 0 ? place - 1 : place-2;
    }

    public int nextPlace(int place, int col, int puzzleSize) {
     if ((place + 1) % col != 0){
            return place + 1;
        }else {
            return place + 2;
        }
    }

    public void removeFromIndex(Shape currentShape, Map<String, Map<Shape, Long>> indexOfShapes) {
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

    public boolean isNoAnyMatches(Map<String, Map<Shape, Long>> indexOfShapes) {

       for(String str: indexOfShapes.keySet()){
           if(!indexOfShapes.get(str).isEmpty()){
               return false;
           }
       }
       return true;
    }

    public List<Shape> findMatchingShape(int place, int col, Shape[] resultEnlarged, Map<String, Map<Shape, Long>> indexOfShapes) {

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
