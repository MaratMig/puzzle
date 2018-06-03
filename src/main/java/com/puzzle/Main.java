package com.puzzle;

public class Main {

    private static String inputFile = null;
    private static String outputFile = null;

    public static void main(String[] args) throws Exception {


        // Check how many arguments were passed in
        if (args.length != 2) {
            System.out.println("Proper Usage is: java program filenames");
            //            System.exit(0);
        }

        //TODO: In production version should not use default
        // input and output files should entered from command line

        setInputFile("src\\main\\resources\\inputFile.txt");
        setOutputFile("src\\main\\resources\\outputFile.txt");

        PuzzleGameManager game = new PuzzleGameManager(inputFile);
        game.startGame();
    }

    public static String getInputFile() {
        return inputFile;
    }

    public static void setInputFile(String inputFile) {
        Main.inputFile = inputFile;
    }

    public static String getOutputFile() {
        return outputFile;
    }

    public static void setOutputFile(String outputFile) {
        Main.outputFile = outputFile;
    }
}
