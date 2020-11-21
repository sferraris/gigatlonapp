package com.example.gigatlon.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.gigatlon.db.entity.MyRoutineEntity;
import com.example.gigatlon.db.entity.RoutineEntity;

import java.util.List;

@Dao
public abstract class RoutineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(RoutineEntity... routines);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<RoutineEntity> routines);

    @Query("SELECT * FROM Routine ORDER BY :order LIMIT :limit OFFSET :offset")
    public abstract LiveData<List<RoutineEntity>> findAll(int limit, int offset, String order);


    @Query("SELECT * FROM Routine ORDER BY averageRating DESC LIMIT :limit OFFSET :offset")
    public abstract LiveData<List<RoutineEntity>> findAvgDESC(int limit, int offset);

    @Query("SELECT * FROM Routine ORDER BY averageRating ASC LIMIT :limit OFFSET :offset")
    public abstract LiveData<List<RoutineEntity>> findAvgASC(int limit, int offset);

    @Query("SELECT * FROM Routine ORDER BY dateCreated ASC LIMIT :limit OFFSET :offset")
    public abstract LiveData<List<RoutineEntity>> findDateASC(int limit, int offset);
    @Query("SELECT * FROM Routine ORDER BY dateCreated DESC LIMIT :limit OFFSET :offset")

    public abstract LiveData<List<RoutineEntity>> findDateDESC(int limit, int offset);

    @Query("SELECT * FROM Routine ORDER BY difficulty ASC LIMIT :limit OFFSET :offset")
    public abstract LiveData<List<RoutineEntity>> findDiffASC(int limit, int offset);

    @Query("SELECT * FROM Routine WHERE id = :id")
    public abstract LiveData<RoutineEntity> findById(int id);

    @Query("SELECT * FROM Routine")
    public abstract LiveData<List<RoutineEntity>> getAll();

    @Query("DELETE FROM Routine WHERE id IN (SELECT id FROM Routine LIMIT :limit OFFSET :offset)")
    public abstract void delete(int limit, int offset);
}