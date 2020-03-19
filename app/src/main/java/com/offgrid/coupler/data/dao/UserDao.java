package com.offgrid.coupler.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.offgrid.coupler.data.entity.User;
import com.offgrid.coupler.data.entity.UserChat;
import com.offgrid.coupler.data.entity.UserChatMessages;

import java.util.List;

@Dao
public interface UserDao {

    @Query("select * from T_User")
    LiveData<List<User>> findAll();

    @Query("select * from T_User where gid = :gid")
    LiveData<User> findByGid(String gid);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(User user);

    @Query("delete from T_User where gid = :gid")
    void deleteByGid(String gid);

    @Query("delete from T_User where id = :id")
    void deleteById(Long id);

    @Query("delete from T_User")
    void deleteAll();

    @Transaction
    @Query("select * from T_User where id = :user_id")
    LiveData<UserChat> findUserChatByUserId(long user_id);

    @Transaction
    @Query("select * from T_User where gid = :gid")
    LiveData<UserChat> findUserChatByGid(String gid);

    @Transaction
    @Query("select user_id as uid, id as cid from T_Chat where user_id = :user_id")
    LiveData<UserChatMessages> findUserChatMessagesByUserId(long user_id);
}
