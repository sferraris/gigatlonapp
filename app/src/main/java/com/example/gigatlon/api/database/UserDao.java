package com.example.gigatlon.api.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(UserEntity... users);

    @Update
    public void updateAll(UserEntity... users);

    @Query("SELECT * FROM userentity")
    public LiveData<List<UserEntity>> getAll();
}
