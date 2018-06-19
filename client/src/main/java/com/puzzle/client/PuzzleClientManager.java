package com.puzzle.client;


import com.google.gson.Gson;
import com.puzzle.client.fileHandlers.OutputFile;
import com.puzzle.client.fileHandlers.Parser;
import com.puzzle.common.entities.Piece;
import com.puzzle.common.jsonPojo.ClientRequest;
import com.puzzle.common.jsonPojo.Pieces;
import com.puzzle.common.jsonPojo.ServerResponse;
import com.puzzle.common.utils.ErrorCollection;

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

            //create json object
            ClientRequest clientRequest = new ClientRequest(new Pieces(puzzlePieces));
            String jsonForSending = new Gson().toJson(clientRequest);

            //send json to server
            Connector connector = new Connector();
            String result = connector.connectionToServer(1, jsonForSending);
            ServerResponse serverResponse = new Gson().fromJson(result, ServerResponse.class);

            if (serverResponse.getPuzzleSolution().isSolutionExists()) {
                int numOfLines = serverResponse.getPuzzleSolution().getSolution().getRows();
                List<Piece> pieces = serverResponse.getPuzzleSolution().getSolution().getPieces();
                printPuzzleFromJson(pieces,  numOfLines);
            } else {
                printServerErrorsFromJson(serverResponse.getPuzzleSolution().getErrors());
            }
        } else {
            printParserErrors();
        }
    }

    private void printServerErrorsFromJson(List<String> errors) {
        StringBuilder outPut = new StringBuilder();
        errors.stream().forEach(e -> {
            outPut.append(e);
            outPut.append("\n");
            System.out.println(e);
        });
        outPutFile.writeResultToFile(fileToHandle, outPut.toString());
    }

    private void printPuzzleFromJson(List<Piece> pieces, int numOfLines) {
        StringBuffer outPut = new StringBuffer();
        outPut.append(String.format("Solution for %s lines:", numOfLines));
        outPut.append("\n\n");
        int col = pieces.size() / numOfLines;
        for (int i = 0; i < pieces.size(); i++) {
            outPut.append(pieces.get(i).getId() + " ");
            if ((i % col == col - 1 && col != 1) || col == pieces.size()) {
                outPut.append("\n");
            }
        }
        System.out.println(outPut.toString());
        outPutFile.writeResultToFile(fileToHandle, outPut.toString());
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