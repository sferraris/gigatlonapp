package com.example.gigatlon;

import android.app.Application;

import androidx.room.Room;

import com.example.gigatlon.api.ApiClient;
import com.example.gigatlon.api.ApiRoutineService;
import com.example.gigatlon.api.ApiUserService;
import com.example.gigatlon.db.MyDatabase;
import com.example.gigatlon.repository.AppExecutors;
import com.example.gigatlon.repository.RoutineRepository;
import com.example.gigatlon.repository.UserRepository;
import com.example.gigatlon.utils.Constants;

public class MyApplication extends Application {

    AppExecutors appExecutors;
    MyPreferences preferences;
    UserRepository userRepository;
    RoutineRepository routineRepository;

    public MyPreferences getPreferences() {
        return preferences;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
    public RoutineRepository getRoutineRepository(){
        return routineRepository;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        preferences = new MyPreferences(this);

        appExecutors = new AppExecutors();

        ApiUserService userService = ApiClient.create(this, ApiUserService.class);

        MyDatabase database = Room.databaseBuilder(this, MyDatabase.class, Constants.DATABASE_NAME).build();

        ApiRoutineService routineService = ApiClient.create(this, ApiRoutineService.class);


        userRepository = new UserRepository(appExecutors, userService, database);
        routineRepository = new RoutineRepository(appExecutors,routineService, database );
    }
}