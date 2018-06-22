package com.puzzle.client.fileHandlers;

import com.puzzle.client.utils.ClientConfigManager;

import java.io.*;
import java.nio.file.Path;

public class OutputFileGenerator {

    private String outPutPath;

    public OutputFileGenerator() {
        this.outPutPath = ClientConfigManager.getOutputPath();
        createOutputFolder();
    }

    private void createOutputFolder() {
        File directory = new File(outPutPath);
        if (!directory.exists()) {
            directory.mkdir();
        }

    }

    public void writeResultToFile(Path fileName, String str) {

        String outputFileName = fileName.getFileName().toString().replace(".txt", ".output");

        try (FileOutputStream fos = new FileOutputStream(outPutPath + File.separator + outputFileName);
             OutputStreamWriter writer = new OutputStreamWriter(fos)) {

            writer.write(str);

        } catch (FileNotFoundException e) {
            System.out.println("Can't create file");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
