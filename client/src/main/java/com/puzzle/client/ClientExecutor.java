package com.puzzle.client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ClientExecutor {
    private final static int THREADS = 4;


    public void startGames(String path) throws IOException, InterruptedException {
        List<Path> fileList = readFilesFromDir(path);
        multiThreadedGame(fileList);
    }

    private List<Path> readFilesFromDir(String path) throws IOException {

        return Files.list(Paths.get(path))
                .filter(Files::isRegularFile).collect(Collectors.toList());
    }

    private void multiThreadedGame(List<Path> fileList) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(THREADS);
        for (int i = 0; i < fileList.size(); i++) {
            Runnable worker = new PuzzleClientManager(fileList.get(i));
            executor.execute(worker);
        }
        executor.shutdown();
        executor.awaitTermination(5L, TimeUnit.MINUTES);
        System.out.println("Game Over !!!");

    }


}
