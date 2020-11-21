package com.example.gigatlon.api;

import androidx.lifecycle.LiveData;

import com.example.gigatlon.api.model.CredentialsModel;
import com.example.gigatlon.api.model.EmailModel;
import com.example.gigatlon.api.model.PagedListModel;
import com.example.gigatlon.api.model.RoutineModel;
import com.example.gigatlon.api.model.TokenModel;
import com.example.gigatlon.api.model.UserModel;
import com.example.gigatlon.api.model.UserWithPasswordModel;
import com.example.gigatlon.api.model.UserWithoutPasswordModel;
import com.example.gigatlon.api.model.VerifyEmailModel;
import com.example.gigatlon.api.model.WeightingModel;
import com.example.gigatlon.api.model.WeightingWithDateModel;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiUserService {
    @POST("user")
    LiveData<ApiResponse<UserModel>> createUser(@Body UserWithPasswordModel userWithPasswordModel);

    @POST("user/login")
    LiveData<ApiResponse<TokenModel>> login(@Body CredentialsModel credentialsModel);

    @POST("user/logout")
    LiveData<ApiResponse<Void>> logout();

    @GET("user/current")
    LiveData<ApiResponse<UserModel>> getCurrentUser();

    @PUT("user/current")
    LiveData<ApiResponse<UserModel>> updateCurrentUser(@Body UserWithoutPasswordModel userWithoutPasswordModel);

    @POST("user/current/weightings")
    LiveData<ApiResponse<WeightingWithDateModel>> createWeighting(@Body WeightingModel weightingModel);

    @GET("user/current/weightings")
    LiveData<ApiResponse<PagedListModel<WeightingWithDateModel>>> getWeightings(@Query("page") int page, @Query("size") int size, @Query("orderBy") String orderBy, @Query("direction") String direction);

    @POST("user/current/routines/{routineId}/favourites")
    LiveData<ApiResponse<Void>> setFavourite(@Path("routineId") int routineId);

    @GET("user/current/routines/favourites")
    LiveData<ApiResponse<PagedListModel<RoutineModel>>> getFavourites(@Query("page") int page, @Query("size") int size);

    @DELETE("user/current/routines/{routineId}/favourites")
    LiveData<ApiResponse<Void>> deleteFavourite(@Path("routineId") int routineId);

    @POST("user/verify_email")
    LiveData<ApiResponse<Void>> verifyEmail(@Body VerifyEmailModel verifyEmailModel);

    @POST("user/resend_verification")
    LiveData<ApiResponse<Void>> resendEmail(@Body EmailModel emailModel);

}