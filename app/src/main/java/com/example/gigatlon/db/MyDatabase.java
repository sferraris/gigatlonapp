package com.example.gigatlon.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.gigatlon.db.dao.CreatorDao;
import com.example.gigatlon.db.dao.FavouriteRoutineDao;
import com.example.gigatlon.db.dao.MyRoutineDao;
import com.example.gigatlon.db.dao.RoutineDao;
import com.example.gigatlon.db.dao.UserDao;
import com.example.gigatlon.db.dao.WeightingDao;
import com.example.gigatlon.db.entity.FavouriteRoutineEntity;
import com.example.gigatlon.db.entity.MyRoutineEntity;
import com.example.gigatlon.db.entity.RoutineEntity;
import com.example.gigatlon.db.entity.UserEntity;
import com.example.gigatlon.db.entity.WeightingEntity;

@Database(entities = {UserEntity.class, WeightingEntity.class, RoutineEntity.class, MyRoutineEntity.class, FavouriteRoutineEntity.class, CreatorDao.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract WeightingDao weightingDao();
    public abstract RoutineDao routineDao();
    public abstract MyRoutineDao myRoutineDao();
    public abstract FavouriteRoutineDao favouriteRoutineDao();
    public abstract CreatorDao creatorDao();
}
