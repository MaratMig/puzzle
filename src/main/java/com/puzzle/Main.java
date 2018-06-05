package com.puzzle;

import com.puzzle.fileHandlers.FileManager;

import java.nio.file.Path;

public class Main {

    private static String inputPath = null;

    public static void main(String[] args) throws Exception {


        // Check how many arguments were passed in
        if (args.length != 2) {
            System.out.println("Proper Usage is: java program filenames");
            //            System.exit(0);
        }

        //TODO: In production version should not use default
        // input and output files should entered from command line

        setInputPath("src\\main\\resources\\inputFiles");

        FileManager fileManager = new FileManager();
        fileManager.startGames(getInputPath());
    }

    public static String getInputPath() {
        return inputPath;
    }

    public static void setInputPath(String inputPath) {
        Main.inputPath = inputPath;
    }
}