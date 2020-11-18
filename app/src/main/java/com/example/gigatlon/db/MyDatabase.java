package com.example.gigatlon.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.gigatlon.db.dao.UserDao;
import com.example.gigatlon.db.dao.WeightingDao;
import com.example.gigatlon.db.entity.UserEntity;
import com.example.gigatlon.db.entity.WeightingEntity;

@Database(entities = {UserEntity.class, WeightingEntity.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract WeightingDao weightingDao();
}
