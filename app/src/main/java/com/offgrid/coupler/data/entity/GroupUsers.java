package com.offgrid.coupler.data.entity;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class GroupUsers {
    @Embedded
    public Group group;
    @Relation(
            parentColumn = "id",
            entityColumn = "id",
            associateBy = @Junction(
                    parentColumn = "group_id",
                    entityColumn = "user_id",
                    value = UserGroupRef.class
            )
    )
    public List<User> users;
}
