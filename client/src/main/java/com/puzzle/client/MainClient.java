package com.puzzle.client;

import com.puzzle.client.utils.ClientConfigManager;

import java.io.File;

public class MainClient {

    public static void main(String[] args) throws Exception {

        System.out.println("Please note that ip, port, Input and Output values can be override by System property");
        System.out.println("e.g. -Dip=localhost, -Dport=7000, -DinputPath=c:\\puzzle, -DouptutPath=c:\\puzzle\\output");
        String ip = System.getProperty("ip", "127.0.0.1");
        System.out.println("Client ip: " + ip);
        String port = System.getProperty("port", "7095");
        System.out.println("Client port: " + port);
        String inputPath = System.getProperty("inputPath", "client/src/main/resources/inputFiles");
        System.out.println("InputPath: " + inputPath);
        String outputPath = System.getProperty("outputPath", inputPath + File.separator + "output");
        System.out.println("OutputPath: " + outputPath);

        ClientConfigManager conf = ClientConfigManager.getInstance();
        conf.init(ip, port, inputPath, outputPath);
        ClientExecutor clientExecutor = new ClientExecutor();
        clientExecutor.startGames(conf.getInputPath());
    }


}