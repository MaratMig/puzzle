import org.junit.Test;

import java.util.ArrayList;

public class PuzzleBoardTests {

    @Test
    public void testSolver(){

        ArrayList<Piece> pieces = new ArrayList<>();

        Piece piece2 = new Piece(2, new int[]{0, 0, 1, 1});
        Piece piece1 = new Piece(1, new int[]{-1, 0, 1, -1});
        Piece piece4 = new Piece(4, new int[]{-1, 0, 0, 1});
        Piece piece3 = new Piece(3, new int[]{0, -1, 1, 1});
        Piece piece6 = new Piece(6, new int[]{-1, 1, -1, -1});
        Piece piece5 = new Piece(5, new int[]{1, -1, 0, -1});
        Piece piece8 = new Piece(8, new int[]{0, -1, 1, 0});
        Piece piece7 = new Piece(7, new int[]{-1, 1, -1, 0});
        Piece piece9 = new Piece(9, new int[]{1, 1, 0, 0});


        pieces.add(piece1);
        pieces.add(piece2);
        pieces.add(piece3);
        pieces.add(piece4);
        pieces.add(piece5);
        pieces.add(piece6);
        pieces.add(piece7);
        pieces.add(piece8);
        pieces.add(piece9);

        PuzzleBoard pboard = new PuzzleBoard(pieces,3);
        pboard.tryToSolvePuzzleRectangle();
        Piece[] pboardResult = pboard.getResult();



    }

}

