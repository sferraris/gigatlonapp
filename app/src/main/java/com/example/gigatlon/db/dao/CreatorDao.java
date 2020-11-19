package com.example.gigatlon.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.gigatlon.db.entity.CreatorEntity;

@Dao
public abstract class CreatorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(CreatorEntity creator);

    @Query("SELECT * FROM User WHERE id = :id")
    public abstract CreatorEntity getById(int id);
}