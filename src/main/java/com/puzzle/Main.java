package com.puzzle;

import com.puzzle.fileHandlers.GameExecutor;
import com.puzzle.utils.ConfigManager;

public class Main {

    public static void main(String[] args) throws Exception {

        //TODO: In production version should be uncomment
//        // Check how many arguments were passed in
//        if(args.length == 0)
//        {
//            System.out.println("Proper Usage is: input files path should be provided");
//            System.exit(0);
//        }

        ConfigManager configManager = new ConfigManager();
        //TODO: In production version we should not use default
//        configManager.setInputPath(args[0]);
        configManager.setInputPath("src\\main\\resources\\inputFiles");
        GameExecutor gameExecutor = new GameExecutor();
        gameExecutor.startGames(configManager.getInputPath());
    }


}