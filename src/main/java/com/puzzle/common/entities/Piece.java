package com.puzzle.common.entities;

import java.util.ArrayList;
import java.util.List;

public class Piece {

    private int id;
    private int sum;
    private int[] sides = new int[4];

    public Piece(int id, int[] sides) {
        this.id = id;
        this.sides = sides;
        calcSum();
    }

    public int getId() {
        return id;
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

    public int getSum() {
        return sum;
    }


    private int calcSum() {
        this.sum = 0;

        for (int side : sides) {
            sum += side;
        }
        return sum;
    }

    public List<Integer> getSidesAsList() {
        List<Integer> sidesNum = new ArrayList<>();
        for(Integer side: sides){
            sidesNum.add(side);
        }
        return sidesNum;
    }

    @Override
    public String toString() {
        return "id=" + id +"(" + getLeft() + ", " + getTop()+ ", " + getRight() + ", " + getBottom()+')';
    }
}
