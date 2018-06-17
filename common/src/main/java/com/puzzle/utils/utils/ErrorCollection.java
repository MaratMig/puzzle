package com.puzzle.utils.utils;

import java.util.ArrayList;
import java.util.List;

public class ErrorCollection {

    private List<String> errors = new ArrayList<>();

    public void addError(String singleError) {
        this.errors.add(singleError);
    }

    public List<String> getErrors() {
        return errors;
    }


}
