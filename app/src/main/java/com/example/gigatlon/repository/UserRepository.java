package com.example.gigatlon.repository;

import android.icu.text.SimpleDateFormat;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.gigatlon.api.ApiResponse;
import com.example.gigatlon.api.ApiUserService;
import com.example.gigatlon.api.model.CredentialsModel;
import com.example.gigatlon.api.model.PagedListModel;
import com.example.gigatlon.api.model.TokenModel;
import com.example.gigatlon.api.model.UserModel;
import com.example.gigatlon.api.model.UserWithPasswordModel;
import com.example.gigatlon.api.model.UserWithoutPasswordModel;
import com.example.gigatlon.api.model.WeightingModel;
import com.example.gigatlon.api.model.WeightingWithDateModel;
import com.example.gigatlon.db.MyDatabase;
import com.example.gigatlon.db.entity.UserEntity;
import com.example.gigatlon.db.entity.WeightingEntity;
import com.example.gigatlon.domain.User;
import com.example.gigatlon.domain.Weighting;
import com.example.gigatlon.vo.AbsentLiveData;
import com.example.gigatlon.vo.Resource;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toList;

public class UserRepository {

    private static final String RATE_LIMITER_GETUSER_KEY = "@@getuser@@";

    private AppExecutors executors;
    private ApiUserService service;
    private MyDatabase database;
    private RateLimiter<String> rateLimit = new RateLimiter<>(10, TimeUnit.MINUTES);
    boolean should_fetch_weight = true;

    public UserRepository(AppExecutors executors, ApiUserService service, MyDatabase database) {
        this.executors = executors;
        this.service = service;
        this.database = database;
    }

    private User mapUserEntityToDomain (UserEntity entity) {
        Date real_date = new Date();
        try {
            real_date=new SimpleDateFormat("dd/MM/yyyy").parse(entity.birthdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new User(entity.id, entity.username, entity.fullName, entity.gender, real_date, entity.email, entity.avatarUrl);
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

    private Weighting mapWeightingEntityToDomain(WeightingEntity entity) {
        Date d = new Date("11/12/99"); //TODO ARREGLARLO
        return new Weighting(entity.id, d, entity.weight, entity.height);
    }

    private WeightingEntity mapWeightingModelToEntity(WeightingWithDateModel model) {
        return new WeightingEntity(model.getId(), model.getDate(), model.getWeight(), model.getHeight());
    }

    private Weighting mapWeightingModelToDomain(WeightingWithDateModel model) {
        return new Weighting(model.getId(), model.getDate(), model.getWeight(), model.getHeight());
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

    public LiveData<Resource<User>> updateCurrentUser(User user) {
        return new NetworkBoundResource<User, UserEntity, UserModel>(executors, this::mapUserEntityToDomain, this::mapUserModelToEntity, this::mapUserModelToDomain)
        {
            @Override
            protected void saveCallResult(@NonNull UserEntity entity) {
                database.userDao().update(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable UserEntity entity) {
                return entity != null;
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
                UserWithoutPasswordModel model = new UserWithoutPasswordModel(user.getUsername(), user.getFullName(), user.getGender(), user.getBirthdate(), user.getEmail(), user.getPhone(), user.getAvatarUrl());
                return service.updateCurrentUser(model);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Weighting>> createWeighting(Weighting weighting) {
        should_fetch_weight = true;
        return new NetworkBoundResource<Weighting, WeightingEntity, WeightingWithDateModel>(executors, this::mapWeightingEntityToDomain, this::mapWeightingModelToEntity, this::mapWeightingModelToDomain)
        {
            int weightingId = 0;



            @Override
            protected void saveCallResult(@NonNull WeightingEntity entity) {
                weightingId = entity.id;
                database.weightingDao().insert(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable WeightingEntity entity) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable WeightingWithDateModel model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<WeightingEntity> loadFromDb() {
                if (weightingId == 0)
                    return AbsentLiveData.create();
                else
                    return database.weightingDao().findById(weightingId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<WeightingWithDateModel>> createCall() {
                WeightingModel model = new WeightingModel(weighting.getWeight(), weighting.getHeight());
                return service.createWeighting(model);
            }
        }.asLiveData();
    }

    public LiveData<Resource<List<Weighting>>> getWeightings(int page, int size, String orderBy, String direction) {
        return new NetworkBoundResource<List<Weighting>, List<WeightingEntity>, PagedListModel<WeightingWithDateModel>>(executors, entities -> {
            return entities.stream()
                    .map(weightingEntity -> new Weighting(weightingEntity.id, new Date("11/12/99"), weightingEntity.weight, weightingEntity.height)) //TODO ARREGLAR
                    .collect(toList());
        },
                model -> {
                    return model.getResults().stream()
                            .map(weightingModel -> new WeightingEntity(weightingModel.getId(), weightingModel.getDate(), weightingModel.getWeight(), weightingModel.getHeight()))
                            .collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(weightingModel -> new Weighting(weightingModel.getId(), weightingModel.getDate(), weightingModel.getWeight(), weightingModel.getHeight()))
                            .collect(toList());
                })
        {
            @Override
            protected void saveCallResult(@NonNull List<WeightingEntity> entities) {
                database.weightingDao().delete(size, page * size);
                database.weightingDao().insert(entities);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<WeightingEntity> entities) {
                boolean fetch = ((entities == null) || (entities.size() == 0) || should_fetch_weight);
                should_fetch_weight = false;
                return fetch;
            }

            @Override
            protected boolean shouldPersist(@Nullable PagedListModel<WeightingWithDateModel> model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<WeightingEntity>> loadFromDb() {
                return database.weightingDao().findAll(size, page * size);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedListModel<WeightingWithDateModel>>> createCall() {
                return service.getWeightings(page, size, orderBy, direction);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> setFavourite(int routineId) {
        return new NetworkBoundResource<Void, Void, Void>(executors, null, null, null)
        {
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
                return service.setFavourite(routineId);
            }
        }.asLiveData();
    }
/*
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