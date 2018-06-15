package com.puzzle;

import com.puzzle.entities.Piece;
import com.puzzle.entities.PuzzleShape;
import com.puzzle.utils.MatchingUtils;
import com.puzzle.utils.MatchingUtilsNew;
import com.puzzle.utils.SolveHelper;

import java.util.*;
import java.util.stream.Collectors;

public class PuzzleSolver {

    private List<Piece> pieces;
    private Integer numOfLines;
    private SolveHelper solveHelper;
    private  Piece[] result;

    public PuzzleSolver(List<Piece> pieceList, Integer numOfLines) {
        this.pieces = pieceList;
        this.result = new Piece[pieces.size()];
        this.numOfLines = numOfLines;
        solveHelper = new SolveHelper(pieces);
    }

    public Piece[] getResult() {
        return result;
    }

    public boolean tryToSolvePuzzleRectangleSuper(){
        return solveHelper.tryToSolvePuzzleByShapes(numOfLines);
    }

    public Piece[] getSolutionByShapes(){

        PuzzleShape[] solutionInShapes = null;

        solutionInShapes = solveHelper.getResultEnlarged();


        List<Integer> idsOfFinalSolution = createSolutionAsListOfIds(solutionInShapes);

        int index = 0;
        for(Integer id : idsOfFinalSolution){
            result[index] = pieces.stream().filter(p->p.getId()==id).findFirst().get();
            index++;
        }
        return result;
    }

    private List<Integer> createSolutionAsListOfIds(PuzzleShape[] solutionInShapes){
        Map<PuzzleShape, List<Integer>> existingShapesToIds = solveHelper.getExistingShapesToIds();
        List<Integer> ids = new ArrayList<>();

        int rows = numOfLines + 1;
        int columns = pieces.size() / numOfLines + 1;

        for(int i =0; i<rows*columns; i++){
            if(i>columns && i%columns!=0){
                Integer id = existingShapesToIds.get(solutionInShapes[i]).get(0);
                ids.add(id);

                List<Integer> idsOfShape = existingShapesToIds.get(solutionInShapes[i]);
                idsOfShape.remove(id);

                existingShapesToIds.put(solutionInShapes[i], idsOfShape);
            }
        }

        return ids;
    }

    public boolean tryToSolvePuzzleRectangleOld() {

        int col = pieces.size() / numOfLines;

        //Map of piece id to all matching afterward
        Map<Integer, List<Piece>> pieceIdToHisMatches = new HashMap<>();

        List<Piece> matchingForCurrent = findFirstPieceAccordingToPuzzleFormOld(col);

        //first entry in map is assigned to key = -1 and all posible TL pieces
        pieceIdToHisMatches.put(-1, matchingForCurrent);

        //At the begining all pieces are free for selection.
        List<Piece> freeToUse = new ArrayList<>();
        freeToUse.addAll(pieces);

        //index of Pieces in result array.
        int i = 0;
        while (!freeToUse.isEmpty() || isNoAnyMatchesOld(pieceIdToHisMatches)) {
            while (!freeToUse.isEmpty() && !matchingForCurrent.isEmpty()) {

                Piece currentPiece = matchingForCurrent.get(0);

                //currently used Piece should be removed from possible options and from free for selection list of Pieces.
                matchingForCurrent.remove(currentPiece);
                freeToUse.remove(currentPiece);

                result[i] = currentPiece;

                i++;

                matchingForCurrent = findPossibleMatchAccordingToPuzzleFormOld(col, freeToUse, i);

                //keep selected piece and it's matches for future use if it will be needed to return to this step and select other matching piece.
                pieceIdToHisMatches.put(currentPiece.getId(), matchingForCurrent);

            }
            if (freeToUse.isEmpty()) {
                return true; //Puzzle is solved
            } else {
                while (matchingForCurrent.isEmpty() && i>0) {
                    //add it to free list
                    i--;
                    freeToUse.add(result[i]);

                    //remove last added
                    result[i] = null;

                    //currentPiece = previously added to boardState map
                    if(i>=1){
                        Piece currentPiece = result[i-1];

                        //currentMatch = boardState.get(id of new currentPiece) (acutally - the previous one))
                        matchingForCurrent = pieceIdToHisMatches.get(currentPiece.getId());
                    }else{
                        matchingForCurrent = pieceIdToHisMatches.get(-1);
                    }
                }
                if (isNoAnyMatchesOld(pieceIdToHisMatches)) {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean tryToSolvePuzzleRectangle() {

        int col = pieces.size() / numOfLines;

        Map<PuzzleShape, List<Integer>> existingShapesToPiecesIds = solveHelper.getExistingShapesToIds();

        //Map of piece shape to all possible matching shapes afterward
        Map<PuzzleShape, List<PuzzleShape>> pieceIdToHisMatches = new HashMap<>();

        List<PuzzleShape> matchingForCurrent = findFirstPieceAccordingToPuzzleForm(col);

        //New: first entry in map is assigned to key = null and all posible TL pieces
        pieceIdToHisMatches.put(null, matchingForCurrent);

        //At the begining all pieces are free for selection.
        List<Piece> freeToUse = new ArrayList<>();
        freeToUse.addAll(pieces);

        //index of Pieces in result array.
        int i = 0;
        while (!freeToUse.isEmpty() || isNoAnyMatches(pieceIdToHisMatches)) {
            while (!freeToUse.isEmpty() && !matchingForCurrent.isEmpty()) {

                PuzzleShape currentShape = matchingForCurrent.get(0);
                List<Integer> possibleIds = existingShapesToPiecesIds.get(currentShape);
                int currentId = possibleIds.get(0);

                //When id is selected, it should be removed from possible options for the same shape.
                //In case it was the last one, current shape should be removed from existing options.
                possibleIds.remove((Integer) currentId);

                if(possibleIds.isEmpty()){
                   existingShapesToPiecesIds.remove(currentShape);
                }else{
                    existingShapesToPiecesIds.put(currentShape, possibleIds);
                }

                Piece currentPiece = solveHelper.findPieceById(currentId);

                //Old: currently used Piece should be removed from possible options and from free for selection list of Pieces.
                //New: currently used shape (current try) should be removed  from possible options so the same shape won't be checked twice
                matchingForCurrent.remove(currentShape);

                freeToUse.remove(currentPiece);

                result[i] = currentPiece;

                i++;

                matchingForCurrent = findPossibleMatchAccordingToPuzzleForm(col, existingShapesToPiecesIds.keySet(), i);

                //Old: keep selected piece and it's matches for future use if it will be needed to return to this step and select other matching piece.
                //New: Keep selected shape and the list of possible  next shapes for future use.
                pieceIdToHisMatches.put(currentShape, matchingForCurrent);

            }
            if (freeToUse.isEmpty()) {
                return true; //Puzzle is solved
            } else {
                while (matchingForCurrent.isEmpty() && i>0) {
                    //add it to free list
                    i--;
                    freeToUse.add(result[i]);
                    existingShapesToPiecesIds = solveHelper.createMapOfExistingShapesToPiecesIds(freeToUse);

                    //remove last added
                    result[i] = null;

                    //currentPiece = previously added to boardState map
                    if(i>=1){
                        Piece currentPiece = result[i-1];

                        //currentMatch = boardState.get(id of new currentPiece) (acutally - the previous one))
                        matchingForCurrent = pieceIdToHisMatches.get(currentPiece.getShape());
                    }else{
                        matchingForCurrent = pieceIdToHisMatches.get(null);
                    }
                }
                if (isNoAnyMatches(pieceIdToHisMatches)) {
                    return false;
                }
            }
        }
        return false;
    }

    private List<Piece> findPossibleMatchAccordingToPuzzleFormOld(int col, List<Piece> freeToUse, int i) {
        List<Piece> matchingForCurrent;
        if(col==pieces.size()){
            matchingForCurrent = findMatchingInOneLinePuzzleOld(freeToUse, result, i);
        }else if(col==1){
            matchingForCurrent = findMatchingInOneColPuzzleOld(freeToUse, result, i);
        }else{
            matchingForCurrent = findMatchingInLinesOld(freeToUse, result, i, col);
        }
        return matchingForCurrent;
    }

    private List<PuzzleShape> findPossibleMatchAccordingToPuzzleForm(int col, Set<PuzzleShape> freeToUse, int i) {
        List<PuzzleShape> matchingForCurrent;
        if(col==pieces.size()){
            matchingForCurrent = findMatchingInOneLinePuzzle(freeToUse, result, i);
        }else if(col==1){
            matchingForCurrent = findMatchingInOneColPuzzle(freeToUse, result, i);
        }else{
            matchingForCurrent = findMatchingInLines(freeToUse, result, i, col);
        }
        return matchingForCurrent;
    }

    private List<Piece> findFirstPieceAccordingToPuzzleFormOld(int col) {
        List<Piece> matchingForCurrent;
        if(col == pieces.size()){
            matchingForCurrent = MatchingUtils.getAllBothTR_BR(pieces);
        }else if(col==1){
            matchingForCurrent = MatchingUtils.getAllBothTR_TL(pieces);
        }else{
            matchingForCurrent = MatchingUtils.getAllMatchingForTL(pieces);
        }
        return matchingForCurrent;
    }

    private List<PuzzleShape> findFirstPieceAccordingToPuzzleForm(int col) {
        List<PuzzleShape> matchingForCurrent;
        if(col == pieces.size()){
            matchingForCurrent = MatchingUtilsNew.getAllBothTR_BR(solveHelper.getExistingShapes());
        }else if(col==1){
            matchingForCurrent = MatchingUtilsNew.getAllBothTR_TL(solveHelper.getExistingShapes());
        }else{
            matchingForCurrent = MatchingUtilsNew.getAllMatchingForTL(solveHelper.getExistingShapes());
        }
        return matchingForCurrent;
    }

    private List<Piece> findMatchingInOneLinePuzzleOld(List<Piece> freeToUse, Piece[] board, int place){
        List<Piece> matchingPieces = new ArrayList<>();

        int length = pieces.size();

        if(place<length-1) {
            matchingPieces = freeToUse.stream()
                    .filter(p -> p.getLeft() + board[place - 1].getRight() == 0 && p.getTop() == 0 && p.getBottom() == 0)
                    .collect(Collectors.toList());

        }
        if(place==length-1){
            matchingPieces = freeToUse.stream().filter(p ->
                    p.getLeft() + board[place - 1].getRight() == 0 && p.getTop() == 0 && p.getBottom() == 0 && p.getRight()==0)
                    .collect(Collectors.toList());
        }
        return matchingPieces;
    }

    private List<PuzzleShape> findMatchingInOneLinePuzzle(Set<PuzzleShape> freeToUse, Piece[] board, int place){
        List<PuzzleShape> matchingPieces = new ArrayList<>();

        int length = pieces.size();

        if(place<length-1) {
            matchingPieces = freeToUse.stream()
                    .filter(p -> p.getLeft() + board[place - 1].getRight() == 0 && p.getTop() == 0 && p.getBottom() == 0)
                    .collect(Collectors.toList());

        }
        if(place==length-1){
            matchingPieces = freeToUse.stream().filter(p ->
                    p.getLeft() + board[place - 1].getRight() == 0 && p.getTop() == 0 && p.getBottom() == 0 && p.getRight()==0)
                    .collect(Collectors.toList());
        }
        return matchingPieces;
    }

    private List<PuzzleShape> findMatchingInOneColPuzzle(Set<PuzzleShape> freeToUse, Piece[] board, int place) {

        List<PuzzleShape> matchingPieces = new ArrayList<>();

        int length = pieces.size();

        if(place<length-1) {
            matchingPieces = freeToUse.stream().filter(p -> p.getTop() + board[place-1].getBottom()==0 && p.getLeft()==0 && p.getRight()==0)
                    .collect(Collectors.toList());

        }
        if(place==length-1){
            matchingPieces = freeToUse.stream().filter(p ->
                    p.getTop() + board[place-1].getBottom()==0 && p.getLeft()==0 && p.getRight()==0 && p.getBottom()==0)
                    .collect(Collectors.toList());
        }
        return matchingPieces;
    }


    private List<Piece> findMatchingInOneColPuzzleOld(List<Piece> freeToUse, Piece[] board, int place) {

        List<Piece> matchingPieces = new ArrayList<>();

        int length = pieces.size();

        if(place<length-1) {
            matchingPieces = freeToUse.stream().filter(p -> p.getTop() + board[place-1].getBottom()==0 && p.getLeft()==0 && p.getRight()==0)
                    .collect(Collectors.toList());

        }
        if(place==length-1){
            matchingPieces = freeToUse.stream().filter(p ->
                    p.getTop() + board[place-1].getBottom()==0 && p.getLeft()==0 && p.getRight()==0 && p.getBottom()==0)
                    .collect(Collectors.toList());
        }
        return matchingPieces;
    }

    private List<PuzzleShape> findMatchingInLines(Set<PuzzleShape> freeToUse, Piece[] board, int place, int col) {
        List<PuzzleShape> matchingShapes = new ArrayList<>();
        List<PuzzleShape> freeShapes = new ArrayList<>();
        freeShapes.addAll(freeToUse);

        if (MatchingUtilsNew.isTRcorner(place, col)) {
            return MatchingUtilsNew.findTRCorners(freeShapes, board, place, col);
        }
        if (MatchingUtilsNew.isBRcorner(place, pieces.size())) {
            return MatchingUtilsNew.findBRCorners(freeShapes, board, place, col);
        }
        if (MatchingUtilsNew.isBLcorner(place, pieces.size(), col)) {
            return MatchingUtilsNew.findBLCorners(freeShapes, board, place, col);
        }
        if (MatchingUtilsNew.isTopEdge(place, col)) {
            return MatchingUtilsNew.findMatchForTopEdge(freeShapes, board, place, col);
        }
        if (MatchingUtilsNew.isBottomEdge(place, pieces.size(), col)) {
            return MatchingUtilsNew.findMatchForBottomEdge(freeShapes, board, place, col);
        }
        if (MatchingUtilsNew.isLeftEdge(place, pieces.size(), col)) {
            return MatchingUtilsNew.findMatchForLeftEdge(freeShapes, board, place, col);
        }
        if (MatchingUtilsNew.isRightEdge(place, pieces.size(), col)) {
            return MatchingUtilsNew.findMatchForRightEdge(freeShapes, board, place, col);
        }
        if (MatchingUtilsNew.isPlaceNotOnEdges(place, pieces.size(), col)) {
            return MatchingUtilsNew.findMatchForInternalPiece(freeShapes, board, place, col);
        }
        return matchingShapes;
    }

    private List<Piece> findMatchingInLinesOld(List<Piece> freeToUse, Piece[] board, int place, int col) {
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

    private boolean isNoAnyMatches(Map<PuzzleShape, List<PuzzleShape>> matchingShapes){

        for(Map.Entry entry: matchingShapes.entrySet()){
            List<PuzzleShape> matchingShapesList = (List<PuzzleShape>) entry.getValue();
            if(!matchingShapesList.isEmpty()){
                return false;
            }
        }
        return true;
    }

    private boolean isNoAnyMatchesOld(Map<Integer, List<Piece>> matchingPieces){

        for(Map.Entry entry: matchingPieces.entrySet()){
            List<Piece> matchingPiecesList = (List<Piece>) entry.getValue();
            if(!matchingPiecesList.isEmpty()){
                return false;
            }
        }
        return true;
    }
}
