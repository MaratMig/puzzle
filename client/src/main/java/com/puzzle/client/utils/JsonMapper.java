package com.puzzle.client.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.puzzle.common.entities.Piece;

import java.util.List;

public class JsonMapper {

    private List<Piece> pieces;

    public JsonMapper(List<Piece> pieces){
        this.pieces = pieces;
    }

    private JsonObject convertPieceToJson(Piece piece){
        JsonObject jsonPiece = new JsonObject();
        jsonPiece.addProperty("id", piece.getId());

        JsonArray pieceSides = new JsonArray();
        pieceSides.add(piece.getLeft());
        pieceSides.add(piece.getTop());
        pieceSides.add(piece.getRight());
        pieceSides.add(piece.getBottom());

        jsonPiece.add("piece", pieceSides);

        return jsonPiece;
    }

    public JsonObject convertPiecesToJsonPuzzle() {
        JsonObject jsonPuzzle = new JsonObject();
        JsonArray jsonPieces = new JsonArray();

        pieces.stream().forEach(piece -> {
            jsonPieces.add(convertPieceToJson(piece));
        });

        JsonObject piecesArray = new JsonObject();
        piecesArray.add("pieces", jsonPieces);
        jsonPuzzle.add("puzzle", piecesArray);

        return jsonPuzzle;
    }


}