package com.puzzle.client;

import com.puzzle.client.utils.ClientConfigManager;

public class MainClient {

    public static void main(String[] args) throws Exception {


        String ip = System.getProperty("ip", "127.0.0.1");
        System.out.println("Client ip: " + ip);
        String port = System.getProperty("port", "7095");
        System.out.println("Client port: " + port);

        //TODO: In production version should be uncomment
//        // Check how many arguments were passed in
//        if(input.length == 0)
//        {
//            System.out.println("Proper Usage is: input files path should be provided");
//            System.exit(0);
//        }

        ClientConfigManager configManager = new ClientConfigManager();
        //TODO: In production version we should not use default
//        configManager.setInputPath(args[0]);
        configManager.setInputPath("src\\main\\resources\\inputFiles");
        configManager.setIp(ip);
        configManager.setPort(Integer.valueOf(port));
        ClientExecutor clientExecutor = new ClientExecutor();
        clientExecutor.startGames(configManager.getInputPath());
    }


}