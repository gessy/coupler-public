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

    @Query("SELECT * from T_Chat")
    LiveData<List<Chat>> findAll();

    @Query("SELECT * from T_Chat where type = :type")
    LiveData<List<Chat>> findAllByType(String type);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Chat chat);

    @Query("DELETE FROM T_Chat")
    void deleteAll();
}
