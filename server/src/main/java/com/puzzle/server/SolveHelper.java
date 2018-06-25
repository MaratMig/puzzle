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

    public Shape findShapeWithMaxOccurrenceInIndex(List<Shape> matchingForCurrent, Map<String, Map<Shape, Long>> indexOfShape){
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
}
