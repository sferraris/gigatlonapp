package com.example.gigatlon;

import android.app.Application;

import com.example.gigatlon.repository.UserRepository;

public class MyApplication extends Application {

    private UserRepository userRepository;

    public UserRepository getUserRepository() {
        return userRepository;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        userRepository = new UserRepository(this);
    }
}
