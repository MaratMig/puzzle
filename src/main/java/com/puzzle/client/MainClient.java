package com.puzzle.client;

import com.puzzle.client.utils.ClientConfigManager;

public class MainClient {

    public static void main(String[] args) throws Exception {

        //TODO: In production version should be uncomment
//        // Check how many arguments were passed in
//        if(args.length == 0)
//        {
//            System.out.println("Proper Usage is: input files path should be provided");
//            System.exit(0);
//        }

        ClientConfigManager configManager = new ClientConfigManager();
        //TODO: In production version we should not use default
//        configManager.setInputPath(args[0]);
        configManager.setInputPath("src\\main\\resources\\inputFiles");
        ClientExecutor clientExecutor = new ClientExecutor();
        clientExecutor.startGames(configManager.getInputPath());
    }


}