package com.example.gigatlon.repository;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.gigatlon.api.ApiClient;
import com.example.gigatlon.api.ApiResponse;
import com.example.gigatlon.api.ApiUserService;
import com.example.gigatlon.api.database.AppDatabase;
import com.example.gigatlon.api.database.UserDao;
import com.example.gigatlon.api.model.Credentials;
import com.example.gigatlon.api.model.Token;
import com.example.gigatlon.api.model.User;

public class UserRepository {

    private final ApiUserService apiService;
    private UserDao dao;
    private AppDatabase db;

    public UserRepository(Application application) {
        db = Room.databaseBuilder(application, AppDatabase.class, "database").build();
        dao = db.userDao();
        this.apiService = ApiClient.create(application.getApplicationContext(), ApiUserService.class);
    }

    public LiveData<Resource<Token>> login(Credentials credentials) {
        return new NetworkBoundResource<Token, Token>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Token>> createCall() {
                return apiService.login(credentials);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> logout() {
        return new NetworkBoundResource<Void, Void>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.logout();
            }
        }.asLiveData();
    }

    public LiveData<Resource<User>> getCurrentUser() {
        return new NetworkBoundResource<User, User>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<User>> createCall() {
                return apiService.getCurrentUser();
            }
        }.asLiveData();
    }
}