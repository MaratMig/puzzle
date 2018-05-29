import org.apache.commons.math3.primes.Primes;

import java.util.*;
import java.util.stream.Collectors;

public class PuzzleBoard {
    private int numOfLines;
    private List<Piece> pieces ;


    public PuzzleBoard(List<Piece> pieceList, Integer numOfLines) {
        this.pieces = pieceList;
        this.numOfLines = numOfLines;
    }


    public Piece[] tryToSolvePuzzleRectangle() {

        Piece[] result = new Piece[pieces.size()];

        int col = pieces.size() / numOfLines;
        //Map of piece id to all matching afterward
        Map<Integer, List<Piece>> pieceIdToHisMatches = new HashMap<>();
        List<Piece> matchingForCurrent = getAllMatchingForTL(pieces);

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
                matchingForCurrent = findMatchingInLines(freeToUse, result, i, col);

                //keep selected piece and it's matches for future use if it will be needed to return to this step and select other matching piece.
                pieceIdToHisMatches.put(currentPiece.getId(), matchingForCurrent);

            }
            if (freeToUse.isEmpty()) {
                return result; //Puzzle is solved
            } else {
                while (matchingForCurrent.isEmpty() && i>0) {
                    //TODO
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
                if (isNoAnyMatches(pieceIdToHisMatches)) {
                    return result;
                }
            }
        }
        return result;
    }

    private boolean isNoAnyMatches(Map<Integer, List<Piece>> matchingPieces){

        for(Map.Entry entry: matchingPieces.entrySet()){
            List<Piece> matchingPiecesList = (List<Piece>) entry.getValue();
            if(!matchingPiecesList.isEmpty()){
                return false;
            }
        }
        return true;
    }



    private List<Piece> findMatching(List<Piece> freeToUse, Piece currentPiece) {
        return freeToUse.stream().filter(p->(p.getLeft()+currentPiece.getRight())==0).collect(Collectors.toList());
    }

    private List<Piece> findMatchingInLines(List<Piece> freeToUse, Piece[] board, int place, int col){
        List<Piece> matchingPieces = new ArrayList<>();

        if(isTRcorner(place, col)){
            matchingPieces = freeToUse.stream().filter(p->(p.getTop()==0 && p.getRight()==0 && (p.getLeft() + board[place-1].getRight())==0))
                    .collect(Collectors.toList());
        }

        if(isBRcorner(place)){
            matchingPieces = freeToUse.stream().filter(p->
                    p.getBottom()==0 && p.getRight()==0 &&
                            p.getLeft() + board[place-1].getRight()==0
                            && p.getTop() + board[place-col].getBottom()==0)
                    .collect(Collectors.toList());
        }

        if(isBLcorner(place, col)){
            matchingPieces = freeToUse.stream().filter(p->
                    p.getBottom()==0 && p.getLeft()==0 &&
                            p.getTop() + board[place-col].getBottom()==0)
                    .collect(Collectors.toList());
        }

        if(isTopEdge(place, col)){
            matchingPieces = freeToUse.stream().filter(p->
                    p.getTop()==0 && p.getLeft() + board[place-1].getRight()==0)
                    .collect(Collectors.toList());
        }

        if(isBottomEdge(place, col)){
            matchingPieces = freeToUse.stream().filter(p->
                    p.getBottom()==0 && p.getLeft() + board[place-1].getRight()==0
                            && p.getTop() + board[place - col].getBottom() == 0)
                    .collect(Collectors.toList());
        }

        if(isPlaceNotOnEdges(place, col)){
            matchingPieces = freeToUse.stream().filter(p->
                    p.getLeft() + board[place-1].getRight()==0
                            && p.getTop() + board[place - col].getBottom() == 0)
                    .collect(Collectors.toList());

        }

        return matchingPieces;
    }

    private boolean isPlaceNotOnEdges(int place, int col) {
        return !isBLcorner(place, col) && !isTRcorner(place, col)
                && !isBRcorner(place) && !isBottomEdge(place, col) && !isTopEdge(place, col);
    }

    private boolean isBottomEdge(int place, int col) {
        return (place>=pieces.size() - col) && !isBLcorner(place, col) && !isBRcorner(place);
    }

    private boolean isTopEdge(int place, int col) {
        return place>0 && place < col;
    }

    private boolean isLeftEdge(int place, int col){
        return place!=0 && (place % col==0) && !isBLcorner(place, col);
    }

    private boolean isRightEdge(int place, int col){
        return !isTRcorner(place, col) && (place % col==col -1);
    }

    private boolean isBLcorner(int place, int col) {
        return place == pieces.size() - col;
    }

    private boolean isTRcorner(int place, int col) {
        return place==col - 1;
    }

    private boolean isBRcorner(int place) {
        return place==pieces.size()-1;
    }

    private List<Piece> getAllMatchingForTL(List<Piece> pieces) {
        return pieces.stream().filter(p->p.getLeft()==0 && p.getTop()==0).collect(Collectors.toList());
    }



}
