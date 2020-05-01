package com.offgrid.coupler.core.model.command;

import androidx.annotation.NonNull;

public interface Command {

    String getCommand();

    boolean isCommand(@NonNull String command);

}
