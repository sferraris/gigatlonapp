package com.example.gigatlon.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.gigatlon.api.ApiResponse;
import com.example.gigatlon.api.ApiRoutineService;
import com.example.gigatlon.api.model.CreatorModel;
import com.example.gigatlon.api.model.CycleModel;
import com.example.gigatlon.api.model.ExerciseModel;
import com.example.gigatlon.api.model.PagedListModel;
import com.example.gigatlon.api.model.RatingModel;
import com.example.gigatlon.api.model.RoutineModel;
import com.example.gigatlon.db.MyDatabase;
import com.example.gigatlon.db.entity.FavouriteRoutineEntity;
import com.example.gigatlon.db.entity.MyRoutineEntity;
import com.example.gigatlon.db.entity.RoutineEntity;
import com.example.gigatlon.domain.Cycle;
import com.example.gigatlon.domain.Exercise;
import com.example.gigatlon.domain.Routine;
import com.example.gigatlon.vo.AbsentLiveData;
import com.example.gigatlon.vo.Resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class RoutineRepository {

    private AppExecutors executors;
    private ApiRoutineService service;
    private MyDatabase database;

    public RoutineRepository(AppExecutors executors, ApiRoutineService service, MyDatabase database) {
        this.executors = executors;
        this.service = service;
        this.database = database;
    }

    private Routine mapRoutineEntityToDomain(RoutineEntity entity) {
        return new Routine(entity.id, entity.name, entity.detail, new Date("11/12/99"), entity.averageRating, entity.isPublic, entity.difficulty, entity.creator);
    }

    private RoutineEntity mapRoutineModelToEntity(RoutineModel model) {
        return new RoutineEntity(model.getId(), model.getName(), model.getDetail(), model.getDateCreated(), model.getAverageRating(), model.getIsPublic(), model.getDifficulty(), model.getCreatorModel().getUsername());
    }

    private Routine mapRoutineModelToDomain(RoutineModel model) {
        return new Routine(model.getId(), model.getName(), model.getDetail(), model.getDateCreated(), model.getAverageRating(), model.getIsPublic(), model.getDifficulty(), model.getCreatorModel().getUsername());
    }

    private Void mapModelToDomainVoid(Void avoid){
        return avoid;
    }

    public LiveData<Resource<List<Routine>>> getAll(int size, int page, String orderBy, String direction) {
        return new NetworkBoundResource<List<Routine>, List<RoutineEntity>, PagedListModel<RoutineModel>>(executors, entities -> {
            return entities.stream()
                    .map(routineEntity -> new Routine(routineEntity.id, routineEntity.name, routineEntity.detail, new Date("11/12/99"), routineEntity.averageRating, routineEntity.isPublic, routineEntity.difficulty, routineEntity.creator)) //TODO ARREGLAR
                    .collect(toList());
        },
                model -> {
                    return model.getResults().stream()
                            .map(routineModel -> new RoutineEntity(routineModel.getId(), routineModel.getName(), routineModel.getDetail(), routineModel.getDateCreated(), routineModel.getAverageRating(), routineModel.getIsPublic(), routineModel.getDifficulty(), routineModel.getCreatorModel().getUsername()))
                            .collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(routineModel -> new Routine(routineModel.getId(), routineModel.getName(), routineModel.getDetail(), routineModel.getDateCreated(), routineModel.getAverageRating(), routineModel.getIsPublic(), routineModel.getDifficulty(), routineModel.getCreatorModel().getUsername()))
                            .collect(toList());
                })
        {
            @Override
            protected void saveCallResult(@NonNull List<RoutineEntity> entities) {
                database.routineDao().delete(size, page * size);
                database.routineDao().insert(entities);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<RoutineEntity> entities) {
                return ((entities == null) || (entities.size() == 0));
            }

            @Override
            protected boolean shouldPersist(@Nullable PagedListModel<RoutineModel> model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<RoutineEntity>> loadFromDb() {
                return database.routineDao().findAll(size, page * size);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedListModel<RoutineModel>>> createCall() {
                return service.getAll(page, size, orderBy, direction);
            }
        }.asLiveData();
    }

    public LiveData<Resource<List<Routine>>> getMyRoutines(int size, int page, String orderBy, String direction) {
        return new NetworkBoundResource<List<Routine>, List<MyRoutineEntity>, PagedListModel<RoutineModel>>(executors, entities -> {
            return entities.stream()
                    .map(myRoutineEntity -> new Routine(myRoutineEntity.id, myRoutineEntity.name, myRoutineEntity.detail, new Date("11/12/99"), myRoutineEntity.averageRating, myRoutineEntity.isPublic, myRoutineEntity.difficulty, myRoutineEntity.creator)) //TODO ARREGLAR
                    .collect(toList());
        },
                model -> {
                    return model.getResults().stream()
                            .map(routineModel -> new MyRoutineEntity(routineModel.getId(), routineModel.getName(), routineModel.getDetail(), routineModel.getDateCreated(), routineModel.getAverageRating(), routineModel.getIsPublic(), routineModel.getDifficulty(), routineModel.getCreatorModel().getUsername()))
                            .collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(routineModel -> new Routine(routineModel.getId(), routineModel.getName(), routineModel.getDetail(), routineModel.getDateCreated(), routineModel.getAverageRating(), routineModel.getIsPublic(), routineModel.getDifficulty(), routineModel.getCreatorModel().getUsername()))
                            .collect(toList());
                })
        {
            @Override
            protected void saveCallResult(@NonNull List<MyRoutineEntity> entities) {
                database.myRoutineDao().delete(size, page * size);
                database.myRoutineDao().insert(entities);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<MyRoutineEntity> entities) {
                return ((entities == null) || (entities.size() == 0));
            }

            @Override
            protected boolean shouldPersist(@Nullable PagedListModel<RoutineModel> model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<MyRoutineEntity>> loadFromDb() {
                return database.myRoutineDao().findAll(size, page * size);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedListModel<RoutineModel>>> createCall() {
                return service.getMyRoutines(page, size, orderBy, direction);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> setFavourite(int routineId) {
        return new NetworkBoundResource<Void, Void, Void>(executors, null, null, this::mapModelToDomainVoid)
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

    public LiveData<Resource<List<Routine>>> getFavourites(int size, int page, String orderBy, String direction) {
        return new NetworkBoundResource<List<Routine>, List<FavouriteRoutineEntity>, PagedListModel<RoutineModel>>(executors, entities -> {
            return entities.stream()
                    .map(favouriteRoutineEntity -> new Routine(favouriteRoutineEntity.id, favouriteRoutineEntity.name, favouriteRoutineEntity.detail, new Date("11/12/99"), favouriteRoutineEntity.averageRating, favouriteRoutineEntity.isPublic, favouriteRoutineEntity.difficulty, favouriteRoutineEntity.creator)) //TODO ARREGLAR
                    .collect(toList());
        },
                model -> {
                    return model.getResults().stream()
                            .map(routineModel -> new FavouriteRoutineEntity(routineModel.getId(), routineModel.getName(), routineModel.getDetail(), routineModel.getDateCreated(), routineModel.getAverageRating(), routineModel.getIsPublic(), routineModel.getDifficulty(), routineModel.getCreatorModel().getUsername()))
                            .collect(toList());
                },
                model -> {
                    return model.getResults().stream()
                            .map(routineModel -> new Routine(routineModel.getId(), routineModel.getName(), routineModel.getDetail(), routineModel.getDateCreated(), routineModel.getAverageRating(), routineModel.getIsPublic(), routineModel.getDifficulty(), routineModel.getCreatorModel().getUsername()))
                            .collect(toList());
                })
        {
            @Override
            protected void saveCallResult(@NonNull List<FavouriteRoutineEntity> entities) {
                database.favouriteRoutineDao().delete(size, page * size);
                database.favouriteRoutineDao().insert(entities);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<FavouriteRoutineEntity> entities) {
               // return ((entities == null) || (entities.size() == 0));
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable PagedListModel<RoutineModel> model) {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<List<FavouriteRoutineEntity>> loadFromDb() {
                return database.favouriteRoutineDao().findAll(size, page * size);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedListModel<RoutineModel>>> createCall() {
                return service.getFavourites(page, size, orderBy, direction);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> deleteFavourite(int routineId) {
        return new NetworkBoundResource<Void, Void, Void>(executors, null, null, this::mapModelToDomainVoid)
        {
            @Override
            protected void saveCallResult(@NonNull Void entity) {
                  //  database.myRoutineDao().delete();
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
                return service.deleteFavourite(routineId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Routine>> getRoutine(int routineId) {
        return new NetworkBoundResource<Routine, RoutineEntity, RoutineModel>(executors, this::mapRoutineEntityToDomain, this::mapRoutineModelToEntity, this::mapRoutineModelToDomain) {

            @Override
            protected void saveCallResult(@NonNull RoutineEntity entity) {
                database.routineDao().insert(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable RoutineEntity entity) {
                return (entity == null);
            }

            @Override
            protected boolean shouldPersist(@Nullable RoutineModel model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<RoutineEntity> loadFromDb() {
                return database.routineDao().findById(routineId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RoutineModel>> createCall() {
                return service.getRoutineById(routineId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<List<Cycle>>> getCycles(int routineId, int size, int page, String orderBy, String direction) {
        return new NetworkBoundResource<List<Cycle>, Void, PagedListModel<CycleModel>>(executors, null, null,
                model -> {
                    return model.getResults().stream()
                            .map(cycleModel -> new Cycle(cycleModel.getId(), cycleModel.getName(), cycleModel.getDetail(), cycleModel.getType(), cycleModel.getOrder(), cycleModel.getRepetitions()))
                            .collect(toList());
                })
        {
            @Override
            protected void saveCallResult(@NonNull Void entities) {

            }

            @Override
            protected boolean shouldFetch(@Nullable Void entities) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable PagedListModel<CycleModel> model) {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<Void> loadFromDb() {
                return AbsentLiveData.create();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedListModel<CycleModel>>> createCall() {
                return service.getCycles(routineId, page, size, orderBy, direction);
            }
        }.asLiveData();
    }

    public LiveData<Resource<List<Exercise>>> getExercises(int routineId, int cycleId, int size, int page, String orderBy, String direction) {
        return new NetworkBoundResource<List<Exercise>, Void, PagedListModel<ExerciseModel>>(executors, null, null,
                model -> {
                    return model.getResults().stream()
                            .map(exerciseModel -> new Exercise(exerciseModel.getId(), exerciseModel.getName(), exerciseModel.getDetail(), exerciseModel.getType(), exerciseModel.getDuration(), exerciseModel.getRepetitions(), exerciseModel.getOrder()))
                            .collect(toList());
                })
        {
            @Override
            protected void saveCallResult(@NonNull Void entities) {

            }

            @Override
            protected boolean shouldFetch(@Nullable Void entities) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable PagedListModel<ExerciseModel> model) {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<Void> loadFromDb() {
                return AbsentLiveData.create();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedListModel<ExerciseModel>>> createCall() {
                return service.getExercises(routineId, cycleId, page, size, orderBy, direction);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Routine>> setRating(int rating, int routineId) {
        return new NetworkBoundResource<Routine, Void, RoutineModel>(executors, null, null, this::mapRoutineModelToDomain)
        {
            @Override
            protected void saveCallResult(@NonNull Void entities) {

            }

            @Override
            protected boolean shouldFetch(@Nullable Void entities) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable RoutineModel model) {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<Void> loadFromDb() {
                return AbsentLiveData.create();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RoutineModel>> createCall() {
                return service.setRating(routineId, new RatingModel(rating, ""));
            }
        }.asLiveData();
    }
}