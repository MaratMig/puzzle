package com.puzzle.server;

import com.puzzle.common.entities.Piece;
import com.puzzle.common.entities.Shape;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PuzzleSolver {

    List<Piece> pieces;
    Integer numOfLines;
    Piece[] result;
    Shape[] resultEnlarged;
    private SolveHelper solveHelper;

    public PuzzleSolver(List<Piece> pieceList, Integer numOfLines) {
        this.pieces = pieceList;
        this.result = new Piece[pieces.size()];
        this.numOfLines = numOfLines;
        solveHelper = new SolveHelper(pieces);
    }

    private void initResultEnlarged(){
        resultEnlarged = solveHelper.createPuzzleEnlargedByStraitPieces(numOfLines);
    }

    public boolean tryToSolvePuzzleRectangle(){
        if(isSolutionInShapesExists(numOfLines)){
            createResultFromSolutionInShapes(resultEnlarged);
            return true;
        } else {
            return false;
        }
    }

    public Piece[] getResult() {
        return result;
    }

    private void createResultFromSolutionInShapes(Shape[] solutionInShapes){

        Map<Shape, List<Piece>> existingShapesToPieces = solveHelper.getExistingShapesToIds();

        int columns = pieces.size() / numOfLines + 1;

        for(int i =0; i<resultEnlarged.length; i++){
            if(i>columns && i%columns!=0){ //avoid added strait pieces
                List<Piece> matchingPieces = existingShapesToPieces.get(solutionInShapes[i]);
                result[i] = matchingPieces.get(0);
                matchingPieces.remove(result[i]);
            }
        }
    }

    /*public boolean tryToSolvePuzzleRectangle() {

        int col = pieces.size() / numOfLines;

        //Map of piece id to all matching afterward
        Map<Integer, List<Piece>> pieceIdToHisMatches = new HashMap<>();

        List<Piece> matchingForCurrent = findFirstPieceAccordingToPuzzleForm(col);

        //first entry in map is assigned to key = -1 and all posible TL pieces
        pieceIdToHisMatches.put(-1, matchingForCurrent);

        //At the begining all pieces are free for selection.
        List<Piece> freeToUse = new ArrayList<>();
        freeToUse.addAll(pieces);

        //index of Pieces in result array.
        int i = 0;
        while (!freeToUse.isEmpty() || isNoAnyMatches(pieceIdToHisMatches)) {
            while (!freeToUse.isEmpty() && !matchingForCurrent.isEmpty()) {

                Piece currentPiece = matchingForCurrent.get(0);

                //currently used Piece should be removed from possible options and from free for selection list of Pieces.
                matchingForCurrent.remove(currentPiece);
                freeToUse.remove(currentPiece);

                result[i] = currentPiece;

                i++;

                matchingForCurrent = findPossibleMatchAccordingToPuzzleForm(col, freeToUse, i);

                //keep selected piece and it's matches for future use if it will be needed to return to this step and select other matching piece.
                pieceIdToHisMatches.put(currentPiece.getId(), matchingForCurrent);

            }
            if (freeToUse.isEmpty()) {
                return true; //Puzzle is solved
            } else {
                while (matchingForCurrent.isEmpty() && i > 0) {
                    //add it to free list
                    i--;
                    freeToUse.add(result[i]);

                    //remove last added
                    result[i] = null;

                    //currentPiece = previously added to boardState map
                    if (i >= 1) {
                        Piece currentPiece = result[i - 1];

                        //currentMatch = boardState.get(id of new currentPiece) (acutally - the previous one))
                        matchingForCurrent = pieceIdToHisMatches.get(currentPiece.getId());
                    } else {
                        matchingForCurrent = pieceIdToHisMatches.get(-1);
                    }
                }
                if (isNoAnyMatches(pieceIdToHisMatches)) {
                    return false;
                }
            }
        }
        return false;
    }

    private List<Piece> findPossibleMatchAccordingToPuzzleForm(int col, List<Piece> freeToUse, int i) {
        List<Piece> matchingForCurrent;
        if (col == pieces.size()) {
            matchingForCurrent = findMatchingInOneLinePuzzle(freeToUse, result, i);
        } else if (col == 1) {
            matchingForCurrent = findMatchingInOneColPuzzle(freeToUse, result, i);
        } else {
            matchingForCurrent = findMatchingInLines(freeToUse, result, i, col);
        }
        return matchingForCurrent;
    }

    private List<Piece> findFirstPieceAccordingToPuzzleForm(int col) {
        List<Piece> matchingForCurrent;
        if (col == pieces.size()) {
            matchingForCurrent = MatchingUtils.getAllBothTR_BR(pieces);
        } else if (col == 1) {
            matchingForCurrent = MatchingUtils.getAllBothTR_TL(pieces);
        } else {
            matchingForCurrent = MatchingUtils.getAllMatchingForTL(pieces);
        }
        return matchingForCurrent;
    }

    private List<Piece> findMatchingInOneLinePuzzle(List<Piece> freeToUse, Piece[] board, int place) {
        List<Piece> matchingPieces = new ArrayList<>();

        int length = pieces.size();

        if (place < length - 1) {
            matchingPieces = freeToUse.stream()
                    .filter(p -> p.getLeft() + board[place - 1].getRight() == 0 && p.getTop() == 0 && p.getBottom() == 0)
                    .collect(Collectors.toList());

        }
        if (place == length - 1) {
            matchingPieces = freeToUse.stream().filter(p ->
                    p.getLeft() + board[place - 1].getRight() == 0 && p.getTop() == 0 && p.getBottom() == 0 && p.getRight() == 0)
                    .collect(Collectors.toList());
        }
        return matchingPieces;
    }

    private List<Piece> findMatchingInOneColPuzzle(List<Piece> freeToUse, Piece[] board, int place) {

        List<Piece> matchingPieces = new ArrayList<>();

        int length = pieces.size();

        if (place < length - 1) {
            matchingPieces = freeToUse.stream().filter(p -> p.getTop() + board[place - 1].getBottom() == 0 && p.getLeft() == 0 && p.getRight() == 0)
                    .collect(Collectors.toList());

        }
        if (place == length - 1) {
            matchingPieces = freeToUse.stream().filter(p ->
                    p.getTop() + board[place - 1].getBottom() == 0 && p.getLeft() == 0 && p.getRight() == 0 && p.getBottom() == 0)
                    .collect(Collectors.toList());
        }
        return matchingPieces;
    }

    private List<Piece> findMatchingInLines(List<Piece> freeToUse, Piece[] board, int place, int col) {
        List<Piece> matchingPieces = new ArrayList<>();

        if (MatchingUtils.isTRcorner(place, col)) {
            return MatchingUtils.findTRCorners(freeToUse, board, place, col);
        }
        if (MatchingUtils.isBRcorner(place, pieces.size())) {
            return MatchingUtils.findBRCorners(freeToUse, board, place, col);
        }
        if (MatchingUtils.isBLcorner(place, pieces.size(), col)) {
            return MatchingUtils.findBLCorners(freeToUse, board, place, col);
        }
        if (MatchingUtils.isTopEdge(place, col)) {
            return MatchingUtils.findMatchForTopEdge(freeToUse, board, place, col);
        }
        if (MatchingUtils.isBottomEdge(place, pieces.size(), col)) {
            return MatchingUtils.findMatchForBottomEdge(freeToUse, board, place, col);
        }
        if (MatchingUtils.isLeftEdge(place, pieces.size(), col)) {
            return MatchingUtils.findMatchForLeftEdge(freeToUse, board, place, col);
        }
        if (MatchingUtils.isRightEdge(place, pieces.size(), col)) {
            return MatchingUtils.findMatchForRightEdge(freeToUse, board, place, col);
        }
        if (MatchingUtils.isPlaceNotOnEdges(place, pieces.size(), col)) {
            return MatchingUtils.findMatchForInternalPiece(freeToUse, board, place, col);
        }
        return matchingPieces;
    }

    private boolean isNoAnyMatches(Map<Integer, List<Piece>> matchingPieces) {

        for (Map.Entry entry : matchingPieces.entrySet()) {
            List<Piece> matchingPiecesList = (List<Piece>) entry.getValue();
            if (!matchingPiecesList.isEmpty()) {
                return false;
            }
        }
        return true;
    }*/

    public boolean isSolutionInShapesExists(int numOfLines) {

        initResultEnlarged();

        int newNumOfLines = numOfLines+1;
        int bigPuzzleSize = resultEnlarged.length;
        int col = bigPuzzleSize / newNumOfLines;

        //This map will be updated during finding solution process;
        //Taken Shape will be removed (while step forward) / added back(while step backward)
        Map<String, Map<Shape, Long>> indexOfShapes = solveHelper.createIndexOfShapesByLeftTopSides();

        //Map of matching for place Shapes - will be used during "step backward" in case there is no possible option to continue
        Map<Integer, List<Shape>> placeToMatchingShapes = new HashMap<>();

        //Starting place is a second piece in the second line (since puzzle was enlarged by strait pieces)
        int place = col + 1;

        //First list of matching Shapes
        List<Shape> matchingForCurrent = solveHelper.findMatchingShape(place, col, resultEnlarged, indexOfShapes);

        Shape currentShape = null;
        int counter = 0;
        Map<Integer, Integer> placeToNumberOfBackSteps = new HashMap<>();
        while (place <= bigPuzzleSize - 1 || solveHelper.isNoAnyMatches(indexOfShapes)) {
            while (place <= bigPuzzleSize - 1 && !matchingForCurrent.isEmpty()) {

                if(!matchingForCurrent.isEmpty()) {

                    //It's better to take a shape with max occurance from all possible options;
                    //It should decrease a chance for missing such shape at the next steps.
                    currentShape = solveHelper.findShapeWithMaxOccuranceInIndex(matchingForCurrent, indexOfShapes);

                    //currently used Piece should be removed from possible options and from free for selection list of Pieces.
                    matchingForCurrent.remove(currentShape);
                    solveHelper.removeFromIndex(currentShape, indexOfShapes);
                    resultEnlarged[place] = currentShape;

                    placeToMatchingShapes.put(place, matchingForCurrent);
                    place=solveHelper.nextPlace(place, col, bigPuzzleSize);
                }
                if(place<bigPuzzleSize) {//no need to find a match after last iteration
                    matchingForCurrent = solveHelper.findMatchingShape(place, col, resultEnlarged, indexOfShapes);
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
                            place = solveHelper.previousPlace(place, col);
                            solveHelper.returnPreviousChoiceToIndex(resultEnlarged[place], indexOfShapes);
                            resultEnlarged[place] = null;
                            matchingForCurrent = placeToMatchingShapes.get(place);
                        }
                        counter=0;
                    }else {
                        place = solveHelper.previousPlace(place, col);
                        solveHelper.returnPreviousChoiceToIndex(resultEnlarged[place], indexOfShapes);
                        resultEnlarged[place] = null;
                        matchingForCurrent = placeToMatchingShapes.get(place);
                    }
                }
                if (place==col + 1 && matchingForCurrent.isEmpty()) {
                    //System.out.println("No solution");
                    return false;
                }
            }
        }
        return false;
    }

}
