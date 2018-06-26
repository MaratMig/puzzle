package com.puzzle.server;

import com.puzzle.common.entities.Piece;
import com.puzzle.common.jsonPojo.PuzzleSolution;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerExecutor {

    private ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private ServerSocket server = null;
    AtomicBoolean killTheServer = new AtomicBoolean(false);

    public ServerExecutor() {
    }

    public void startServer(int threads, int port) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        int ClientNum = 1;

        try {
            server = new ServerSocket(port);
            System.out.println("<<< SERVER STARTED >>>\n");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        while (!killTheServer.get()) {

            try {
                Socket socket = server.accept();
                if (!killTheServer.get()) {
                    ClientHandler ch = new ClientHandler(this, socket, ClientNum++);
                    clientHandlers.add(ch);
                    executor.execute(ch);
                }
            } catch (IOException e1) {
                // exception while waiting on server.accept
                try {
                    if (server != null && !server.isClosed()) {
                        server.close();
                    }
                } catch (IOException e) {
                    //ignore
                }
                if (killTheServer.get()) {
                    System.out.println("server shutdown normally");
                } else {
                    e1.printStackTrace();
                }
            }
        }

        executor.shutdown();
        executor.awaitTermination(5L, TimeUnit.MINUTES);
        System.out.println("Server is Down Over !!!");

    }


    public PuzzleSolution tryToSolve(List<Piece> piecesList) {

        //start solving and return the result
        PuzzleServerManager puzzleServerManager = new PuzzleServerManager();
        PuzzleSolution result = puzzleServerManager.startGame(piecesList);

        return result;
    }
}
