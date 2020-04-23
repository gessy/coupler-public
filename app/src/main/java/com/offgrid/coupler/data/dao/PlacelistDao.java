package com.offgrid.coupler.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.offgrid.coupler.data.entity.ListPlaces;
import com.offgrid.coupler.data.entity.Placelist;

import java.util.List;

@Dao
public abstract class PlacelistDao {

    @Query("select * from T_Placelist ")
    public abstract LiveData<List<Placelist>> findAll();

    @Query("select * from T_Placelist where id = :id")
    public abstract LiveData<Placelist> findById(Long id);

    @Query("delete from T_Placelist where id = :id")
    abstract void _delete(Long id);

    @Query("delete from T_Place where placelist_id = :list_id")
    abstract void _deletePlaces(Long list_id);

    @Transaction
    public void delete(Placelist placelist) {
        _deletePlaces(placelist.getId());
        _delete(placelist.getId());
    }

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void update(Placelist list);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(Placelist list);

    @Transaction
    @Query("select id as lid from T_Placelist where id = :id")
    public abstract LiveData<ListPlaces> findListPlacesById(Long id);


    @Transaction
    @Query("select id as lid from T_Placelist where show_on_map = 1")
    public abstract LiveData<List<ListPlaces>> findVisibleListPlaces();

}
