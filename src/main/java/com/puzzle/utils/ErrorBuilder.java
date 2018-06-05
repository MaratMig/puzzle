package com.puzzle.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ErrorBuilder {

    public String getError() {
        return error;
    }

    private String error;

    public ErrorBuilder(ErrorTypeEnum error,  Object... variables) {
        String formatReadyString = error.getValue().replaceAll("%[\\d]+", "%s");
        formatReadyString = String.format(formatReadyString, variables);

        this.error = formatReadyString;
    }





}
