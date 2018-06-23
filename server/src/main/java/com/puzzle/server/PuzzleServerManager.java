package com.puzzle.server;

import com.puzzle.common.entities.Piece;
import com.puzzle.common.jsonPojo.PuzzleSolution;
import com.puzzle.common.jsonPojo.Solution;
import com.puzzle.common.utils.ErrorBuilder;
import com.puzzle.common.utils.ErrorCollection;
import com.puzzle.common.utils.ErrorTypeEnum;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class PuzzleServerManager  {

    private PuzzleValidator puzzleValidator;
    private PuzzleSolver puzzleSolver;
    private ErrorCollection errorCollection= new ErrorCollection();;

    public PuzzleSolution startGame(List<Piece> puzzlePieces) {

        puzzleValidator = new PuzzleValidator(puzzlePieces);
        boolean isPuzzleCanBeSolved = puzzleValidator.isPuzzleValid();
        if (isPuzzleCanBeSolved) {
            return solvePuzzle(puzzlePieces);
        } else {
            return prepareValidationErrorsObject();
        }
    }

    private PuzzleSolution solvePuzzle(List<Piece> puzzlePieces) {
        Set<Integer> boardSize = puzzleValidator.getValidNumOfRows();
        boolean solutionFound = false;
        for (Integer numOfLines : boardSize) {
            puzzleSolver = new PuzzleSolver(puzzlePieces, numOfLines);
            if (puzzleSolver.tryToSolvePuzzleRectangle()) {
                Piece[] solutions = puzzleSolver.getResult();
                solutionFound = true;
                return prepareSolutionObject(solutions, numOfLines);
            }
        }
        if (!solutionFound) {
            return prepareSolverErrorsObject();
        }
        return null;
    }

    private PuzzleSolution prepareSolverErrorsObject() {
        ErrorBuilder error = new ErrorBuilder(ErrorTypeEnum.NO_SOLUTION);
        errorCollection.addError(error.getError());

        return new PuzzleSolution(false, errorCollection.getErrors());

    }

    private PuzzleSolution prepareSolutionObject(Piece[] pieces, int numOfLines) {
        Solution solution = new Solution(numOfLines, new ArrayList<>(Arrays.asList(pieces)));
        return new PuzzleSolution(true, solution);
    }

    private PuzzleSolution prepareValidationErrorsObject() {
        List<String> validatorErrors = puzzleValidator.getErrorCollection();
        return  new PuzzleSolution(false, validatorErrors);
    }
}

