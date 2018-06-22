package com.puzzle.server;



import com.google.gson.JsonObject;
import com.puzzle.utils.JsonErrorsMapper;
import com.puzzle.utils.JsonSolutionMapper;
import com.puzzle.utils.entities.Piece;
import com.puzzle.utils.utils.ErrorBuilder;
import com.puzzle.utils.utils.ErrorCollection;
import com.puzzle.utils.utils.ErrorTypeEnum;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PuzzleServerManager  {

    private PuzzleValidator puzzleValidator;
    private PuzzleSolver puzzleSolver;
    private ErrorCollection errorCollection= new ErrorCollection();;



    public JsonObject startGame(ArrayList<Piece> puzzlePieces) {

        puzzleValidator = new PuzzleValidator(puzzlePieces);
        boolean isPuzzleCanBeSolved = puzzleValidator.isPuzzleValid();
        if (isPuzzleCanBeSolved) {
            return solvePuzzle(puzzlePieces);
        } else {
            return printValidationErrors();
        }
    }

    private JsonObject solvePuzzle(ArrayList<Piece> puzzlePieces) {
        Set<Integer> boardSize = puzzleValidator.getValidNumOfRows();
        boolean solutionFound = false;
        for (Integer numOfLines : boardSize) {
            puzzleSolver = new PuzzleSolver(puzzlePieces, numOfLines);
            if (puzzleSolver.tryToSolvePuzzleRectangleNew()) {
                //Piece[] solutions = puzzleSolver.getResult();

                solutionFound = true;
                //return printPuzzle(solutions, numOfLines);
//                break;
            }
        }
        if (!solutionFound) {
            return printSolverErrors();
        }
        return null;
    }

    private JsonObject printSolverErrors() {
        ErrorBuilder error = new ErrorBuilder(ErrorTypeEnum.NO_SOLUTION);
        errorCollection.addError(error.getError());

        // TODO create json object - review
        JsonObject noSolution = new JsonObject();
        JsonObject solution = new JsonObject();
        solution.addProperty("solutionExist", false);
        noSolution.add("solution", solution);

        return noSolution;
    }

    private JsonObject printPuzzle(Piece[] pieces, int numOfLines) {
        StringBuffer outPut = new StringBuffer();
        outPut.append(String.format("Solution for %s lines:", numOfLines));
        outPut.append("\n\n");
        int col = pieces.length / numOfLines;
        for (int i = 0; i < pieces.length; i++) {
            outPut.append(pieces[i].toString() + " ");
            if ((i % col == col - 1 && col != 1) || col == pieces.length) {
                outPut.append("\n");
            }
        }
        System.out.println(outPut.toString());
        //TODO create json object - review
        return new JsonSolutionMapper(pieces, numOfLines).getPuzzleSolutionAsJson();
    }

    private JsonObject printValidationErrors() {
        StringBuilder outPut = new StringBuilder();
        List<String> validatorErrors = puzzleValidator.getErrorCollection();
        validatorErrors.stream().forEach(e -> {
            outPut.append(e);
            outPut.append("\n");
            System.out.println(e);
        });

        //TODO create json Object - review
        JsonErrorsMapper errorsMapper = new JsonErrorsMapper(validatorErrors);
        return  errorsMapper.getErrorsAsJson();
    }

    public static void main(String[] args){
        com.puzzle.server.Parser puzzleParser = new com.puzzle.server.Parser(Paths.get("C:\\Ella\\solvable5x8_2.txt"));

        PuzzleServerManager puzzleServerManager = new PuzzleServerManager();
        puzzleServerManager.startGame(puzzleParser.parse());

    }
}

