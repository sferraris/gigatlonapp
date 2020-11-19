package com.example.gigatlon.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gigatlon.db.entity.WeightingEntity;
import com.example.gigatlon.domain.Weighting;

import java.util.List;

@Dao
public abstract class WeightingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(WeightingEntity... weightings);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<WeightingEntity> sports);

    @Query("SELECT * FROM Weighting ORDER BY id DESC LIMIT :limit OFFSET :offset  " )
    public abstract LiveData<List<WeightingEntity>> findAllDESC(int limit, int offset);

    @Query("SELECT * FROM Weighting ORDER BY id ASC LIMIT :limit OFFSET :offset  " )
    public abstract LiveData<List<WeightingEntity>> findAllASC(int limit, int offset);

    @Query("SELECT * FROM Weighting WHERE id = :id")
    public abstract LiveData<WeightingEntity> findById(int id);

    @Query("SELECT * FROM Weighting")
    public abstract LiveData<List<WeightingEntity>> getAll();

    @Query("DELETE FROM Weighting WHERE id IN (SELECT id FROM Weighting LIMIT :limit OFFSET :offset)")
    public abstract void delete(int limit, int offset);
}