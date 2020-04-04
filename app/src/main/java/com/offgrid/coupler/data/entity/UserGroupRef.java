package com.offgrid.coupler.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;


@Entity(tableName = "T_User_Group_Ref", primaryKeys = {"user_id", "group_id"})
public class UserGroupRef {
    @NonNull
    @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user_id")
    @ColumnInfo(name = "user_id")
    public Long userId;

    @NonNull
    @ForeignKey(entity = Group.class, parentColumns = "id", childColumns = "group_id")
    @ColumnInfo(name = "group_id")
    public Long groupId;

    public UserGroupRef() {
    }

    @Ignore
    public UserGroupRef(@NonNull Long userId, @NonNull Long groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }
}
