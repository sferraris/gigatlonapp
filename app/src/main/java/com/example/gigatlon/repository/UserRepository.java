package com.example.gigatlon.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.gigatlon.api.ApiClient;
import com.example.gigatlon.api.ApiResponse;
import com.example.gigatlon.api.ApiUserService;
import com.example.gigatlon.api.model.Credentials;
import com.example.gigatlon.api.model.Token;
import com.example.gigatlon.api.model.User;

public class UserRepository {

    private final ApiUserService apiService;

    public UserRepository(Context context) {
        this.apiService = ApiClient.create(context, ApiUserService.class);
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