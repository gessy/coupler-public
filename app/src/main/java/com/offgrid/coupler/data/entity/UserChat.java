package com.offgrid.coupler.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

public class UserChat {
    @Embedded
    public User user;
    @Relation(
            parentColumn = "id",
            entityColumn = "user_id"
    )
    public Chat chat;
}
