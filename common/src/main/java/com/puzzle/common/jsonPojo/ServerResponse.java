package com.puzzle.common.jsonPojo;

public class ServerResponse {

    PuzzleSolution puzzleSolution;

    public ServerResponse(PuzzleSolution puzzleSolution) {
        this.puzzleSolution = puzzleSolution;
    }

    public PuzzleSolution getPuzzleSolution() {
        return puzzleSolution;
    }

    public void setPuzzleSolution(PuzzleSolution puzzleSolution) {
        this.puzzleSolution = puzzleSolution;
    }
}
