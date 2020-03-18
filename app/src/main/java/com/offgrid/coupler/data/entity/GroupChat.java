package com.offgrid.coupler.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

public class GroupChat {
    @Embedded
    public Group group;
    @Relation(
            parentColumn = "id",
            entityColumn = "group_id",
            entity = Chat.class
    )
    public Chat chat;

    public GroupChat(Group group, Chat chat) {
        this.group = group;
        this.chat = chat;
    }
}
