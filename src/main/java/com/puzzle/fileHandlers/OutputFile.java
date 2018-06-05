package com.puzzle.fileHandlers;

import java.io.*;
import java.nio.file.Path;

public class OutputFile {

    private Path outPutPath;
    private String fileName;

    public OutputFile(Path outPutPath) {
        this.outPutPath = outPutPath;
        createOutputFolder();
    }

    private void createOutputFolder() {
        Path outputFolder = outPutPath.getParent().resolve("output");
        File directory = new File(String.valueOf(outputFolder));
        if (! directory.exists()){
            directory.mkdir();
        }

    }


    public void writeResultToFile(Path fileName, String str)  {

        FileOutputStream fos = null;
        try {
            String output = fileName.getFileName().toString().replace(".txt",".output");
            fos = new FileOutputStream(outPutPath.resolve(output).toString());
            try {
                try (OutputStreamWriter writer = new OutputStreamWriter(fos)) {
                    try {
                        writer.write(str);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            System.out.println("Can't create file");
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }



}
