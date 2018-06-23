package com.puzzle.common.entities;

import java.util.Arrays;

public class Shape {

    private int[] sides = new int[4];

    public Shape(int[] sides) {
        this.sides = sides;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shape)) return false;

        Shape shape = (Shape) o;

        return Arrays.equals(sides, shape.sides);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(sides);
    }

    @Override
    public String toString() {
        return "(" +
                Arrays.toString(sides) +
                '}';
    }

    public int[] getSides() {
        return sides;
    }
}
