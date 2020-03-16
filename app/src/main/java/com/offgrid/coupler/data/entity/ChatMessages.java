package com.offgrid.coupler.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ChatMessages {
    @Embedded
    public Chat chat;

    @Relation(
            parentColumn = "id",
            entityColumn = "chat_id"
    )
    public List<Message> messages;
}
