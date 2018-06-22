package com.puzzle.common.jsonPojo;

public class PuzzleReceived {
    private String sessionId;
    private int numPieces;

    public PuzzleReceived(String sessionId, int numPieces) {
        this.sessionId = sessionId;
        this.numPieces = numPieces;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getNumPieces() {
        return numPieces;
    }

    public void setNumPieces(int numPieces) {
        this.numPieces = numPieces;
    }
}
