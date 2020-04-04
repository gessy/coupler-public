package com.offgrid.coupler.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.offgrid.coupler.data.entity.User;
import com.offgrid.coupler.data.entity.UserChat;
import com.offgrid.coupler.data.entity.UserChatMessages;

import java.util.List;

@Dao
public abstract class UserDao {

    @Query("select * from T_User")
    public abstract LiveData<List<User>> findAll();

    @Query("select * from T_User order by last_seen desc")
    public abstract LiveData<List<User>> findAllOrderedByLastSeenDate();

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void update(User user);

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
