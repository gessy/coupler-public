package com.offgrid.coupler.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.offgrid.coupler.data.entity.Group;

import java.util.List;

@Dao
public interface GroupDao {

    @Query("select * from T_Group")
    LiveData<List<Group>> findAll();

    @Query("select * from T_Group where id = :id")
    LiveData<Group> findById(Long id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Group group);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(Group group);

    @Query("delete from T_Group where id = :id")
    void deleteById(Long id);

    @Query("delete from T_Group")
    void deleteAll();
}
