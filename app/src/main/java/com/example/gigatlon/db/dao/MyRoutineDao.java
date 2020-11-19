package com.example.gigatlon.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.gigatlon.db.entity.MyRoutineEntity;

import java.util.List;

@Dao
public abstract class MyRoutineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(MyRoutineEntity... routines);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<MyRoutineEntity> routines);

    @Query("SELECT * FROM MyRoutine LIMIT :limit OFFSET :offset")
    public abstract LiveData<List<MyRoutineEntity>> findAll(int limit, int offset);

    @Query("SELECT * FROM MyRoutine WHERE id = :id")
    public abstract LiveData<MyRoutineEntity> findById(int id);

    @Query("SELECT * FROM MyRoutine")
    public abstract LiveData<List<MyRoutineEntity>> getAll();

    @Query("DELETE FROM MyRoutine WHERE id IN (SELECT id FROM MyRoutine LIMIT :limit OFFSET :offset)")
    public abstract void delete(int limit, int offset);
}