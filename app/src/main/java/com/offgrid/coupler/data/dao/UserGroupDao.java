package com.offgrid.coupler.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.offgrid.coupler.data.entity.UserGroupRef;


@Dao
public abstract class UserGroupDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(UserGroupRef ref);

    @Delete
    public abstract void delete(UserGroupRef ref);
}
