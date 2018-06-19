package com.puzzle.common.jsonPojo;

public class ClientRequest {

        Pieces puzzle;

        public ClientRequest(Pieces pieces) {
            this.puzzle=pieces;
        }

        public Pieces getPieces() {
            return puzzle;
        }

        public void setPieces(Pieces pieces) {
            this.puzzle = pieces;
        }
}
