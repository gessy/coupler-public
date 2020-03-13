package com.offgrid.coupler.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.offgrid.coupler.data.entity.Chat;

import java.util.List;

@Dao
public interface ChatDao {

    @Query("select * from T_Chat")
    LiveData<List<Chat>> findAll();

    @Query("select * from T_Chat where id = :id")
    LiveData<Chat> findById(long id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Chat chat);

    @Query("delete from T_Chat")
    void deleteAll();
}
