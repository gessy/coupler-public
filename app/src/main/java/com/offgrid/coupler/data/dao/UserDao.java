package com.offgrid.coupler.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.offgrid.coupler.data.entity.Chat;
import com.offgrid.coupler.data.entity.User;
import com.offgrid.coupler.data.entity.UserChat;
import com.offgrid.coupler.data.entity.UserChatMessages;

import java.util.List;

@Dao
public abstract class UserDao {

    @Query("select * from T_User")
    public abstract LiveData<List<User>> findAll();

    @Query("select * from T_User where gid = :gid")
    public abstract LiveData<User> findByGid(String gid);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(User user);

    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = Chat.class)
    abstract long _insert(Chat chat);

    @Transaction
    public void insert(UserChat userChat) {
        long id = insert(userChat.user);
        Chat chat = userChat.chat;
        if (chat != null) {
            chat.setUserId(id);
            _insert(chat);
        }
    }

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void update(User user);

    @Query("delete from T_User where gid = :gid")
    public abstract void deleteByGid(String gid);

    @Query("delete from T_User where id = :id")
    public abstract void deleteById(Long id);

    @Query("delete from T_User")
    public abstract void deleteAll();

    @Transaction
    @Query("select * from T_User where id = :user_id")
    public abstract LiveData<UserChat> findUserChatByUserId(long user_id);

    @Transaction
    @Query("select * from T_User where gid = :gid")
    public abstract LiveData<UserChat> findUserChatByGid(String gid);

    @Transaction
    @Query("select user_id as uid, id as cid from T_Chat where user_id = :user_id")
    public abstract LiveData<UserChatMessages> findUserChatMessagesByUserId(long user_id);
}
