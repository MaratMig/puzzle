import java.util.List;
import java.util.stream.Collectors;

public class MatchingUtils {

    public static List<Piece> getAllBothTR_TL(List<Piece> pieces) {
        return pieces.stream().filter(p->p.getLeft()==0 && p.getTop()==0 && p.getRight()==0).collect(Collectors.toList());
    }

    public static List<Piece> getAllBothTR_BR(List<Piece> pieces) {
        return pieces.stream().filter(p->p.getLeft()==0 && p.getTop()==0 && p.getBottom()==0).collect(Collectors.toList());
    }

    public static List<Piece> getAllMatchingForTL(List<Piece> pieces) {
        return pieces.stream().filter(p->p.getLeft()==0 && p.getTop()==0).collect(Collectors.toList());
    }

    public static boolean isPlaceNotOnEdges(int place, int numOfPieces, int col) {
        return !isBLcorner(place, numOfPieces, col) && !isTRcorner(place, col)
                && !isBRcorner(place, numOfPieces) && !isBottomEdge(place, numOfPieces, col)
                && !isTopEdge(place, col) && !isLeftEdge(place, numOfPieces, col)
                && !isRightEdge(place, col);
    }

    public static boolean isBottomEdge(int place, int numOfPieces, int col) {
        return (place>=numOfPieces - col) && !isBLcorner(place, numOfPieces, col) && !isBRcorner(place, numOfPieces);
    }

    public static boolean isTopEdge(int place, int col) {
        return place>0 && place < col-1;
    }

    public static boolean isLeftEdge(int place, int numOfPieces, int col){
        return place!=0 && (place % col==0) && !isBLcorner(place, numOfPieces, col);
    }

    public static boolean isRightEdge(int place, int col){
        return !isTRcorner(place, col) && (place % col==col -1);
    }

    public static boolean isBLcorner(int place, int numOfPieces, int col) {
        return place == numOfPieces - col;
    }

    public static boolean isTRcorner(int place, int col) {
        return place==col - 1;
    }

    public static boolean isBRcorner(int place, int numOfPieces) {
        return place==numOfPieces-1;
    }

    public static List<Piece> findTRCorners(List<Piece> freeToUse, Piece[] board, int place, int col){
        return freeToUse.stream()
                .filter(p -> (p.getTop() == 0 && p.getRight() == 0 && (p.getLeft() + board[place - 1].getRight()) == 0))
                .collect(Collectors.toList());
    }

    public static List<Piece> findBRCorners(List<Piece> freeToUse, Piece[] board, int place, int col){
        return freeToUse.stream().filter(p ->
                p.getBottom() == 0 && p.getRight() == 0 &&
                        p.getLeft() + board[place - 1].getRight() == 0
                        && p.getTop() + board[place - col].getBottom() == 0)
                .collect(Collectors.toList());
    }

    public static List<Piece> findBLCorners(List<Piece> freeToUse, Piece[] board, int place, int col){
        return freeToUse.stream().filter(p ->
                p.getBottom() == 0 && p.getLeft() == 0 &&
                        p.getTop() + board[place - col].getBottom() == 0)
                .collect(Collectors.toList());
    }

    public static List<Piece> findMatchForTopEdge(List<Piece> freeToUse, Piece[] board, int place, int col){
        return freeToUse.stream().filter(p ->
                p.getTop() == 0 && p.getLeft() + board[place - 1].getRight() == 0)
                .collect(Collectors.toList());
    }

    public static List<Piece> findMatchForBottomEdge(List<Piece> freeToUse, Piece[] board, int place, int col){
        return freeToUse.stream().filter(p ->
                p.getBottom() == 0 && p.getLeft() + board[place - 1].getRight() == 0
                        && p.getTop() + board[place - col].getBottom() == 0)
                .collect(Collectors.toList());
    }

    public static List<Piece> findMatchForLeftEdge(List<Piece> freeToUse, Piece[] board, int place, int col){
        return freeToUse.stream().filter(p ->
                p.getLeft() == 0 && p.getTop() + board[place - col].getBottom() == 0)
                .collect(Collectors.toList());
    }

    public static List<Piece> findMatchForRightEdge(List<Piece> freeToUse, Piece[] board, int place, int col){
        return freeToUse.stream().filter(p ->
                p.getLeft() + board[place - 1].getRight() == 0
                        && p.getTop() + board[place - col].getBottom() == 0 && p.getRight() == 0)
                .collect(Collectors.toList());
    }

    public static List<Piece> findMatchForInternalPiece(List<Piece> freeToUse, Piece[] board, int place, int col){
        return freeToUse.stream().filter(p ->
                p.getLeft() + board[place - 1].getRight() == 0
                        && p.getTop() + board[place - col].getBottom() == 0)
                .collect(Collectors.toList());
    }
}
