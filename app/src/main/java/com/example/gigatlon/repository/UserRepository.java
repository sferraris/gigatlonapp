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
import com.example.gigatlon.api.model.PagedList;
import com.example.gigatlon.api.model.Routine;
import com.example.gigatlon.api.model.Token;
import com.example.gigatlon.api.model.User;
import com.example.gigatlon.api.model.UserWithPassword;
import com.example.gigatlon.api.model.UserWithoutPassword;
import com.example.gigatlon.api.model.Weighting;
import com.example.gigatlon.api.model.WeightingWithDate;

public class UserRepository {

    private final ApiUserService apiService;
    private UserDao dao;
    private AppDatabase db;

    public UserRepository(Application application) {
        db = Room.databaseBuilder(application, AppDatabase.class, "database").build();
        dao = db.userDao();
        this.apiService = ApiClient.create(application.getApplicationContext(), ApiUserService.class);
    }

    public LiveData<Resource<User>> createUser(UserWithPassword userWithPassword) {
        return new NetworkBoundResource<User, User>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<User>> createCall() {
                return apiService.createUser(userWithPassword);
            }
        }.asLiveData();
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

    public LiveData<Resource<User>> updateCurrentUser(UserWithoutPassword userWithoutPassword) {
        return new NetworkBoundResource<User, User>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<User>> createCall() {
                return apiService.updateCurrentUser(userWithoutPassword);
            }
        }.asLiveData();
    }

    public LiveData<Resource<WeightingWithDate>> createWeighting(Weighting weighting) {
        return new NetworkBoundResource<WeightingWithDate, WeightingWithDate>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<WeightingWithDate>> createCall() {
                return apiService.createWeighting(weighting);
            }
        }.asLiveData();
    }

    public LiveData<Resource<PagedList<WeightingWithDate>>> getWeightings() {
        return new NetworkBoundResource<PagedList<WeightingWithDate>, PagedList<WeightingWithDate>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<WeightingWithDate>>> createCall() {
                return apiService.getWeightings();
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> setFavourite(int routineId) {
        return new NetworkBoundResource<Void, Void>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.setFavourite(routineId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<PagedList<Routine>>> getFavourites() {
        return new NetworkBoundResource<PagedList<Routine>, PagedList<Routine>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Routine>>> createCall() {
                return apiService.getFavourites();
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> deleteFavourite(int routineId) {
        return new NetworkBoundResource<Void, Void>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.deleteFavourite(routineId);
            }
        }.asLiveData();
    }
}