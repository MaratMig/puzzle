package com.puzzle.client;


import com.google.gson.Gson;
import com.puzzle.client.fileHandlers.OutputFileGenerator;
import com.puzzle.client.fileHandlers.Parser;
import com.puzzle.common.entities.Piece;
import com.puzzle.common.jsonPojo.ClientRequest;
import com.puzzle.common.jsonPojo.Pieces;
import com.puzzle.common.jsonPojo.ServerResponse;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PuzzleClientRunner implements Runnable {
    private Path fileToHandle;
    private Parser parser;


    public PuzzleClientRunner(Path fileToHandle) {
        this.fileToHandle = fileToHandle;
    }

    public void startClient() {
        List<Piece> puzzlePieces = startParser();
        if (puzzlePieces != null) {

            //create json object
            ClientRequest clientRequest = new ClientRequest(new Pieces(puzzlePieces));
            String jsonForSending = new Gson().toJson(clientRequest);

            //send json to server
            Connector connector = new Connector();
            String result = connector.connectionToServer(jsonForSending);
            ServerResponse serverResponse = null;
            try {
                serverResponse = new Gson().fromJson(result, ServerResponse.class);
            }catch (Exception e){
                throw new RuntimeException("client could not parse server response");
            }


            if (serverResponse.getPuzzleSolution().isSolutionExists()) {
                int numOfLines = serverResponse.getPuzzleSolution().getSolution().getRows();
                List<Piece> pieces = serverResponse.getPuzzleSolution().getSolution().getPieces();
                printPuzzleSolution(pieces, numOfLines);
            } else {
                printServerErrors(serverResponse.getPuzzleSolution().getErrors());
            }
        } else {
            printParserErrors();
        }
    }

    private void printServerErrors(List<String> errors) {
        StringBuilder outPut = new StringBuilder();
        outPut.append(String.format("Errors for %s file", fileToHandle.getFileName().toString()));
        errors.stream().forEach(e -> {
            outPut.append(e);
            outPut.append("\n");
            System.out.println(e);
        });
        writeToOutPutFile(fileToHandle, outPut.toString());
    }

    private void printPuzzleSolution(List<Piece> pieces, int numOfLines) {
        StringBuffer outPut = new StringBuffer();
        outPut.append(String.format("Solution for %s file", fileToHandle.getFileName().toString()));
        outPut.append("\n");
        outPut.append(String.format("Found %s lines solution:", numOfLines));
        outPut.append("\n\n");
        int col = pieces.size() / numOfLines;
        for (int i = 0; i < pieces.size(); i++) {
            outPut.append(pieces.get(i).getId() + " ");
            if ((i % col == col - 1 && col != 1) || col == pieces.size()) {
                outPut.append("\n");
            }
        }
        System.out.println(outPut.toString());
        writeToOutPutFile(fileToHandle, outPut.toString());
    }

    private void printParserErrors() {
        ArrayList<String> inputValidationErrors = parser.getInputValidationErrors();
        StringBuilder outPut = new StringBuilder();
        outPut.append(String.format("Errors for %s file", fileToHandle.getFileName().toString()));
        inputValidationErrors.stream().forEach(e -> {
            outPut.append(e);
            outPut.append("\n");
            System.out.println(e);
        });
        writeToOutPutFile(fileToHandle, outPut.toString());
    }

    private void writeToOutPutFile(Path fileToHandle, String str) {
        OutputFileGenerator outPutFileGenerator = new OutputFileGenerator();
        outPutFileGenerator.writeResultToFile(fileToHandle, str);
    }

    private List<Piece> startParser() {
        parser = new Parser(fileToHandle);
        return parser.parse();
    }

    @Override
    public void run() {

        try {
            startClient();
        } catch (Exception e) {
            System.out.println("Failed to play the game - " + e.getMessage());
        }
    }
}