package com.puzzle.server;

import com.google.gson.Gson;
import com.puzzle.common.jsonPojo.ClientRequest;
import com.puzzle.common.jsonPojo.PuzzleReceived;
import com.puzzle.common.jsonPojo.PuzzleSolution;
import com.puzzle.common.jsonPojo.ServerResponse;

import java.io.*;
import java.net.Socket;


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
		try (BufferedReader clientInput = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF8"));
				PrintStream clientOutput = new PrintStream(socket.getOutputStream(), /* autoflush */ true, "UTF8");) {
			this.clientOutput = clientOutput;
			String line = "";
			System.out.println("Client: " + clientNum + " is connected now ");

			while (!line.equals("!")) {

				//read input from socket
				line = clientInput.readLine();
				if (line.equals("!")) {break;}
				if (line != null && !line.isEmpty()) {

					ClientRequest clientRequest = convertClientRequestToObject(line);
					String immediateServerResponse = immediateServerResponse(new PuzzleReceived(clientNum,clientRequest.getPieces().getPieces().size()));
					clientOutput.println(immediateServerResponse);
					PuzzleSolution result = mainServer.tryToSolve(clientRequest.getPieces().getPieces());
					String puzzleSolution = puzzleSolutionResponse(result);
					clientOutput.println(puzzleSolution);
				}
				else{
					clientOutput.println("no pieces have been accepted");
				}
			}

		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			// TODO log
			System.out.println("Client: " + clientNum + " has aborted");
		} finally {
			close();
		}

	}

	private String puzzleSolutionResponse(PuzzleSolution result) {
		ServerResponse serverResponse = new ServerResponse(result);
		Gson gsonResult = new Gson();
		return gsonResult.toJson(serverResponse);

	}

	private String immediateServerResponse(PuzzleReceived puzzleReceived) {
		ServerResponse serverResponse = new ServerResponse(puzzleReceived);
		Gson gsonResult = new Gson();
		return gsonResult.toJson(serverResponse);
	}

	private ClientRequest convertClientRequestToObject(String line) {
		Gson gson = new Gson();
		ClientRequest clientRequest = gson.fromJson(line, ClientRequest.class);
		return clientRequest;
	}

	public void close() {
		if (socket!=null && !socket.isClosed()){
			try {

				socket.close();
			} catch (IOException e) {
				// ignore
			}

		}
		System.out.println("Client: " + clientNum + " was disconnected ");
		
	}


}
