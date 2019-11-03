package com.awesome.testing.dto.information;

public enum InformationField {

    NAME("name"),
    NATIONALITY("nationality"),
    SALARY("salary"),
    ID("id");

    private final String fieldName;

    InformationField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getValue() {
        return fieldName;
    }
}
