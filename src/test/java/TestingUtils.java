import java.util.ArrayList;
import java.util.List;

public class TestingUtils {

    private static List<Piece> testedPieces = new ArrayList<>();
    private static List<Piece> testedTLcorners = new ArrayList<>();
    private static List<Piece> testedTRcorners = new ArrayList<>();
    private static List<Piece> testedBRcorners = new ArrayList<>();
    private static List<Piece> testedBLcorners = new ArrayList<>();
    private static List<Piece> testedTopEdges = new ArrayList<>();
    private static List<Piece> testedLeftEdges = new ArrayList<>();
    private static List<Piece> testedRightEdges = new ArrayList<>();
    private static List<Piece> testedBottomEdges = new ArrayList<>();

    public TestingUtils(){

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

        testedTLcorners.add(piece2);
        testedTRcorners.add(piece4);
        testedBRcorners.add(piece9);
        testedBLcorners.add(piece8);
        testedLeftEdges.add(piece3);
        testedTopEdges.add(piece1);
        testedRightEdges.add(piece5);
        testedBottomEdges.add(piece7);

    }

    public List<Piece> getTestedPieces() {
        return testedPieces;
    }

    public List<Piece> getTestedTLcorners() {
        return testedTLcorners;
    }

    public List<Piece> getTestedTRcorners() {
        return testedTRcorners;
    }

    public List<Piece> getTestedBRcorners() {
        return testedBRcorners;
    }

    public List<Piece> getTestedBLcorners() {
        return testedBLcorners;
    }

    public List<Piece> getTestedTopEdges() {
        return testedTopEdges;
    }

    public List<Piece> getTestedLeftEdges() {
        return testedLeftEdges;
    }

    public List<Piece> getTestedRightEdges() {
        return testedRightEdges;
    }

    public List<Piece> getTestedBottomEdges() {
        return testedBottomEdges;
    }
}
