package com.offgrid.coupler.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Relation;

import java.util.List;

public class GroupChatMessages implements ChatMessages {
    @ColumnInfo(name = "gid")
    public Long groupId;
    @ColumnInfo(name = "cid")
    public Long chatId;

    @Relation(
            entity = Group.class,
            parentColumn = "gid",
            entityColumn = "id"
    )
    public Group group;

    @Relation(
            entity = Chat.class,
            parentColumn = "gid",
            entityColumn = "group_id"
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
        return group;
    }
}
