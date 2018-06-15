package com.puzzle.entities;

import java.util.Arrays;

public class PuzzleShape {

    private int[] sides = new int[4];

    public PuzzleShape(int[] sides) {
        this.sides = sides;
        calcSum();
    }

    public int calcSum() {
        int sum = 0;
        for (int side : sides) {
            sum += side;
        }
        return sum;
    }

    @Override
    public String toString() {
        return "(" + getLeft() + ", " + getTop()+ ", " + getRight() + ", " + getBottom()+')';
    }

    public int getLeft() {
        return sides[0];
    }

    public int getTop() {
        return sides[1];
    }

    public int getRight() {
        return sides[2];
    }

    public int getBottom() {
        return sides[3];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PuzzleShape)) return false;

        PuzzleShape that = (PuzzleShape) o;

        return Arrays.equals(sides, that.sides);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(sides);
    }
}
