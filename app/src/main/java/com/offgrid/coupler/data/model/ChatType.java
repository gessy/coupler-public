package com.offgrid.coupler.data.model;

public enum ChatType {
    NONE("NONE"),
    PERSONAL("PERSONAL"),
    GROUP("GROUP");

    private String value;

    ChatType(String value) {
        this.value = value;
    }
}
