package com.puzzle.common.jsonPojo;

import com.puzzle.common.entities.Piece;

import java.util.List;

public class Solution {
    private int rows;
    private List<Piece> pieces;

    public Solution(int rows, List<Piece> pieces) {
        this.rows = rows;
        this.pieces = pieces;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
    }
}
