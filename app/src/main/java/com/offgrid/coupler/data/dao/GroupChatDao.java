package com.offgrid.coupler.data.dao;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
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
    public void insert(@NonNull GroupChat groupChat) {
        if (isNotNull(groupChat)) {
            long id = _insert(groupChat.group);
            Chat chat = groupChat.chat;
            chat.setUserId(id);
            _insert(chat);
        }
    }


    @Update(onConflict = OnConflictStrategy.IGNORE, entity = Group.class)
    abstract void _update(Group group);

    @Update(onConflict = OnConflictStrategy.IGNORE, entity = Chat.class)
    abstract void _update(Chat chat);

    @Transaction
    public void update(@NonNull GroupChat groupChat) {
        if (isNotNull(groupChat)) {
            _update(groupChat.group);
            _update(groupChat.chat);
        }
    }

    @Delete(entity = Chat.class)
    abstract void _delete(Chat chat);

    @Delete(entity = Group.class)
    abstract void _delete(Group group);

    @Transaction
    public void delete(GroupChat groupChat) {
        if (groupChat.group != null ) _delete(groupChat.group);
        if (groupChat.chat != null ) _delete(groupChat.chat);
    }

    private boolean isNotNull(GroupChat groupChat) {
        return groupChat != null && groupChat.group != null && groupChat.chat != null;
    }
}
