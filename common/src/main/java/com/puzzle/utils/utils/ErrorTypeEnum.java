package com.puzzle.utils.utils;

public enum ErrorTypeEnum {
    MISSING_ELEMENTS("Missing puzzle element(s) with the following IDs: %s"),
    WRONG_ELEMENT_ID("Puzzle of size %s cannot have the following IDs: %s"),
    WRONG_NUM_OF_PIECES("Wrong number of pieces. Expected: %s got: %s"),
    WRONG_ELEMENT_FORMAT("Puzzle ID %s has wrong data:%s"),
    WRONG_ELEMENT_FORMAT_GENERIC("Missing piece details."),
    WRONG_FILE_HEADER_FORMAT("Incorrect file header, no number of lines info"),
    MISSING_CORNER("Cannot solve puzzle: missing corner element:%s"),
    WRONG_NUM_STRAIGHTS("Cannot solve puzzle: wrong number of straight edges"),
    SUM_NOT_ZERO("Cannot solve puzzle: sum of edges is not zero"),
    SUM_LR_NOT_ZERO("Cannot solve puzzle: sum of Right and Left edges is not zero"),
    SUM_TB_NOT_ZERO("Cannot solve puzzle: sum of Top and Bottom edges is not zero"),
    NO_SOLUTION("Cannot solve puzzle: it seems that there is no proper solution");


    private String errorMessage;

    private ErrorTypeEnum(String value) {
        this.errorMessage = value;
    }

    public String getValue() {

        return errorMessage;
    }






}
