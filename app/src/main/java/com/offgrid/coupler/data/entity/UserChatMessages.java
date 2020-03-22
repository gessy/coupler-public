package com.offgrid.coupler.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Relation;

import java.util.List;

public class UserChatMessages implements ChatMessages {
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

    @Override
    public Chat chat() {
        return chat;
    }

    @Override
    public List<Message> messages() {
        return messages;
    }

    @Override
    public Object reference() {
        return user;
    }

}
