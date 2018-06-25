import com.puzzle.common.MatchingUtils;
import com.puzzle.common.entities.Piece;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatchingUtilsTest {

    private static TestingUtils testingUtils = new TestingUtils();
    private static List<Piece> testedPieces;

    @BeforeAll
    static void createPiecesListForTesting() {
        testedPieces = testingUtils.getTestedPieces();
    }

    @Test
    void getAllBothTR_TL() {
    }

    @Test
    void getAllBothTR_BR() {
    }

    @Test
    void getAllMatchingForTL() {
        List<Piece> actualTLCorners = MatchingUtils.getAllMatchingForTL(testedPieces);

        assertEquals(testingUtils.getTestedTLcorners().size(), actualTLCorners.size());
        testingUtils.getTestedTLcorners().stream().forEach(piece -> assertTrue(actualTLCorners.contains(piece)));
    }

    //0 1 2 3
    //4 5 6 7
    //8 9 10 11
    //numOfPieces = 12, col = 4;
    @Test
    void isPlaceNotOnEdges() {
        assertTrue(MatchingUtils.isPlaceNotOnEdges(5, 12, 4));
    }

    @ParameterizedTest
    @CsvSource({"9, 12, 4","10, 12, 4"})
    void isBottomEdge(int place, int numOfPieces, int numOfColumns) {
        assertTrue(MatchingUtils.isBottomEdge(place, numOfPieces, numOfColumns));
    }

    @ParameterizedTest
    @CsvSource({"1, 4","2, 4"})
    void isTopEdge(int place, int numOfColumns) {
        assertTrue(MatchingUtils.isTopEdge(place, numOfColumns));
    }

    //0  1  2  3
    //4  5  6  7
    //8  9  10 11
    //12 13 14 15
    @ParameterizedTest
    @CsvSource({"4, 16, 4","8, 16, 4"})
    void isLeftEdge(int place, int numOfPieces, int numOfColumns) {
        assertTrue(MatchingUtils.isLeftEdge(place, numOfPieces, numOfColumns));
    }

    @ParameterizedTest
    @CsvSource({"7, 16, 4","11, 16, 4"})
    void isRightEdge(int place, int numOfLines, int col) {
        assertTrue(MatchingUtils.isRightEdge(place, numOfLines, col));
    }

    @ParameterizedTest
    @CsvSource({"15, 16, 4","3, 16, 4"})
    void rightCornerIsNotRecognizedAsRightEdge(int place, int numOfPieces, int numOfColumns) {
        assertFalse(MatchingUtils.isRightEdge(place, numOfPieces, numOfColumns));
    }

    @ParameterizedTest
    @CsvSource({"12, 16, 4","6, 9, 3"})
    void isBLcorner(int place, int numOfLines, int col) {
        assertTrue(MatchingUtils.isBLcorner(place, numOfLines, col));
    }

    @ParameterizedTest
    @CsvSource({"1, 16, 4","1, 9, 3"})
    void isBLcornerNegative(int place, int numOfLines, int col) {
        assertFalse(MatchingUtils.isBLcorner(place, numOfLines, col));
    }

    @ParameterizedTest
    @CsvSource({"3, 4","2, 3"})
    void isTRcorner(int place, int col) {
        assertTrue(MatchingUtils.isTRcorner(place, col));
    }

    @ParameterizedTest
    @CsvSource({"15, 16","8, 9"})
    void isBRcorner(int place, int numOfPieces) {
        assertTrue(MatchingUtils.isTRcorner(place, numOfPieces));
    }

    @Test
    void findTRCorners() {
        //List<Piece> actualTRCorners = MatchingUtils.findTRCorners(testedPieces);

        //assertEquals(testingUtils.getTestedTLcorners().size(), actualTLCorners.size());
        //testingUtils.getTestedTLcorners().stream().forEach(piece -> assertTrue(actualTLCorners.contains(piece)));
    }

    @Test
    void findBRCorners() {
    }

    @Test
    void findBLCorners() {
    }

    @Test
    void findMatchForTopEdge() {
    }

    @Test
    void findMatchForBottomEdge() {
    }

    @Test
    void findMatchForLeftEdge() {
    }

    @Test
    void findMatchForRightEdge() {
    }

    @Test
    void findMatchForInternalPiece() {
    }
}