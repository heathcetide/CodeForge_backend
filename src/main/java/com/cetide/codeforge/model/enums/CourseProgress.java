package com.cetide.codeforge.model.enums;

public enum CourseProgress {

    NOT_STARTED("NOT_STARTED"),

    IN_PROGRESS("IN_PROGRESS"),

    COMPLETED("COMPLETED");

    private final String value;

    CourseProgress(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
