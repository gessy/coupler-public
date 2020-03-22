package com.offgrid.coupler.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

public class UserChat {
    @Embedded
    public User user;

    @Relation(parentColumn = "id", entityColumn = "user_id", entity = Chat.class)
    public Chat chat;

    public UserChat(User user, Chat chat) {
        this.user = user;
        this.chat = chat;
    }
}
