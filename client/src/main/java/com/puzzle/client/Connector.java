package com.puzzle.client;

import com.puzzle.client.utils.ClientConfigManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Connector {


    public String connectionToServer(String json) {

        try ( // try with resource for all the below
              Socket socket = new Socket(ClientConfigManager.getIp(), ClientConfigManager.getPort());
              BufferedReader socketInput = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF8"));
              PrintStream socketOutput = new PrintStream(socket.getOutputStream(), /* autoflush */ true, "UTF8");) {
            // Thread for handling server input


                socketOutput.println(json);
                System.out.println(socketInput.readLine());
                String serverResult = socketInput.readLine();
                    if (serverResult == null) {
                        throw new RuntimeException("server could not handle request");
                    }
                socketOutput.println("!");
                return serverResult;

        } catch (IOException e) {
            throw new RuntimeException("connection to server failed- " + e );
        }

    }
}