package com.offgrid.coupler.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.offgrid.coupler.data.entity.Place;

import java.util.List;

@Dao
public interface PlaceDao {

    @Query("select * from T_Place ")
    LiveData<List<Place>> findAll();

    @Query("select * from T_Place where id = :id")
    LiveData<Place> findById(Long id);

    @Query("delete from T_Place where id = :id")
    void delete(Long id);

    @Delete
    void delete(Place place);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(Place place);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Place place);
}
