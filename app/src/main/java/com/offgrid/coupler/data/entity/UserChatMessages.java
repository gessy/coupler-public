package com.offgrid.coupler.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Relation;

import java.util.List;

public class UserChatMessages {
    @ColumnInfo(name = "uid")
    public Long userId;
    @ColumnInfo(name = "cid")
    public Long chatId;

    @Relation(
            entity = User.class,
            parentColumn = "uid",
            entityColumn = "id"
    )
    public User user;

    @Relation(
            entity = Chat.class,
            parentColumn = "uid",
            entityColumn = "user_id"
    )
    public Chat chat;

    @Relation(
            entity = Message.class,
            parentColumn = "cid",
            entityColumn = "chat_id"
    )
    public List<Message> messages;
}
