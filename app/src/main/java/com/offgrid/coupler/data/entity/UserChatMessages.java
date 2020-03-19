package com.offgrid.coupler.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserChatMessages {
    @Embedded
    public User user;

    @Relation(
            entity = Chat.class,
            parentColumn = "id",
            entityColumn = "user_id"
    )
    public Chat chat;

    @Relation(
            entity = Message.class,
            parentColumn = "id",
            entityColumn = "chat_id"
    )
    public List<Message> messages;
}
