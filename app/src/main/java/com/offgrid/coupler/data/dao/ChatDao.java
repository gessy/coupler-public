package com.offgrid.coupler.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.offgrid.coupler.data.entity.Chat;
import com.offgrid.coupler.data.entity.ChatMessages;

import java.util.List;

@Dao
public interface ChatDao {

    @Query("select * from T_Chat")
    LiveData<List<Chat>> findAll();

    @Query("select * from T_Chat where id = :id")
    LiveData<Chat> findById(long id);

    @Query("select * from T_Chat where user_id = :userId")
    LiveData<Chat> findByUserId(long userId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Chat chat);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(Chat user);

    @Query("delete from T_Chat")
    void deleteAll();

    @Query("delete from T_Chat where id = :chat_id")
    void deleteChat(long chat_id);

    @Transaction
    @Query("select * from T_Chat where id = :chat_id")
    LiveData<ChatMessages> getChatMessages(long chat_id);

    @Transaction
    @Query("select * from T_Chat where user_id = :user_id")
    LiveData<ChatMessages> getUserChatMessages(long user_id);
}
