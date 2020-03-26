package com.offgrid.coupler.data.dao;

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


@Dao
public abstract class GroupChatDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = Group.class)
    abstract long _insert(Group group);

    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = Chat.class)
    abstract long _insert(Chat chat);

    @Transaction
    public void insert(GroupChat groupChat) {
        long id = _insert(groupChat.group);
        Chat chat = groupChat.chat;
        if (chat != null) {
            chat.setGroupId(id);
            _insert(chat);
        }
    }


    @Update(onConflict = OnConflictStrategy.IGNORE, entity = Group.class)
    abstract void _update(Group group);

    @Update(onConflict = OnConflictStrategy.IGNORE, entity = Chat.class)
    abstract void _update(Chat chat);


    @Transaction
    public void update(GroupChat groupChat) {
        if (groupChat.group != null) _update(groupChat.group);
        if (groupChat.chat != null) _update(groupChat.chat);
    }

    @Delete(entity = Group.class)
    abstract void _delete(Group group);

    @Delete(entity = Chat.class)
    abstract void _delete(Chat chat);

    @Query("delete from T_User_Group_Ref where group_id =:group_id")
    abstract void _deleteUserGroupRelation(long group_id);

    @Transaction
    public void delete(GroupChat groupChat) {
        if (groupChat.group != null) {
            _deleteUserGroupRelation(groupChat.group.getId());
            _delete(groupChat.group);
        }

        if (groupChat.chat != null) _delete(groupChat.chat);
    }

}
