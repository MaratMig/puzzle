package com.puzzle.common.jsonPojo;

public class ServerResponse {

    private PuzzleSolution puzzleSolution;
    private PuzzleReceived puzzleReceived;

    public ServerResponse(PuzzleSolution puzzleSolution) {
        this.puzzleSolution = puzzleSolution;
    }

    public PuzzleSolution getPuzzleSolution() {
        return puzzleSolution;
    }

    public void setPuzzleSolution(PuzzleSolution puzzleSolution) {
        this.puzzleSolution = puzzleSolution;
    }


    public ServerResponse(PuzzleReceived puzzleReceived) {
        this.puzzleReceived = puzzleReceived;
    }

    public PuzzleReceived getPuzzleReceived() {
        return puzzleReceived;
    }

    public void setPuzzleReceived(PuzzleReceived puzzleReceived) {
        this.puzzleReceived = puzzleReceived;
    }
}
