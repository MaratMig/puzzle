package com.puzzle;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

class PuzzleValidatorTest {
    @Test
    void getValidNumOfRows() {
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
    }

    @Test
    void puzzleNotValidDueToMissingBRCorner() {
    }

    @Test
    void puzzleNotValidDueToMissingBLCorner() {
    }

    @Test
    void puzzleNotValidDueToWrongNumberOfLeftStraitEdges(){

    }

    @Test
    void puzzleNotValidDueToWrongNumberOfTopStraitEdges(){

    }

}