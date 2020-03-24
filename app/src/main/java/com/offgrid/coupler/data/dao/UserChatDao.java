package com.offgrid.coupler.data.dao;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Transaction;
import androidx.room.Update;

import com.offgrid.coupler.data.entity.Chat;
import com.offgrid.coupler.data.entity.User;
import com.offgrid.coupler.data.entity.UserChat;

@Dao
public abstract class UserChatDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = User.class)
    abstract long _insert(User user);

    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = Chat.class)
    abstract long _insert(Chat chat);

    @Transaction
    public void insert(@NonNull UserChat userChat) {
        if (isNotNull(userChat)) {
            long id = _insert(userChat.user);
            Chat chat = userChat.chat;
            chat.setUserId(id);
            _insert(chat);
        }
    }


    @Update(onConflict = OnConflictStrategy.IGNORE, entity = User.class)
    abstract void _update(User user);

    @Update(onConflict = OnConflictStrategy.IGNORE, entity = Chat.class)
    abstract void _update(Chat chat);

    @Transaction
    public void update(@NonNull UserChat userChat) {
        if (isNotNull(userChat)) {
            _update(userChat.user);
            _update(userChat.chat);
        }
    }

    @Delete(entity = Chat.class)
    abstract void _delete(Chat chat);

    @Delete(entity = User.class)
    abstract void _delete(User user);

    @Transaction
    public void delete(UserChat userChat) {
        if (userChat.user != null ) _delete(userChat.user);
        if (userChat.chat != null ) _delete(userChat.chat);
    }

    private boolean isNotNull(UserChat userChat) {
        return userChat != null && userChat.user != null && userChat.chat != null;
    }

}
