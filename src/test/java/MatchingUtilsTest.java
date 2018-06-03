import com.puzzle.utils.MatchingUtils;
import com.puzzle.Piece;
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

    //Can be parametrized
    @Test
    void isPlaceNotOnEdges() {
        assertTrue(MatchingUtils.isPlaceNotOnEdges(5, 9, 3));
    }

    //0 1 2 3
    //4 5 6 7
    //8 9 10 11
    //place = 3, col = 4;

   /* @ParameterizedTest
    @CsvSource({ "foo, 1", "bar, 2", "'baz, qux', 3" })
    void testWithCsvSource(String first, int second) {
        assertNotNull(first);
        assertNotEquals(0, second);
    }*/
    @ParameterizedTest
    @CsvSource({"9, 12, 4","10, 12, 4"})
    void isBottomEdge(int place, int numOfPieces, int numOfColumns) {
        assertTrue(MatchingUtils.isBottomEdge(place, numOfPieces, numOfColumns));
    }

    @ParameterizedTest
    @CsvSource({"1, 12, 4","2, 12, 4"})
    void isTopEdge(int place, int numOfPieces, int numOfColumns) {
        assertTrue(MatchingUtils.isBottomEdge(place, numOfPieces, numOfColumns));
    }

    @Test
    void isLeftEdge() {
    }

    //0  1  2  3
    //4  5  6  7
    //8  9  10 11
    //12 13 14 15
    @ParameterizedTest
    @CsvSource({"4, 16, 4","8, 16, 4"})
    void isLeftEdge(int place, int numOfPieces, int numOfColumns) {
        assertTrue(MatchingUtils.isBottomEdge(place, numOfPieces, numOfColumns));
    }

    @Test
    void isRightEdge() {

    }

    @Test
    void isBLcorner() {
    }

    @Test
    void isTRcorner() {
    }

    @Test
    void isBRcorner() {
    }

    @Test
    void findTRCorners() {

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