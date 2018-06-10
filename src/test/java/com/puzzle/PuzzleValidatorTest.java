package com.puzzle;

import com.puzzle.entities.Corner;
import com.puzzle.entities.Piece;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.*;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

class PuzzleValidatorTest {
    @Test
    void getValidNumOfRows() {
        assertFalse(true);
    }

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
        assertFalse(true);
    }

    @Test
    void puzzleNotValidDueToMissingBLCorner() {
        assertFalse(true);
    }

    @Test
    void puzzleNotValidDueToWrongNumberOfLeftStraitEdges() {
        assertFalse(true);
    }

    @Test
    void puzzleNotValidDueToWrongNumberOfTopStraitEdges() {
        assertFalse(true);
    }

    @Test
    void puzzleNotValidDueToWrongNumberOfRightStraitEdges(){
        assertFalse(true);
    }

    @Test
    void puzzleNotValidDueToWrongNumberOfBottomStraitEdges(){
        assertFalse(true);
    }

    @Test
    void puzzleNotValidDueToNonZeroSumOfLeftAndRightEdges(){
        assertFalse(true);
    }

    @Test
    void puzzleNotValidDueToNonZeroSumOfTopAndBottomEdges(){
        assertFalse(true);
    }

    @Test
    void anyPossibleNumberOfRowsIsNotMissed(){
        List<Integer> expectedPossibleNumberOfRows = Arrays.asList(1,2,4,5,10,20,25,50,100);

        List<Piece> pieces = new ArrayList<>();
        IntStream.range(0,100).forEach(i->pieces.add(new Piece(0, new int[]{0, 0, 0, 0})));
        PuzzleValidator validator = new PuzzleValidator(pieces);

        List<Integer> actualPossibleNumbers = new ArrayList<>();
        actualPossibleNumbers.addAll(validator.getPosibleNumRows());

        assertThat(actualPossibleNumbers).asList().containsExactlyInAnyOrderElementsOf(expectedPossibleNumberOfRows);
    }

    @Test
    void possibleNumberOfRowsForPrimeNumberOfPieces(){
        List<Integer> expectedPossibleNumberOfRows = Arrays.asList(1,13);

        List<Piece> pieces = new ArrayList<>();
        IntStream.range(0,13).forEach(i->pieces.add(new Piece(0, new int[]{0, 0, 0, 0})));
        PuzzleValidator validator = new PuzzleValidator(pieces);

        List<Integer> actualPossibleNumbers = new ArrayList<>();
        actualPossibleNumbers.addAll(validator.getPosibleNumRows());

        assertThat(actualPossibleNumbers).asList().containsExactlyInAnyOrderElementsOf(expectedPossibleNumberOfRows);
    }


    @Test
    void puzzleNotValidDueToMissingPossibleNumberOfLines(){
        assertFalse(true);
    }
}