package com.offgrid.coupler.data.entity;

import java.util.List;

public interface ChatMessages {
    Chat chat();

    List<Message> messages();

    Object reference();
}
