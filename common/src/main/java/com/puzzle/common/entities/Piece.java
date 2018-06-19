package com.puzzle.common.entities;

import java.util.ArrayList;
import java.util.List;

public class Piece {

    private int id;
    private int[] piece = new int[4];

    public Piece(int id, int[] piece) {
        this.id = id;
        this.piece = piece;
    }

    public int getId() {
        return id;
    }

    public int getLeft() {
        return piece[0];
    }

    public int getTop() {
        return piece[1];
    }

    public int getRight() {
        return piece[2];
    }

    public int getBottom() {
        return piece[3];
    }



    public int getSum() {
        int sum = 0;

        for (int side : piece) {
            sum += side;
        }
        return sum;
    }

    public List<Integer> getSidesAsList() {
        List<Integer> sidesNum = new ArrayList<>();
        for(Integer side: piece){
            sidesNum.add(side);
        }
        return sidesNum;
    }

    @Override
    public String toString() {
        return "id=" + id +"(" + getLeft() + ", " + getTop()+ ", " + getRight() + ", " + getBottom()+')';
    }
}
