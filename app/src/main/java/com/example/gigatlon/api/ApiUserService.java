package com.example.gigatlon.api;

import androidx.lifecycle.LiveData;

import com.example.gigatlon.api.model.Credentials;
import com.example.gigatlon.api.model.PagedList;
import com.example.gigatlon.api.model.Routine;
import com.example.gigatlon.api.model.Token;
import com.example.gigatlon.api.model.User;
import com.example.gigatlon.api.model.UserWithPassword;
import com.example.gigatlon.api.model.UserWithoutPassword;
import com.example.gigatlon.api.model.Weighting;
import com.example.gigatlon.api.model.WeightingWithDate;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiUserService {
    @POST("user")
    LiveData<ApiResponse<User>> createUser(@Body UserWithPassword userWithPassword);

    @POST("user/login")
    LiveData<ApiResponse<Token>> login(@Body Credentials credentials);

    @POST("user/logout")
    LiveData<ApiResponse<Void>> logout();

    @GET("user/current")
    LiveData<ApiResponse<User>> getCurrentUser();

    @PUT("user/current")
    LiveData<ApiResponse<User>> updateCurrentUser(@Body UserWithoutPassword userWithoutPassword);

    @POST("user/current/weightings")
    LiveData<ApiResponse<WeightingWithDate>> createWeighting(@Body Weighting weighting);

    @GET("user/current/weightings")
    LiveData<ApiResponse<PagedList<WeightingWithDate>>> getWeightings();

    @POST("user/current/routines/{routineId}/favourites")
    LiveData<ApiResponse<Void>> setFavourite(@Path("routineId") int routineId);

    @GET("user/current/routines/favourites")
    LiveData<ApiResponse<PagedList<Routine>>> getFavourites();

    @DELETE("user/current/routines/{routineId}/favourites")
    LiveData<ApiResponse<Void>> deleteFavourite(@Path("routineId") int routineId);
}