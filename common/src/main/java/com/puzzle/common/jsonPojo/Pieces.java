package com.puzzle.common.jsonPojo;

import com.puzzle.common.entities.Piece;

import java.util.List;

public class Pieces {
    private List<Piece> pieces;

    public Pieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
    }
}
