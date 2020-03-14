package com.offgrid.coupler.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.offgrid.coupler.data.entity.Chat;

import java.util.List;
import java.util.concurrent.CompletableFuture;

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

    @Query("delete from T_Chat")
    void deleteAll();

    @Query("delete from T_Chat where id = :chat_id")
    void deleteChat(long chat_id);
}
