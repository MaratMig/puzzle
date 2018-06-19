package com.puzzle.common.utils;

public class ErrorBuilder {

    private String error;

    public ErrorBuilder(ErrorTypeEnum error, Object... variables) {
        String formatReadyString = error.getValue().replaceAll("%[\\d]+", "%s");
        formatReadyString = String.format(formatReadyString, variables);

        this.error = formatReadyString;
    }

    public String getError() {
        return error;
    }


}
