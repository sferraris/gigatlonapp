package com.example.gigatlon.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.gigatlon.api.ApiResponse;
import com.example.gigatlon.api.ApiUserService;
import com.example.gigatlon.api.model.CredentialsModel;
import com.example.gigatlon.api.model.TokenModel;
import com.example.gigatlon.api.model.UserModel;
import com.example.gigatlon.api.model.UserWithPasswordModel;
import com.example.gigatlon.db.MyDatabase;
import com.example.gigatlon.db.entity.UserEntity;
import com.example.gigatlon.domain.User;
import com.example.gigatlon.vo.AbsentLiveData;
import com.example.gigatlon.vo.Resource;

import java.util.concurrent.TimeUnit;

public class UserRepository {

    private static final String RATE_LIMITER_GETUSER_KEY = "@@getuser@@";

    private AppExecutors executors;
    private ApiUserService service;
    private MyDatabase database;
    private RateLimiter<String> rateLimit = new RateLimiter<>(10, TimeUnit.MINUTES);

    public UserRepository(AppExecutors executors, ApiUserService service, MyDatabase database) {
        this.executors = executors;
        this.service = service;
        this.database = database;
    }

    private User mapUserEntityToDomain (UserEntity entity) {
        return new User(entity.id, entity.username, entity.fullName, entity.gender, entity.birthdate, entity.email, entity.avatarUrl);
    }

    private UserEntity mapUserModelToEntity (UserModel model) {
        return new UserEntity(model.getId(), model.getUsername(), model.getFullName(), model.getGender(), model.getBirthdate(), model.getEmail(), model.getAvatarUrl());
    }

    private User mapUserModelToDomain (UserModel model) {
        return new User(model.getId(), model.getUsername(), model.getFullName(), model.getGender(), model.getBirthdate(), model.getEmail(), model.getAvatarUrl());
    }

    private UserWithPasswordModel mapUserDomainToUserWithPassword(User user) {
        return new UserWithPasswordModel(user.getUsername(), user.getPassword(), user.getFullName(), user.getGender(), user.getBirthdate(), user.getEmail(), user.getPhone(), user.getAvatarUrl());
    }

    public LiveData<Resource<User>> createUser(User user) {
        return new NetworkBoundResource<User, Void, UserModel>(executors, null, null, this::mapUserModelToDomain)
        {
            @Override
            protected void saveCallResult(@NonNull Void entity) {
            }

            @Override
            protected boolean shouldFetch(@Nullable Void entity) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable UserModel model) {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<Void> loadFromDb() {
                return AbsentLiveData.create();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<UserModel>> createCall() {
                UserWithPasswordModel model = mapUserDomainToUserWithPassword(user);
                return service.createUser(model);
            }
        }.asLiveData();
    }

    public LiveData<Resource<String>> login(String username, String password) {

        return new NetworkBoundResource<String, Void, TokenModel>(executors,null, null, model -> model.getToken()) {

            @Override
            protected void saveCallResult(@NonNull Void entity) {
            }

            @Override
            protected boolean shouldFetch(@Nullable Void entity) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable TokenModel model) {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<Void> loadFromDb() {
                return AbsentLiveData.create();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<TokenModel>> createCall() {
                return service.login(new CredentialsModel(username, password));
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> logout() {

        return new NetworkBoundResource<Void, Void, Void>
                (executors, null, null, null) {

            @Override
            protected void saveCallResult(@NonNull Void entity) {
            }

            @Override
            protected boolean shouldFetch(@Nullable Void entity) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable Void model) {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<Void> loadFromDb() {
                return AbsentLiveData.create();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return service.logout();
            }
        }.asLiveData();
    }

    public LiveData<Resource<User>> getCurrentUser() {
        return new NetworkBoundResource<User, UserEntity, UserModel>(executors, this::mapUserEntityToDomain, this::mapUserModelToEntity, this::mapUserModelToDomain)
        {
            @Override
            protected void saveCallResult(@NonNull UserEntity entity) {
                database.userDao().insert(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable UserEntity entity) {
                return (entity == null) || rateLimit.shouldFetch(RATE_LIMITER_GETUSER_KEY);
            }

            @Override
            protected boolean shouldPersist(@Nullable UserModel model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<UserEntity> loadFromDb() {
                return database.userDao().get();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<UserModel>> createCall() {
                return service.getCurrentUser();
            }
        }.asLiveData();
    }
/*
    public LiveData<Resource<UserModel>> updateCurrentUser(UserWithoutPasswordModel userWithoutPasswordModel) {
        return new NetworkBoundResource<UserModel, UserModel>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<UserModel>> createCall() {
                return apiService.updateCurrentUser(userWithoutPasswordModel);
            }
        }.asLiveData();
    }

    public LiveData<Resource<WeightingWithDateModel>> createWeighting(WeightingModel weightingModel) {
        return new NetworkBoundResource<WeightingWithDateModel, WeightingWithDateModel>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<WeightingWithDateModel>> createCall() {
                return apiService.createWeighting(weightingModel);
            }
        }.asLiveData();
    }

    public LiveData<Resource<PagedListModel<WeightingWithDateModel>>> getWeightings() {
        return new NetworkBoundResource<PagedListModel<WeightingWithDateModel>, PagedListModel<WeightingWithDateModel>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedListModel<WeightingWithDateModel>>> createCall() {
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

    public LiveData<Resource<PagedListModel<RoutineModel>>> getFavourites() {
        return new NetworkBoundResource<PagedListModel<RoutineModel>, PagedListModel<RoutineModel>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedListModel<RoutineModel>>> createCall() {
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

 */
}