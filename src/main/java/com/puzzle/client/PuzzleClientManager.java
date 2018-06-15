package com.puzzle.client;

import com.google.gson.JsonObject;
import com.puzzle.common.entities.Piece;
import com.puzzle.common.utils.ErrorCollection;
import com.puzzle.fileHandlers.OutputFile;
import com.puzzle.fileHandlers.Parser;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PuzzleClientManager implements Runnable {
    private Path fileToHandle;
    private Parser parser;
    private OutputFile outPutFile;


    public PuzzleClientManager(Path fileToHandle) {
        this.fileToHandle = fileToHandle;
        outPutFile = new OutputFile(fileToHandle.getParent().resolve("output"));
    }

    public void startClient() {
        ArrayList<Piece> puzzlePieces = startParser();
        if (puzzlePieces != null) {
            //covert pieces list to json
            //TODO add code that conver pieces list to JSON object
            //this piece of code is only for testing purpose
            JsonObject test = new JsonObject();
            test.addProperty("pieces","piece1");

            //send json to server
            Connector connector = new Connector();
            String result = connector.connectionToServer(1, test);
            System.out.println(result);

            //TODO convert result back to json object
            //TODO get relevant data from json object to continue bellow flow
            //result received as string we should convert it back to json object
            //get data from above object and call to relevant method (use below code)


            ErrorCollection errorCollection = null;
            Piece[] pieces = new Piece[0];

            int numOfLines=0;

            boolean solutionExists = true;

            if (solutionExists) {
                printPuzzle(pieces,  numOfLines);
            } else {
                printServerErrors(errorCollection);
            }
        } else {
            printParserErrors();
        }
    }



    private void printServerErrors(ErrorCollection errorCollection) {
        List<String> inputValidationErrors = errorCollection.getErrors();
        StringBuilder outPut = new StringBuilder();
        inputValidationErrors.stream().forEach(e -> {
            outPut.append(e);
            outPut.append("\n");
            System.out.println(e);
        });
        outPutFile.writeResultToFile(fileToHandle, outPut.toString());
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



    @Override
    public void run() {
        startClient();
    }

}
