package com.puzzle.entities;

import java.util.ArrayList;
import java.util.List;

public class Piece{

    private int id;
    private PuzzleShape shape;
    private int sum;
    private boolean isUsed;

    public Piece(int id, int[] sides) {
        this.id = id;
        shape = new PuzzleShape(sides);
        sum = shape.calcSum();
        isUsed = false;
    }

    public int getId() {
        return id;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public int getLeft() {
        return shape.getLeft();
    }

    public int getTop() {
        return shape.getTop();
    }

    public int getRight() {
        return shape.getRight();
    }

    public int getBottom() {
        return shape.getBottom();
    }

    public PuzzleShape getShape(){
        return shape;
    }

    public int getSum() {
        return sum;
    }

    @Override
    public String toString() {
        return "(" + getLeft() + ", " + getTop()+ ", " + getRight() + ", " + getBottom()+')';
    }
}
