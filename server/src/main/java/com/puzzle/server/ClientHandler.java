package com.puzzle.server;

import com.google.gson.Gson;
import com.puzzle.common.jsonPojo.ClientRequest;
import com.puzzle.common.jsonPojo.PuzzleReceived;
import com.puzzle.common.jsonPojo.PuzzleSolution;
import com.puzzle.common.jsonPojo.ServerResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


public class ClientHandler extends Thread {
    private Socket socket = null;
    private String clientNum = "0";
    ServerExecutor mainServer;
    PrintStream clientOutput = null;

    public ClientHandler(ServerExecutor serverExecutor, Socket socket, int clientNum) {
        this.socket = socket;
        this.clientNum = String.valueOf(clientNum);
        this.mainServer = serverExecutor;
    }

    @Override
    public void run() {
        try (BufferedReader clientInput = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             PrintStream clientOutput = new PrintStream(socket.getOutputStream(), /* autoflush */ true, StandardCharsets.UTF_8.toString());) {
            this.clientOutput = clientOutput;
            String line = "";
            System.out.println("Client: " + clientNum + " is connected now ");

            while (!line.equals("!")) {

                //read input from socket
                line = clientInput.readLine();
                if (line.equals("!")) {
                    break;
                }
                if (line != null && !line.isEmpty()) {
                    ClientRequest clientRequest = convertClientRequestToObject(line);
                    String immediateServerResponse = immediateServerResponse(new PuzzleReceived(clientNum, clientRequest.getPieces().getPieces().size()));
                    clientOutput.println(immediateServerResponse);
                    PuzzleSolution result = mainServer.tryToSolve(clientRequest.getPieces().getPieces());
                    String puzzleSolution = puzzleSolutionResponse(result);
                    clientOutput.println(puzzleSolution);
                } else {
                    clientOutput.println("no pieces have been accepted");
                }
            }


        } catch (IOException e) {
            // TODO log
            System.out.println("Client: " + clientNum + " has aborted");
        } catch (RuntimeException e) {
            System.out.println("server could not handle request - " + e.getMessage());
        }

        System.out.println("Client: " + clientNum + " was disconnected ");

    }

    private String puzzleSolutionResponse(PuzzleSolution result) throws RuntimeException {
        ServerResponse serverResponse = new ServerResponse(result);
        Gson gsonResult = new Gson();
        return gsonResult.toJson(serverResponse);

    }

    private String immediateServerResponse(PuzzleReceived puzzleReceived) throws RuntimeException {
        ServerResponse serverResponse = new ServerResponse(puzzleReceived);
        Gson gsonResult = new Gson();
        return gsonResult.toJson(serverResponse);
    }

    private ClientRequest convertClientRequestToObject(String line) {
        Gson gson = new Gson();
        ClientRequest clientRequest;
        try {
            clientRequest = gson.fromJson(line, ClientRequest.class);
        } catch (RuntimeException e) {
            throw new RuntimeException("failed to parse JSON object from client");
        }
        return clientRequest;
    }


}
