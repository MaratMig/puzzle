import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class PuzzleGameTests {



    private static final String resourceDir= ("src/test/resources/puzzleBoards/");

    @Test
    public void dashAfterPieceDetails() throws Exception {
        PuzzleGame game = new PuzzleGame(resourceDir + "9elements.txt");
        game.startGame();

    }
}
