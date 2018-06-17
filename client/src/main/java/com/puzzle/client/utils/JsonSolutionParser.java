package com.puzzle.client.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class JsonSolutionParser {

    private String puzzleSolution;
    private JsonObject jsonSolution;

    public JsonSolutionParser(String puzzleSolution) {
        this.puzzleSolution = puzzleSolution;
        jsonSolution = new JsonParser().parse(puzzleSolution).getAsJsonObject();
    }

    public boolean isSolutionExist(){
        return jsonSolution.get("solutionExists").getAsBoolean();
    }

    public List<String> getErrors(){
        List<String> errors = new ArrayList<>();
        JsonArray jsonErrors = jsonSolution.get("errors").getAsJsonArray();
        if(jsonErrors!=null){
            for(int i = 0; i<jsonErrors.size(); i++){
                errors.add(jsonErrors.get(i).getAsString());
            }
        }
        return errors;
    }

    public int getNumOfLinesInSolution(){
        JsonObject solution = jsonSolution.get("solution").getAsJsonObject();
        if(solution!=null){
            return solution.get("rows").getAsInt();
        }
        return 0;
    }

    public String[] getSolutionPiecesIds(){
        JsonObject solution = jsonSolution.get("solution").getAsJsonObject();
        String[] piecesIds = null;
        if(solution!=null){
            JsonArray jsonPiecesIds = solution.getAsJsonArray("solutionPieces");
            piecesIds = new String[jsonPiecesIds.size()];
            for(int i=0; i<jsonPiecesIds.size(); i++){
                piecesIds[i] = jsonPiecesIds.get(i).getAsString();
            }
        }
        return piecesIds;
    }
}