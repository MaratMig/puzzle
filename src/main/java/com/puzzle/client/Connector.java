package com.puzzle.client;

import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Connector  {

    public Connector() {
    }

    public String connectionToServer(int myNumber, JsonObject pieces) {

        try ( // try with resource for all the below
              Socket socket = new Socket("localhost", 7000);
              BufferedReader socketInput = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF8"));
              PrintStream socketOutput = new PrintStream(socket.getOutputStream(), /* autoflush */ true, "UTF8");){
            // Thread for handling server input

                try {

                    socketOutput.println(pieces.toString());
                    System.out.println(socketInput.readLine());
                    String serverResult = socketInput.readLine();
                    socketOutput.println("!");
                    return serverResult;

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

        } catch (IOException e) {
            System.out.println("Client number: " + myNumber + " has exited.");
        }


        return null;
    }



}
