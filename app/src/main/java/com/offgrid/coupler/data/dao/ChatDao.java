package com.offgrid.coupler.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.offgrid.coupler.data.entity.Chat;

import java.util.List;

@Dao
public interface ChatDao {

    @Query("select * from T_Chat where visible = 1")
    LiveData<List<Chat>> findAll();


    @Query("select * from T_Chat where visible = 1 order by last_modification_date desc")
    LiveData<List<Chat>> findAllOrdaredByModificationDate();

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(Chat chat);
}
