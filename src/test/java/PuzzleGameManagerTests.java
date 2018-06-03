import com.puzzle.PuzzleGameManager;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PuzzleGameManagerTests {



    private static final String resourceDir= ("src/test/resources/puzzleBoards/");

    @Test
    public void dashAfterPieceDetails() throws Exception {
        PuzzleGameManager game = new PuzzleGameManager(resourceDir + "9elements.txt");
        game.startGame();

    }
}
