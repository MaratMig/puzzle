package com.puzzle;

import com.puzzle.fileHandlers.OutputFile;
import com.puzzle.fileHandlers.Parser;
import com.puzzle.utils.ErrorBuilder;
import com.puzzle.utils.ErrorTypeEnum;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PuzzleGameManager {
    private static Path currentInputFile;
    private Parser parser;
    private OutputFile outPutFile;
    PuzzleValidator puzzleValidator;

    private static List<String> exceptionCollection = new ArrayList<>();


    public PuzzleGameManager(Path currentInputFile) {
        this.currentInputFile = currentInputFile;
        outPutFile = new OutputFile(currentInputFile.getParent().resolve("output"));

    }

    public void startGame()  {
        ArrayList<Piece> puzzlePieces = startParser();
        if (puzzlePieces!=null) {
            //boolean isPuzzleCanBeSolved = validateBeforeSolver(puzzlePieces);
            puzzleValidator = new PuzzleValidator(puzzlePieces);
            boolean isPuzzleCanBeSolved = puzzleValidator.isPuzzleValid();
            if (isPuzzleCanBeSolved) {
                solvePuzzle(puzzlePieces);
            }
            else {
                printValidationErrors();
            }
        }else {
                printParserErrors();
            }
    }

    private void solvePuzzle(ArrayList<Piece> puzzlePieces)  {
        Set<Integer> boardSize = puzzleValidator.getValidNumOfRows();
        boolean solutionFound = false;
        for (Integer numOfLines : boardSize) {
            PuzzleSolver puzzleSolver = new PuzzleSolver(puzzlePieces, numOfLines);
            if (puzzleSolver.tryToSolvePuzzleRectangle()) {
                Piece[] solutions = puzzleSolver.getResult();

                printPuzzle(solutions, numOfLines);
                solutionFound = true;
                break;
            }
        }
        if (!solutionFound) {printSolverErrors();}
    }

    private void printSolverErrors() {
        ErrorBuilder error = new ErrorBuilder(ErrorTypeEnum.NO_SOLUTION);
        PuzzleGameManager.addException(error.getError());
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
        outPutFile.writeResultToFile(currentInputFile, outPut.toString());
    }

    private void printPuzzle(Piece[] pieces, int numOfLines)  {
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
        outPutFile.writeResultToFile(currentInputFile, outPut.toString());
    }

    private ArrayList<Piece> startParser()  {
        parser = new Parser(currentInputFile);
        ArrayList<Piece> puzzelPiecesInput = parser.parse();
        return puzzelPiecesInput;
    }


    /*private boolean validateBeforeSolver(List<Piece> pieces) {
        return ValidationUtils.isPuzzleValid(pieces);
    }
*/
    public static void addException(String s) {
        exceptionCollection.add(s);
    }

    private void printValidationErrors()  {
        StringBuilder outPut = new StringBuilder();
        exceptionCollection.stream().forEach(e -> {
                                                    outPut.append(e);
                                                    outPut.append("\n");
                                                    System.out.println(e);
                                                });
        outPutFile.writeResultToFile(currentInputFile, outPut.toString());

    }


}
