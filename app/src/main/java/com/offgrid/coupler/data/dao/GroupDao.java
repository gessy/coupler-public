package com.offgrid.coupler.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.offgrid.coupler.data.entity.Chat;
import com.offgrid.coupler.data.entity.Group;
import com.offgrid.coupler.data.entity.GroupChat;

import java.util.List;

@Dao
public abstract class GroupDao {

    @Query("select * from T_Group")
    public abstract LiveData<List<Group>> findAll();

    @Query("select * from T_Group where id = :id")
    public abstract LiveData<Group> findById(Long id);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void update(Group group);

    @Query("delete from T_Group where id = :id")
    public abstract void deleteById(Long id);

    @Query("delete from T_Group")
    public abstract void deleteAll();

    @Transaction
    @Query("select * from T_Group where id = :group_id")
    public abstract LiveData<GroupChat> findGroupChatByGroupId(long group_id);

    @Transaction
    public void insert(GroupChat groupChat) {
        long id = insert(groupChat.group);
        Chat chat = groupChat.chat;
        if (chat != null) {
            chat.setGroupId(id);
            _insert(chat);
        }
    }


    @Transaction
    public void delete(GroupChat groupChat) {
        if (groupChat.group != null) delete(groupChat.group);
        if (groupChat.chat != null) _delete(groupChat.chat);
    }


    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = Group.class)
    public abstract long insert(Group group);

    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = Chat.class)
    abstract long _insert(Chat chat);

    @Delete(entity = Group.class)
    public abstract void delete(Group group);

    @Delete(entity = Chat.class)
    abstract void _delete(Chat chat);

}
