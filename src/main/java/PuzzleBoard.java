import org.apache.commons.math3.primes.Primes;

import java.util.*;
import java.util.stream.Collectors;

public class PuzzleBoard {
    int numOfLines;
    ArrayList<Piece> pieces;

    private Set<Integer> possibleNumberOfLines(){
        int numOfPieces = pieces.size();
        Set<Integer> possibleNumberOfLines = new HashSet<>();
        possibleNumberOfLines.add(1);

        List<Integer> factors = new ArrayList<>();
        factors.addAll(Primes.primeFactors(numOfPieces));

        int multiple = 1;
        for(int factor : factors){
            multiple*=factor;
            possibleNumberOfLines.add(multiple);
            possibleNumberOfLines.add(numOfPieces/multiple);
        }

        return possibleNumberOfLines;
    }

    private boolean isTotalSumZero(){
        int sum = 0;
        for(Piece piece : pieces){
            sum+=piece.getSum();
        }
        return sum==0;
    }

    private List<Corner> findMissingCorners(){
        List<Corner> missingCorners = new ArrayList<>();

        boolean isTLCornerExist = false;
        boolean isTRCornerExist = false;
        boolean isBLCornerExist = false;
        boolean isBRCornerExist = false;

        for(Piece piece : pieces){

            if(piece.getLeft() == 0 && piece.getTop()==0){
                isTLCornerExist = true;
                continue;
            }
            if(piece.getTop()==0 && piece.getRight()==0){
                isTRCornerExist=true;
                continue;
            }
            if(piece.getRight()==0 && piece.getBottom()==0){
                isBRCornerExist=true;
                continue;
            }
            if(piece.getLeft()==0 && piece.getBottom()==0){
                isBLCornerExist=true;
                continue;
            }
        }

        if(!isTLCornerExist){
            missingCorners.add(Corner.TL);
        }
        if(!isTRCornerExist){
            missingCorners.add(Corner.TR);
        }
        if(!isBRCornerExist){
            missingCorners.add(Corner.BR);
        }
        if(!isBLCornerExist){
            missingCorners.add(Corner.BL);
        }

        return missingCorners;
    }

    //Number of top and bottom strait sides should be equal;
    //Number of left and right strait sides should be equal;
    private boolean isValidNumberOfStraitSides(){
        long numOfStraitLeft = pieces.stream().filter(p->p.getLeft()==0).count();
        long numOfStraitRight = pieces.stream().filter(p->p.getRight()==0).count();

        long numOfStraitTop = pieces.stream().filter(p->p.getTop()==0).count();
        long numOfStraitBottom = pieces.stream().filter(p->p.getBottom()==0).count();

        return (numOfStraitLeft==numOfStraitRight) && (numOfStraitTop==numOfStraitBottom);
    }

    private Piece[] tryToSolve(ArrayList<Piece> pieces) {

        Piece[] result = new Piece[pieces.size()];

        //Map of piece id to all matching afterward
        Map<Integer, List<Piece>> pieceIdToHisMatches = new HashMap<>();
        List<Piece> matchingForCurrent = getAllMatchingForTL(pieces);

        //first enty in map is assigned to key = -1 and all posible TL pieces
        pieceIdToHisMatches.put(-1, matchingForCurrent);

        //At the begining all pieces are free for selection.
        List<Piece> freeToUse = pieces;

        //index of Pieces in result array.
        int index = -1;
        while (index >= 0 || index == pieces.size()) {
            while (!freeToUse.isEmpty() && !matchingForCurrent.isEmpty()) {
                index++;
                Piece currentPiece = matchingForCurrent.get(0);

                //currently used Piece should be removed from possible options and from free for selection list of Pieces.
                matchingForCurrent.remove(currentPiece);
                freeToUse.remove(currentPiece);

                result[index] = currentPiece;
                matchingForCurrent = findMatching(freeToUse, currentPiece);

                //keep selected piece and it's matches for future use if it will be needed to return to this step and select other matching piece.
                pieceIdToHisMatches.put(currentPiece.getId(), matchingForCurrent);

            }
            if (freeToUse.isEmpty()) {
                return result; //Puzzle is solved
            } else {
                while (matchingForCurrent.isEmpty() || pieceIdToHisMatches.get(-1).isEmpty()) {
                    //TODO
                    //add it to free list
                    freeToUse.add(result[index]);

                    //remove last added
                    result[index] = null;

                    index--;
                    //currentPiece = previously added to boardState map
                    Piece currentPiece = result[index];

                    //currentMatch = boardState.get(id of new currentPiece (acutally - the previous one))
                    matchingForCurrent = pieceIdToHisMatches.get(currentPiece.getId());
                }
                if (pieceIdToHisMatches.get(-1).isEmpty()) {
                    return BAD RESULt;
                }
            }
        }
        return result;
    }

    private List<Piece> findMatching(List<Piece> freeToUse, Piece currentPiece) {
        return null;
    }

    private void moveForward() {

    }

    private List<Piece> getAllMatchingForTL(ArrayList<Piece> pieces) {
        return null;
    }
}
