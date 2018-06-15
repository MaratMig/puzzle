package com.puzzle.utils;

import com.puzzle.entities.Piece;
import com.puzzle.entities.PuzzleShape;

import java.util.*;

import static java.util.stream.Collectors.*;

public class SolveHelper {

    private List<Piece> pieces;
    private Map<PuzzleShape, List<Integer>> existingShapesToIds;
    private PuzzleShape[] resultEnlarged;

    public SolveHelper(List<Piece> pieces) {
        this.pieces = pieces;
        existingShapesToIds = pieces.stream().collect(groupingBy(Piece::getShape, mapping(Piece::getId, toList())));
    }

    public Map<PuzzleShape, List<Integer>> createMapOfExistingShapesToPiecesIds(List<Piece> pieces){
         return pieces.stream().collect(groupingBy(Piece::getShape, mapping(Piece::getId, toList())));
    }

    public Map<PuzzleShape, List<Integer>> getExistingShapesToIds() {
        return existingShapesToIds;
    }

    public List<PuzzleShape> getExistingShapes(){
        List<PuzzleShape> existingShapes = new ArrayList<>();
        Set<PuzzleShape> existingShapesLst = existingShapesToIds.keySet();
        existingShapes.addAll(existingShapesLst);

        return existingShapes;
    }


    public Piece findPieceById(int currentId) {
        return pieces.stream().filter(piece -> piece.getId()==currentId).findFirst().get();
    }

    private PuzzleShape[] createPuzzleEnlargedByStraitPieces(int numOfLines) {

        int rows = numOfLines + 1;
        int columns = pieces.size() / numOfLines + 1;
        PuzzleShape[] bigPuzzle = new PuzzleShape[rows * columns];

        PuzzleShape allSidesStraits = new PuzzleShape(new int[]{0, 0, 0, 0});

        for(int i =0; i<rows*columns; i++){
            if(i<columns || i%columns==0){
                bigPuzzle[i] = allSidesStraits;
            }
        }
        return bigPuzzle;
    }

    private Map<PuzzleShape, Long> getExistingShapesByAmount(){
        return pieces.stream().collect(groupingBy(Piece::getShape, counting()));
    }

    public PuzzleShape[] getResultEnlarged() {
        return resultEnlarged;
    }

    //To simplify finding matching shapes, original puzzle is enlarged by first line and column with all strait sides
    public boolean tryToSolvePuzzleByShapes(int numOfLines) {

        resultEnlarged = createPuzzleEnlargedByStraitPieces(numOfLines);

        int col = resultEnlarged.length/(numOfLines+1);

        //int col = pieces.size() / numOfLines;

        //Map of shape to all possible matching shapes afterward
        Map<Integer, List<PuzzleShape>> placeToMatchingShapes = new HashMap<>();

        Map<PuzzleShape, Long> amountOfExistingShapes = getExistingShapesByAmount();

        //Place in result array of Shapes where puzzle is starting: second place in the second row.
        Integer place = col+1;

        List<PuzzleShape> matchingForCurrent = findMatchingInLines(amountOfExistingShapes.keySet(), resultEnlarged, place, col);

        //First entry in the map of possible options is assigned to key = null
        placeToMatchingShapes.put(place, matchingForCurrent);

        while (!amountOfExistingShapes.isEmpty() || isNoAnyMatches(placeToMatchingShapes)) {
            while (!amountOfExistingShapes.isEmpty() && !matchingForCurrent.isEmpty()) {

                PuzzleShape currentShape = matchingForCurrent.get(0);

                //When Shape is selected, its counter should be decreased in the map.
                //In case it was the last one, current shape should be removed from existing options.
                Long amountOfCurrentShapes = amountOfExistingShapes.get(currentShape) - 1;

                //When id is selected, it should be removed from possible options for the same shape.
                //In case it was the last one, current shape should be removed from existing options.

                if(amountOfCurrentShapes==0){
                    amountOfExistingShapes.remove(currentShape);
                }else{
                    amountOfExistingShapes.put(currentShape, amountOfCurrentShapes);
                }

                //Currently used shape (current try) should be removed  from possible options so the same shape won't be checked twice
                matchingForCurrent.remove(currentShape);

                resultEnlarged[place] = currentShape;

                place++;

                //no need to find a match for the first column of enlarged puzzle
                if(place%col==0){
                    place++;
                }

                matchingForCurrent = findMatchingInLines(amountOfExistingShapes.keySet(), resultEnlarged, place, col);

                //New: Keep selected shape and the list of possible  next shapes for future use.
                placeToMatchingShapes.put(place, matchingForCurrent);

            }
            if (amountOfExistingShapes.isEmpty()) {
                return true; //Puzzle is solved
            } else {
                while (matchingForCurrent.isEmpty() && place>col+1) {
                    //add it to free list

                    //No need to analyze first column containing only strait shapes
                    place--;
                    if(place%col==0){
                        place--;
                    }

                    PuzzleShape shapeToReturnToPull = resultEnlarged[place];

                    Long numOfShapesToReturn = amountOfExistingShapes.get(shapeToReturnToPull);
                    if(numOfShapesToReturn==null){
                        amountOfExistingShapes.put(shapeToReturnToPull, 1L);
                    }else{
                        amountOfExistingShapes.put(shapeToReturnToPull, numOfShapesToReturn+1);
                    }

                    //remove last added
                    resultEnlarged[place] = null;

                    //Step back: new list of possible matches is according to previous place on puzzle
                    place--;
                    if(place%col==0){
                        place--;
                    }
                    matchingForCurrent = placeToMatchingShapes.get(place);
                    /*
                    if(place>=col+2){
                        PuzzleShape currentShape = resultEnlarged[place-1];

                        //currentMatch = boardState.get(id of new currentPiece) (acutally - the previous one))
                        matchingForCurrent = placeToMatchingShapes.get(currentShape);
                    }else{
                        matchingForCurrent = placeToMatchingShapes.get(null);
                    }*/
                }
                if (isNoAnyMatches(placeToMatchingShapes)) {
                    return false;
                }
            }
        }
        return false;
    }

    private boolean isNoAnyMatches(Map<Integer, List<PuzzleShape>> matchingShapes){

        for(Map.Entry entry: matchingShapes.entrySet()){
            List<PuzzleShape> matchingShapesList = (List<PuzzleShape>) entry.getValue();
            if(!matchingShapesList.isEmpty()){
                return false;
            }
        }
        return true;
    }

    private List<PuzzleShape> findMatchingInLines(Set<PuzzleShape> freeToUse, PuzzleShape[] alreadyPlaced, int place, int col) {
        List<PuzzleShape> matchingShapes = new ArrayList<>();
        List<PuzzleShape> freeShapes = new ArrayList<>();
        freeShapes.addAll(freeToUse);

        int bigSize = alreadyPlaced.length;
        boolean found = false;

        if (MatchingUtilsNew.isPlaceNotOnEdges(place, bigSize, col)) {
            found = true;
            return MatchingUtilsNew.findMatchForInternalPiece(freeShapes, alreadyPlaced, place, col);
        }
        if (MatchingUtilsNew.isBRcorner(place, bigSize)) {
            found = true;
            return MatchingUtilsNew.findBRCorners(freeShapes, alreadyPlaced, place, col);
        }
        if (MatchingUtilsNew.isBottomEdge(place, bigSize, col)) {
            found = true;
            return MatchingUtilsNew.findMatchForBottomEdge(freeShapes, alreadyPlaced, place, col);
        }
        if (MatchingUtilsNew.isRightEdge(place, bigSize, col)) {
            found = true;
            return MatchingUtilsNew.findMatchForRightEdge(freeShapes, alreadyPlaced, place, col);
        }
        System.out.println(found);
        return matchingShapes;
    }
}
