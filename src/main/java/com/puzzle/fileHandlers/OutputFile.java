package com.puzzle.fileHandlers;

import java.io.*;
import java.nio.file.Path;

public class OutputFile {

    private Path outPutPath;

    public OutputFile(Path outPutPath) {
        this.outPutPath = outPutPath;
        createOutputFolder();
    }

    private void createOutputFolder() {
        Path outputFolder = outPutPath.getParent().resolve("output");
        File directory = new File(String.valueOf(outputFolder));
        if (!directory.exists()) {
            directory.mkdir();
        }

    }


    public void writeResultToFile(Path fileName, String str) {

        String outputFileName = fileName.getFileName().toString().replace(".txt", ".output");

        try (FileOutputStream fos = new FileOutputStream(outPutPath.resolve(outputFileName).toString());
             OutputStreamWriter writer = new OutputStreamWriter(fos)) {

            writer.write(str);

        } catch (FileNotFoundException e) {
            System.out.println("Can't create file");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
