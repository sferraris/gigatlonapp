package com.example.gigatlon.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gigatlon.db.entity.UserEntity;

import java.util.List;

@Dao
public abstract class UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(UserEntity user);

    @Update
    public abstract void update(UserEntity user);

    @Query("SELECT * FROM User")
    public abstract LiveData<UserEntity> get();

}
