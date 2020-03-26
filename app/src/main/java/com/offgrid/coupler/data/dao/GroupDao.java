package com.offgrid.coupler.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.offgrid.coupler.data.entity.Group;
import com.offgrid.coupler.data.entity.GroupChat;
import com.offgrid.coupler.data.entity.GroupChatMessages;
import com.offgrid.coupler.data.entity.GroupUsers;

import java.util.List;

@Dao
public abstract class GroupDao {

    @Query("select * from T_Group")
    public abstract LiveData<List<Group>> findAll();

    @Query("delete from T_Group")
    public abstract void deleteAll();

    @Transaction
    @Query("select * from T_Group where id = :group_id")
    public abstract LiveData<GroupChat> findGroupChatByGroupId(long group_id);

    @Transaction
    @Query("select group_id as gid, id as cid from T_Chat where group_id = :group_id")
    public abstract LiveData<GroupChatMessages> findGroupChatMessagesByGroupId(long group_id);

    @Update(onConflict = OnConflictStrategy.IGNORE, entity = Group.class)
    public abstract void update(Group group);

    @Transaction
    @Query("select * from T_Group where id = :group_id")
    public abstract LiveData<GroupUsers> findGroupUsersByGroupId(long group_id);

}
