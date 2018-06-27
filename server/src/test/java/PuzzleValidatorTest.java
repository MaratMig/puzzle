import com.puzzle.common.entities.Corner;
import com.puzzle.common.entities.Piece;
import com.puzzle.server.PuzzleValidator;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

class PuzzleValidatorTest {

    @Test
    void puzzleNotValidDueToMissingTLCorner() {
        ArrayList<Piece> pieces = new ArrayList<>();

        Piece piece1 = new Piece(1, new int[]{1, 0, 0, 1});
        Piece piece2 = new Piece(2, new int[]{1, 0, 0, 0});
        Piece piece3 = new Piece(3, new int[]{1, -1, 0, 0});
        Piece piece4 = new Piece(4, new int[]{0, 1, -1, 0});

        pieces.add(piece1);
        pieces.add(piece2);
        pieces.add(piece3);
        pieces.add(piece4);

        PuzzleValidator validator = new PuzzleValidator(pieces);

        assertFalse(validator.isPuzzleValid());
        assertThat(validator.findMissingCorners()).asList().containsOnly(Corner.TL);
    }

    @Test
    void puzzleNotValidDueToMissingTRCorner() {
        ArrayList<Piece> pieces = new ArrayList<>();

        Piece piece1 = new Piece(1, new int[]{0, 0, -1, 1});
        Piece piece2 = new Piece(2, new int[]{1, 0, 1, 0});
        Piece piece3 = new Piece(3, new int[]{1, -1, 0, 0});
        Piece piece4 = new Piece(4, new int[]{0, 1, -1, 0});

        pieces.add(piece1);
        pieces.add(piece2);
        pieces.add(piece3);
        pieces.add(piece4);

        PuzzleValidator validator = new PuzzleValidator(pieces);

        assertFalse(validator.isPuzzleValid());
        assertThat(validator.findMissingCorners()).asList().containsOnly(Corner.TR);
    }

    @Test
    void puzzleNotValidDueToMissingBRCorner() {
        ArrayList<Piece> pieces = new ArrayList<>();

        Piece piece1 = new Piece(1, new int[]{0, 0, -1, 1});
        Piece piece2 = new Piece(2, new int[]{1, 0, 0, -1});
        Piece piece3 = new Piece(3, new int[]{1, -1, 1, 0});
        Piece piece4 = new Piece(4, new int[]{0, 1, -1, 0});

        pieces.add(piece1);
        pieces.add(piece2);
        pieces.add(piece3);
        pieces.add(piece4);

        PuzzleValidator validator = new PuzzleValidator(pieces);

        assertFalse(validator.isPuzzleValid());
        assertThat(validator.findMissingCorners()).asList().containsOnly(Corner.BR);
    }

    @Test
    void puzzleNotValidDueToMissingBLCorner() {
        ArrayList<Piece> pieces = new ArrayList<>();

        Piece piece1 = new Piece(1, new int[]{0, 0, -1, 1});
        Piece piece2 = new Piece(2, new int[]{1, 0, 0, -1});
        Piece piece3 = new Piece(3, new int[]{1, -1, 0, 0});
        Piece piece4 = new Piece(4, new int[]{1, 1, -1, 0});

        pieces.add(piece1);
        pieces.add(piece2);
        pieces.add(piece3);
        pieces.add(piece4);

        PuzzleValidator validator = new PuzzleValidator(pieces);

        assertFalse(validator.isPuzzleValid());
        assertThat(validator.findMissingCorners()).asList().containsOnly(Corner.BL);

    }

    @Test
    void puzzleNotValidDueToNonZeroSumOfLeftAndRightSides() {
        ArrayList<Piece> pieces = new ArrayList<>();

        Piece piece1 = new Piece(1, new int[]{0, 0, -1, 1});
        Piece piece2 = new Piece(2, new int[]{1, 0, 0, -1});
        Piece piece3 = new Piece(3, new int[]{1, -1, 0, 0});
        Piece piece4 = new Piece(4, new int[]{0, 1, -1, 0});
        Piece piece5 = new Piece(3, new int[]{1, -1, 0, 0});
        Piece piece6 = new Piece(4, new int[]{0, 1, 0, 0});

        pieces.add(piece1);
        pieces.add(piece2);
        pieces.add(piece3);
        pieces.add(piece4);
        pieces.add(piece5);
        pieces.add(piece6);

        PuzzleValidator validator = new PuzzleValidator(pieces);
        assertFalse(validator.isPuzzleValid());
    }

    @Test
    void puzzleNotValidDueToDueToNonZeroSumOfTopAndBottomSides() {
        ArrayList<Piece> pieces = new ArrayList<>();

        Piece piece1 = new Piece(1, new int[]{0, 0, -1, 1});
        Piece piece2 = new Piece(2, new int[]{1, 0, 0, 1});
        Piece piece3 = new Piece(3, new int[]{1, -1, 0, 0});
        Piece piece4 = new Piece(4, new int[]{0, 1, -1, 0});
        Piece piece5 = new Piece(3, new int[]{1, -1, 0, 0});
        Piece piece6 = new Piece(4, new int[]{0, 1, -1, 0});

        pieces.add(piece1);
        pieces.add(piece2);
        pieces.add(piece3);
        pieces.add(piece4);
        pieces.add(piece5);
        pieces.add(piece6);

        PuzzleValidator validator = new PuzzleValidator(pieces);
        assertFalse(validator.isPuzzleValid());
    }

    @Test
    void anyPossibleNumberOfRowsIsNotMissed(){

        List<Piece> pieces = new ArrayList<>();
        IntStream.range(0,100).forEach(i->pieces.add(new Piece(0, new int[]{0, 0, 0, 0})));
        PuzzleValidator validator = new PuzzleValidator(pieces);

        List<Integer> actualPossibleNumbers = new ArrayList<>();
        actualPossibleNumbers.addAll(validator.getPosibleNumRows());

        assertThat(actualPossibleNumbers).asList().containsExactlyInAnyOrder(1,2,4,5,10,20,25,50,100);
    }

    @Test
    void possibleNumberOfRowsForPrimeNumberOfPieces(){

        List<Piece> pieces = new ArrayList<>();
        IntStream.range(0,13).forEach(i->pieces.add(new Piece(0, new int[]{0, 0, 0, 0})));
        PuzzleValidator validator = new PuzzleValidator(pieces);

        List<Integer> actualPossibleNumbers = new ArrayList<>();
        actualPossibleNumbers.addAll(validator.getPosibleNumRows());

        assertThat(actualPossibleNumbers).asList().containsExactlyInAnyOrder(1,13);
    }


    @Test
    void puzzleNotValidDueToMissingPossibleNumberOfLines(){
/*
        public Set<Integer> getValidNumOfRows() {
            Set<Integer> possibleNumOfLines = getPosibleNumRows();
            Set<Integer> numOfLinesAfterElimination = new HashSet<>();

            for (int numOfLines : possibleNumOfLines) {
                int numOfCol = pieces.size() / numOfLines;

                if (getNumOfStraitTopEdges() >= numOfCol && getNumOfStraitBottomEdges() >= numOfCol
                        && getNumOfStraitRightEdges() >= numOfLines && getNumOfStraitLeftEdges() >= numOfLines) {
                    numOfLinesAfterElimination.add(numOfLines);
                }
            }
            return numOfLinesAfterElimination;
        }*/
        ArrayList<Piece> pieces = new ArrayList<>();

        Piece piece1 = new Piece(1, new int[]{0, 0, -1, 0});
        Piece piece2 = new Piece(2, new int[]{1, 0, 0, 1});
        Piece piece3 = new Piece(3, new int[]{1, -1, 0, 0});
        Piece piece4 = new Piece(4, new int[]{0, 1, -1, 0});
        Piece piece5 = new Piece(3, new int[]{0, -1, 0, 0});

        pieces.add(piece1);
        pieces.add(piece2);
        pieces.add(piece3);
        pieces.add(piece4);
        pieces.add(piece5);

        PuzzleValidator validator = new PuzzleValidator(pieces);
        assertFalse(validator.isPuzzleValid());
    }
}