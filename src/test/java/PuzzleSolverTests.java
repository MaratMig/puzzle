import com.puzzle.entities.Piece;
import com.puzzle.PuzzleSolver;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PuzzleSolverTests {

    TestingUtils testingUtils = new TestingUtils();

    @Test
    public void testSolverSolutionExists(){

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

        PuzzleSolver puzzleSolver = new PuzzleSolver(pieces,3);
        puzzleSolver.tryToSolvePuzzleRectangle();
        Piece[] result = puzzleSolver.getResult();

        assertTrue(testingUtils.isPuzzleSolved(result, 3));
    }

    @Test
    public void solvePuzzleOfOneLine(){

        ArrayList<Piece> pieces = new ArrayList<>();

        Piece piece1 = new Piece(2, new int[]{0, 0, 1, 0});
        Piece piece2 = new Piece(1, new int[]{-1, 0, 1, 0});
        Piece piece3 = new Piece(4, new int[]{-1, 0, 0, 0});

        pieces.add(piece3);
        pieces.add(piece2);
        pieces.add(piece1);

        PuzzleSolver puzzleSolver = new PuzzleSolver(pieces,1);
        puzzleSolver.tryToSolvePuzzleRectangle();
        Piece[] result = puzzleSolver.getResult();

        assertTrue(testingUtils.isPuzzleSolved(result, 1));
    }

    @Test
    public void solvePuzzleOfOneColumn(){

        ArrayList<Piece> pieces = new ArrayList<>();

        Piece piece1 = new Piece(2, new int[]{0, 0, 0, 1});
        Piece piece2 = new Piece(1, new int[]{0, -1, 0, -1});
        Piece piece3 = new Piece(4, new int[]{0, 1, 0, 0});

        pieces.add(piece2);
        pieces.add(piece3);
        pieces.add(piece1);

        PuzzleSolver puzzleSolver = new PuzzleSolver(pieces,3);
        puzzleSolver.tryToSolvePuzzleRectangle();
        Piece[] result = puzzleSolver.getResult();

        assertTrue(testingUtils.isPuzzleSolved(result, 3));
    }

    @Test
    public void puzzleWithNoSolution() {

        ArrayList<Piece> pieces = new ArrayList<>();

        Piece piece1 = new Piece(2, new int[]{0, 0, 0, 1});
        Piece piece2 = new Piece(1, new int[]{0, 1, 0, -1});
        Piece piece3 = new Piece(4, new int[]{0, 1, 0, 0});

        pieces.add(piece2);
        pieces.add(piece3);
        pieces.add(piece1);


        PuzzleSolver puzzleSolver = new PuzzleSolver(pieces, 1);
        boolean isSolved = puzzleSolver.tryToSolvePuzzleRectangle();
        Piece[] result = puzzleSolver.getResult();

        assertFalse(isSolved);
        List<Piece> resultAsList = Arrays.asList(result);

        //According to solver implementation if there is no solution, result array will contain null values.
        assertTrue(resultAsList.contains(null));
    }

    @Test
    public void puzzleRequiresStepBackTillFirstSelectedPiece(){
        ArrayList<Piece> pieces = new ArrayList<>();

        Piece piece1 = new Piece(1, new int[]{0, 0, 0, 0});
        Piece piece2 = new Piece(2, new int[]{1, 0, 0, 0});
        Piece piece3 = new Piece(3, new int[]{0, -1, 0, 0});
        Piece piece4 = new Piece(4, new int[]{0, 0, -1, 1});

        pieces.add(piece1);
        pieces.add(piece2);
        pieces.add(piece3);
        pieces.add(piece4);

        PuzzleSolver puzzleSolver = new PuzzleSolver(pieces,2);
        puzzleSolver.tryToSolvePuzzleRectangle();
        Piece[] result = puzzleSolver.getResult();

        assertTrue(testingUtils.isPuzzleSolved(result, 2));
    }
}

