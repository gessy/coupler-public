package com.offgrid.coupler.core.model.command;

import androidx.annotation.NonNull;

public abstract class BaseCommand implements Command {

    public static final String REGION_LOCATION = "region_location";

    String command;

    BaseCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public boolean isCommand(@NonNull String command) {
        return this.command.equals(command);
    }
}
