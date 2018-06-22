package com.puzzle.common.jsonPojo;

import java.util.List;

public class PuzzleSolution {
    private boolean solutionExists;
    private List<String> errors;
    private Solution solution;

    public PuzzleSolution(boolean solutionExists, List<String> errors) {
        this.solutionExists = solutionExists;
        this.errors = errors;
    }

    public PuzzleSolution(boolean solutionExists, Solution solution) {
        this.solutionExists = solutionExists;
        this.solution = solution;
    }

    public boolean isSolutionExists() {
        return solutionExists;
    }

    public void setSolutionExists(boolean solutionExists) {
        this.solutionExists = solutionExists;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public Solution getSolution() {
        return solution;
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
    }
}
