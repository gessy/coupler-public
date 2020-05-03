package com.offgrid.coupler.data.model;

public enum DownloadState {
    READY_TO_LOAD("READY_TO_LOAD"),
    IN_PROGRESS("IN_PROGRESS"),
    DOWNLOADED("DOWNLOADED");

    private String value;

    DownloadState(String value) {
        this.value = value;
    }
}
