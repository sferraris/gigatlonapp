package com.example.gigatlon.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.gigatlon.db.dao.UserDao;
import com.example.gigatlon.db.entity.UserEntity;

@Database(entities = {UserEntity.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
