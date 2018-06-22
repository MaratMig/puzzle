package com.puzzle.common;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.puzzle.common.entities.Piece;

import java.util.Arrays;

public class JsonSolutionMapper {

    private Piece[] pieces;
    private int numOfLines;

    public JsonSolutionMapper(Piece[] pieces, int numOfLines){
        this.pieces = pieces;
        this.numOfLines = numOfLines;
    }

    private JsonArray convertPiecesToJsonArray(){
        JsonArray jsonPieces = new JsonArray();
        Arrays.stream(pieces).filter(piece -> piece !=null).forEach(piece -> jsonPieces.add(piece.getId()));
        return jsonPieces;
    }

    public JsonObject getPuzzleSolutionAsJson(){
        JsonObject solution = new JsonObject();
        solution.addProperty("rows", numOfLines);
        solution.add("solutionPieces", convertPiecesToJsonArray());

        JsonObject jsonSolution = new JsonObject();
        jsonSolution.add("solution", solution);

        JsonObject puzzleSolution = new JsonObject();
        puzzleSolution.add("puzzleSolution", jsonSolution);
        return puzzleSolution;
    }
}