package com.puzzle.server;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;


public class ClientHandler extends Thread {
	private Socket socket = null;
	private String clientNum = "0";
	private String clientName;
	AtomicBoolean killTheServer = new AtomicBoolean(false);
	ServerExecutor mainServer;
	PrintStream clientOutput = null;

	public ClientHandler(ServerExecutor mainServer, Socket socket, int clientNum) {
		this.socket = socket;
		this.clientNum = String.valueOf(clientNum);
		this.mainServer = mainServer;
	}

	@Override
	public void run() {
		try (BufferedReader clientInput = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF8"));
				PrintStream clientOutput = new PrintStream(socket.getOutputStream(), /* autoflush */ true, "UTF8");) {
			this.clientOutput = clientOutput;
			String line = "";
			System.out.println("Received pieces from: " + clientNum + " is now connected");
			clientOutput.println("Pieces from clientNum: " + clientNum + " in process now");
			while (!line.equals("!")) {
				line = clientInput.readLine();

				if (line != null && !line.isEmpty()) {

					System.out.println("This was received from client: " + line);
					String result = mainServer.tryToSolve(line);
					System.out.println("RESULT: " + result);
					clientOutput.println(result);
				}
				else{
					clientOutput.println("no pieces have been accepted");
				}
//				("Name:")) {
//					clientNum=line.replace("Name:", "");
//					line = String.format("Client change it name To: %s", clientNum);
//					mainServer.sendToAll(clientNum,line);
//					System.out.println(line);
//
//				}
//				if (line.equals("?")) {
//					mainServer.shutdown();
//				} else {
//					mainServer.sendToAll(clientNum,line);
//					System.out.println(line);
//				}

			}
			

		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			// TODO log
			System.out.println("Client: " + clientNum + " has aborted");
		} finally {
			try {
				socket.close();
//				mainServer.unregister(this);
			} catch (IOException e) {
				// ignore
			}
		}

	}

	public void close() {
		if (socket!=null && !socket.isClosed()){
			try {
				socket.close();
			} catch (IOException e) {
				// ignore
			}
		}
		
	}

	public void sendToClient(String clientNum, String line) {
		if (this.clientNum != clientNum  && clientOutput!=null) {
			try {
				clientOutput.println("" + clientNum + ": " + line);
			} catch (Exception e) {
				//ignore - the client is probably closed
			}
		}
		
	}

}
