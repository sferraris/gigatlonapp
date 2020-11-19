package com.example.gigatlon.api;

import androidx.lifecycle.LiveData;

import com.example.gigatlon.api.model.CycleModel;
import com.example.gigatlon.api.model.ExerciseModel;
import com.example.gigatlon.api.model.PagedListModel;
import com.example.gigatlon.api.model.RoutineModel;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRoutineService {
    @GET("routines")
    LiveData<ApiResponse<PagedListModel<RoutineModel>>> getAll(@Query("page") int page, @Query("size") int size, @Query("orderBy") String orderBy, @Query("direction") String direction);

    @GET("user/current/routines")
    LiveData<ApiResponse<PagedListModel<RoutineModel>>> getMyRoutines(@Query("page") int page, @Query("size") int size, @Query("orderBy") String orderBy, @Query("direction") String direction);

    @POST("user/current/routines/{routineId}/favourites")
    LiveData<ApiResponse<Void>> setFavourite(@Path("routineId") int routineId);

    @GET("user/current/routines/favourites")
    LiveData<ApiResponse<PagedListModel<RoutineModel>>> getFavourites(@Query("page") int page, @Query("size") int size, @Query("orderBy") String orderBy, @Query("direction") String direction);

    @DELETE("user/current/routines/{routineId}/favourites")
    LiveData<ApiResponse<Void>> deleteFavourite(@Path("routineId") int routineId);

    @GET("routines/{routineId}")
    LiveData<ApiResponse<RoutineModel>> getRoutineById(@Path("routineId") int routineId);

    @GET("routines/{routineId}/cycles")
    LiveData<ApiResponse<PagedListModel<CycleModel>>> getCycles(@Path("routineId") int routineId, @Query("page") int page, @Query("size") int size, @Query("orderBy") String orderBy, @Query("direction") String direction);

    @GET("routine/{routineId}/cycles/{cycleId}/exercises")
    LiveData<ApiResponse<PagedListModel<ExerciseModel>>> getExercises(@Path("routineId") int routineId, @Path("cycleId") int cycleId, @Query("page") int page, @Query("size") int size, @Query("orderBy") String orderBy, @Query("direction") String direction);
}