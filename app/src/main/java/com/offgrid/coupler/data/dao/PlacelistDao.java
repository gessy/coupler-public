package com.offgrid.coupler.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.offgrid.coupler.data.entity.Placelist;

import java.util.List;

@Dao
public interface PlacelistDao {

    @Query("select * from T_Placelist ")
    LiveData<List<Placelist>> findAll();

    @Query("select * from T_Placelist where id = :id")
    LiveData<Placelist> findById(Long id);

    @Query("delete from T_Placelist where id = :id")
    void delete(Long id);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(Placelist list);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Placelist list);

}
