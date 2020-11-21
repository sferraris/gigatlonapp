package com.example.gigatlon.ui.extended_routine;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.gigatlon.domain.Cycle;
import com.example.gigatlon.domain.Routine;
import com.example.gigatlon.repository.RoutineRepository;
import com.example.gigatlon.viewmodel.RepositoryViewModel;
import com.example.gigatlon.vo.AbsentLiveData;
import com.example.gigatlon.vo.Resource;

import java.util.ArrayList;
import java.util.List;

public class ExtendedRoutineViewModel extends RepositoryViewModel<RoutineRepository> {



    private final static int PAGE_SIZE = 500;


    private final MutableLiveData<Integer> routineId = new MutableLiveData<>();
    private  LiveData<Resource<Routine>> routine = null;
    MediatorLiveData<Resource<List<Cycle>>> list = new MediatorLiveData<>();
    List<Cycle> all = new ArrayList<>();
    MediatorLiveData<Resource<List<Routine>>> listFav = new MediatorLiveData<>();
    List<Routine> favData = new ArrayList<>();



    public ExtendedRoutineViewModel(RoutineRepository repository) {
        super(repository);
        routine = Transformations.switchMap(routineId, routinId -> {
            if (routinId == null) {
                return AbsentLiveData.create();
            } else {
                return repository.getRoutine(routinId);
            }

        });
    }
    public LiveData<Resource<Routine>> getRoutine() {

        return routine;
    }

    public Integer getRoutinId(){
        return routineId.getValue();
    }

    public LiveData<Resource<List<Cycle>>> getCycles(){
        more();
        return list;
    }

    public void more(){

        list.addSource(repository.getCycles(routineId.getValue(),PAGE_SIZE ,0, "order", "asc" ), r->{
            switch (r.status){
                case LOADING:break;
                case SUCCESS:
                    all.clear();
                    all.addAll(r.data);
                    list.setValue(Resource.success(all));
                    break;
                case ERROR:
                    break;
            }
        });
    }

    public void setRoutine(int routineId) {
        if ((this.routineId.getValue() != null) &&
                (routineId == this.routineId.getValue())) {
            return;
        }

        this.routineId.setValue(routineId);
    }

    public LiveData<Resource<List<Routine>>> getFav(){
        moreFav();
        return  listFav;
    }
    public void moreFav(){
        listFav.addSource( repository.getFavourites( PAGE_SIZE, 0, "id", "asc"), r ->{
            switch (r.status){
                case LOADING:
                    //Log.d("UI", "");
                    break;
                case SUCCESS:
                    favData.clear();
                    favData.addAll(r.data);
                    listFav.setValue(Resource.success(favData));
                    break;

                case ERROR:
                    Log.d("UI", r.message);

            }
        });
    }










}