package com.example.gigatlon.api;

import androidx.lifecycle.LiveData;

import com.example.gigatlon.api.model.Credentials;
import com.example.gigatlon.api.model.Token;
import com.example.gigatlon.api.model.User;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiUserService {
    @POST("user/login")
    LiveData<ApiResponse<Token>> login(@Body Credentials credentials);

    @POST("user/logout")
    LiveData<ApiResponse<Void>> logout();

    @GET("user/current")
    LiveData<ApiResponse<User>> getCurrentUser();
}
