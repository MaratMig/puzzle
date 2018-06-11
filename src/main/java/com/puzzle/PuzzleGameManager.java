package com.puzzle;

import com.puzzle.entities.Piece;
import com.puzzle.fileHandlers.OutputFile;
import com.puzzle.fileHandlers.Parser;
import com.puzzle.utils.ErrorBuilder;
import com.puzzle.utils.ErrorCollection;
import com.puzzle.utils.ErrorTypeEnum;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PuzzleGameManager implements Runnable {
    private Path fileToHandle;
    private Parser parser;
    private OutputFile outPutFile;
    private PuzzleValidator puzzleValidator;
    private PuzzleSolver puzzleSolver;
    private ErrorCollection errorCollection= new ErrorCollection();;

    public PuzzleGameManager(Path fileToHandle) {
        this.fileToHandle = fileToHandle;
        outPutFile = new OutputFile(fileToHandle.getParent().resolve("output"));
    }

    public void startGame() {
        ArrayList<Piece> puzzlePieces = startParser();
        if (puzzlePieces != null) {
            puzzleValidator = new PuzzleValidator(puzzlePieces);
            boolean isPuzzleCanBeSolved = puzzleValidator.isPuzzleValid();
            if (isPuzzleCanBeSolved) {
                solvePuzzle(puzzlePieces);
            } else {
                printValidationErrors();
            }
        } else {
            printParserErrors();
        }
    }

    private void solvePuzzle(ArrayList<Piece> puzzlePieces) {
        Set<Integer> boardSize = puzzleValidator.getValidNumOfRows();
        boolean solutionFound = false;
        for (Integer numOfLines : boardSize) {
            puzzleSolver = new PuzzleSolver(puzzlePieces, numOfLines);
            if (puzzleSolver.tryToSolvePuzzleRectangle()) {
                Piece[] solutions = puzzleSolver.getResult();

                printPuzzle(solutions, numOfLines);
                solutionFound = true;
                break;
            }
        }
        if (!solutionFound) {
            printSolverErrors();
        }
    }

    private void printSolverErrors() {
        ErrorBuilder error = new ErrorBuilder(ErrorTypeEnum.NO_SOLUTION);
        errorCollection.addError(error.getError());
        printValidationErrors();
    }

    private void printParserErrors() {
        ArrayList<String> inputValidationErrors = parser.getInputValidationErrors();
        StringBuilder outPut = new StringBuilder();
        inputValidationErrors.stream().forEach(e -> {
            outPut.append(e);
            outPut.append("\n");
            System.out.println(e);
        });
        outPutFile.writeResultToFile(fileToHandle, outPut.toString());
    }

    private void printPuzzle(Piece[] pieces, int numOfLines) {
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
        outPutFile.writeResultToFile(fileToHandle, outPut.toString());
    }

    private ArrayList<Piece> startParser() {
        parser = new Parser(fileToHandle);
        ArrayList<Piece> puzzelPiecesInput = parser.parse();
        return puzzelPiecesInput;
    }

    private void printValidationErrors() {
        StringBuilder outPut = new StringBuilder();
        List<String> validatorErrors = puzzleValidator.getErrorCollection();
        validatorErrors.stream().forEach(e -> {
            outPut.append(e);
            outPut.append("\n");
            System.out.println(e);
        });
        outPutFile.writeResultToFile(fileToHandle, outPut.toString());

    }


    @Override
    public void run() {
        startGame();
    }

}
