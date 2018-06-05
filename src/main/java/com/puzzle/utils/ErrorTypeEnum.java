package com.puzzle.utils;

public enum ErrorTypeEnum {
    MISSING_ELEMENTS("Missing puzzle element(s) with the following IDs:%s"),
    WRONG_ELEMENT_ID("Puzzle of size %d cannot have the following IDs:%s"),
    WRONG_ELEMENT_FORMAT("Puzzle ID %d has wrong data:%s"),
    MISSING_CORNER("Cannot solve puzzle: missing corner element:%s"),
    WRONG_NUM_STRAIGHTS("Cannot solve puzzle: wrong number of straight edges"),
    SUM_NOT_ZERO("Cannot solve puzzle: sum of edges is not zero"),
    NO_SOLUTION("Cannot solve puzzle: it seems that there is no proper solution");


    private String errorMessage;

    private ErrorTypeEnum(String value) {
        this.errorMessage = value;
    }

    public String getValue() {

        return errorMessage;
    }






}
