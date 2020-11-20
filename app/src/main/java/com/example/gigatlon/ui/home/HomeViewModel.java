package com.example.gigatlon.ui.home;

import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gigatlon.domain.Routine;
import com.example.gigatlon.repository.RoutineRepository;
import com.example.gigatlon.ui.MainActivity;
import com.example.gigatlon.viewmodel.RepositoryViewModel;
import com.example.gigatlon.vo.Resource;
import com.example.gigatlon.vo.Status;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends RepositoryViewModel<RoutineRepository> {



    private final static int PAGE_SIZE = 10;

    private int routinePage = 0;
    private boolean isLastRutinePage = false;
    private final List<Routine> allRoutines = new ArrayList<>();
    private final MediatorLiveData<Resource<List<Routine>>> routines = new MediatorLiveData<>();
    private final MutableLiveData<Integer> routineId = new MutableLiveData<>();
    private final LiveData<Resource<Routine>> routine = null;
    private final MediatorLiveData<Resource<Routine>> addRoutine = new MediatorLiveData<>();

    public HomeViewModel(RoutineRepository repository) {
        super(repository);

    }
    public LiveData<Resource<List<Routine>>> getRoutines(){
        getMoreRoutines();
        return routines;
    }
    public void getMoreRoutines() {
        if (isLastRutinePage)
            return;

        routines.addSource(repository.getMyRoutines(PAGE_SIZE, routinePage, "dateCreated", "asc"), resource -> {
            if (resource.status == Status.SUCCESS) {
                if ((resource.data.size() == 0) || (resource.data.size() < PAGE_SIZE))
                    isLastRutinePage = true;

                routinePage++;

                allRoutines.addAll(resource.data);
                routines.setValue(Resource.success(allRoutines));
            } else if (resource.status == Status.LOADING) {
                Log.d("UI", "loading");
                routines.setValue(resource);
            }else{
                Log.d("UI", resource.message);
            }
        });

    }

    public  LiveData<Resource<Routine>> getR(){
        return repository.getRoutine(1);
    }






}