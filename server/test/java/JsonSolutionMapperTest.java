import com.google.gson.JsonObject;
import com.puzzle.common.JsonSolutionMapper;
import com.puzzle.common.entities.Piece;
import org.junit.jupiter.api.Test;

class JsonSolutionMapperTest {

    private Piece[] pieces = new Piece[12];
    private int numOfLines = 3;

    @Test
    void getPuzzleSolutionAsJson() {

        createTestPieces();

        JsonSolutionMapper target = new JsonSolutionMapper(pieces, numOfLines);
        JsonObject puzzleSolution = target.getPuzzleSolutionAsJson();
        System.out.println(puzzleSolution);
    }

    private void createTestPieces() {

        pieces[0] = new Piece(2, new int[]{0, 0, 1, 1});
        pieces[1] = new Piece(1, new int[]{-1, 0, 1, -1});
        pieces[2] = new Piece(4, new int[]{-1, 0, 0, 1});
        pieces[3] = new Piece(3, new int[]{0, -1, 1, 1});
        pieces[4] = new Piece(6, new int[]{-1, 1, -1, -1});
        pieces[5] = new Piece(5, new int[]{1, -1, 0, -1});
        pieces[6] = new Piece(8, new int[]{0, -1, 1, 0});
        pieces[7] = new Piece(7, new int[]{-1, 1, -1, 0});
        pieces[8] = new Piece(9, new int[]{1, 1, 0, 0});
    }
}