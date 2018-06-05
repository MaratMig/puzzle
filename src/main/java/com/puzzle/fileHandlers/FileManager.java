package com.puzzle.fileHandlers;

import com.puzzle.PuzzleGameManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class FileManager {
    final static int THREADS = 1;
    List<Path> fileList;

    public FileManager() {
    }

    public void startGames(String path) throws IOException, InterruptedException {
        readFilesFromDir(path);
        multiThreadedGame();
    }

    private void readFilesFromDir(String path) throws IOException {

        fileList = Files.list(Paths.get(path))
                .filter(Files::isRegularFile).collect(Collectors.toList());

    }

    private void multiThreadedGame() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(THREADS);
        for (int i = 0; i < fileList.size() ; i++) {
            int taskNum = i;
            int finalI = i;
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    System.out.println("ThreadId: " + Thread.currentThread().getId());
                    PuzzleGameManager puzzleGame = new PuzzleGameManager(fileList.get(finalI));
                    try {
                        puzzleGame.startGame();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            executor.execute(task);

        }
        executor.shutdown();
        while (!executor.awaitTermination(10L, TimeUnit.MINUTES)) {
            System.out.println("Not yet. Still waiting for games completion");
        }
        System.out.println("Game over !!!");

    }



}
