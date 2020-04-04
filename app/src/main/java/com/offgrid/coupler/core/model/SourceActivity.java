package com.offgrid.coupler.core.model;

public enum SourceActivity {
    NOT_DEFINED("NOT_DEFINED"),
    CHAT("CHAT"),
    GROUP_INFO("GROUP_INFO");

    private String value;

    SourceActivity(String value) {
        this.value = value;
    }
}
