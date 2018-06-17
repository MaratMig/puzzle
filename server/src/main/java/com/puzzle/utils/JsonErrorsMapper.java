package com.puzzle.utils;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

public class JsonErrorsMapper {

    List<String> errors;

    public JsonErrorsMapper(List<String> errors){
        this.errors = errors;
    }

    public JsonObject getErrorsAsJson(){
        JsonObject jsonErrors = new JsonObject();

        JsonArray errorsArray = new JsonArray();
        errors.stream().forEach(error ->errorsArray.add(error));
        jsonErrors.add("errors", errorsArray);

        JsonObject puzzleSolution = new JsonObject();
        puzzleSolution.add("puzzleSolution",errorsArray);
        return puzzleSolution;
    }
}
