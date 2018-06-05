package com.puzzle;

import com.puzzle.fileHandlers.OutputFile;
import com.puzzle.fileHandlers.Parser;

import java.io.IOException;
import java.util.*;
import java.util.List;

public class PuzzleGameManager {
    private String fileName;
    private Parser parser;
    private Map<String, Boolean> resultCollector = new HashMap<>();


    private static List<String> exceptionCollection = new ArrayList<>();


    public PuzzleGameManager(String fileName) {
        this.fileName = fileName;

    }

    public void startGame() throws Exception {
        ArrayList<Piece> puzzlePieces = startParser();
        if (puzzlePieces!=null) {
            //boolean isPuzzleCanBeSolved = validateBeforeSolver(puzzlePieces);
            PuzzleValidator puzzleValidator = new PuzzleValidator(puzzlePieces);
            boolean isPuzzleCanBeSolved = puzzleValidator.isPuzzleValid();
            if (isPuzzleCanBeSolved) {
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
            }
            else {
                printExceptionCollection();
            }
        }else {
                printErrorsFromParser();
            }
    }




    private void addResultToCollector(String s, boolean b) {

        resultCollector.put(s, b);

    }


    private void printErrorsFromParser() throws IOException {
        ArrayList<String> inputValidationErrors = parser.getInputValidationErrors();
        StringBuilder outPut = new StringBuilder();
        inputValidationErrors.stream().forEach(e -> {
            outPut.append(e);
            System.out.println(e);
        });
        OutputFile.writeResultToFile(outPut.toString());
    }

    public static void printPuzzle(Piece[] pieces, int numOfLines) throws IOException {
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
        OutputFile.writeResultToFile(outPut.toString());
    }

    private List<Piece> convertStringListToPieces(ArrayList<String> stringsList) {
        List<Piece> listPieces = new ArrayList<>();
        for (String strPieces : stringsList) {
            List<Integer> nums = new ArrayList<>();
            int counter = 1;
            for (int i = 1; i <= strPieces.length(); i++) {
                if (strPieces.length() >= 5 && !(nums.size() == 4)) {
                    if (String.valueOf(strPieces.charAt(strPieces.length() - i - 1)).contains("-")) {
                        StringBuilder s = new StringBuilder().append(strPieces.charAt(strPieces.length() - i - 1)).append(strPieces.charAt(strPieces.length() - i));
                        nums.add(Integer.parseInt(s.toString()));
                        i++;
                    } else {
                        nums.add(Character.getNumericValue(strPieces.charAt(strPieces.length() - i)));
                    }
                    counter++;
                } else {
                    nums.add(Integer.parseInt(strPieces.substring(0, strPieces.length() - i + 1)));
                    break;
                }
            }
            int[] sidesData = new int[4];
            sidesData[3] = nums.get(0);
            sidesData[2] = nums.get(1);
            sidesData[1] = nums.get(2);
            sidesData[0] = nums.get(3);
            Piece piece = new Piece(nums.get(4), sidesData);
            listPieces.add(piece);
        }

        return listPieces;
    }

    private boolean validateParserOutput(ArrayList<String> stringsList) {
        return true;
    }

    private ArrayList<Piece> startParser() throws IOException {
        parser = new Parser(fileName);
        ArrayList<Piece> puzzelPiecesInput = parser.parse();
        return puzzelPiecesInput;
    }

    private ArrayList<Piece> convertLinestoPieces(Map<Integer, int[]> parsedPieces) {
        ArrayList<Piece> puzzlePieces = new ArrayList<>();

        for (Map.Entry<Integer, int[]> entry : parsedPieces.entrySet()) {
            Piece piece = new Piece(entry.getKey(), entry.getValue());
            puzzlePieces.add(piece);
        }
        return puzzlePieces;
    }

    /*private boolean validateBeforeSolver(List<Piece> pieces) {
        return ValidationUtils.isPuzzleValid(pieces);
    }
*/
    public static void addException(String s) {
        exceptionCollection.add(s);
    }

    public static void  printExceptionCollection() throws IOException {
        StringBuilder outPut = new StringBuilder();
        exceptionCollection.stream().forEach(e -> {
                                                    outPut.append(e);
                                                    System.out.println(e);
                                                });
        OutputFile.writeResultToFile(outPut.toString());

    }


}
