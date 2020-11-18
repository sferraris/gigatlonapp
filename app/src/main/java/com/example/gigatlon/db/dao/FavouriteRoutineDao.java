package com.example.gigatlon.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gigatlon.db.entity.FavouriteRoutineEntity;
import com.example.gigatlon.db.entity.WeightingEntity;
import com.example.gigatlon.domain.Weighting;

import java.util.List;

@Dao
public abstract class FavouriteRoutineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(FavouriteRoutineEntity... routines);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<FavouriteRoutineEntity> routines);

    @Query("SELECT * FROM FavouriteRoutine LIMIT :limit OFFSET :offset")
    public abstract LiveData<List<FavouriteRoutineEntity>> findAll(int limit, int offset);

    @Query("SELECT * FROM FavouriteRoutine WHERE id = :id")
    public abstract LiveData<FavouriteRoutineEntity> findById(int id);

    @Query("SELECT * FROM FavouriteRoutine")
    public abstract LiveData<List<FavouriteRoutineEntity>> getAll();

    @Query("DELETE FROM FavouriteRoutine WHERE id IN (SELECT id FROM FavouriteRoutine LIMIT :limit OFFSET :offset)")
    public abstract void delete(int limit, int offset);
}