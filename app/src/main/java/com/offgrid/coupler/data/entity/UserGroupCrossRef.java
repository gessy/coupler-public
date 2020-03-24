package com.offgrid.coupler.data.entity;

import androidx.room.Entity;

@Entity(primaryKeys = {"userId", "groupId"})
public class UserGroupCrossRef {
    public long userId;
    public long groupId;
}
