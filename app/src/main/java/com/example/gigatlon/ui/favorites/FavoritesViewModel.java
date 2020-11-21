package com.example.gigatlon.ui.favorites;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gigatlon.domain.Routine;
import com.example.gigatlon.repository.RoutineRepository;
import com.example.gigatlon.viewmodel.RepositoryViewModel;
import com.example.gigatlon.vo.Resource;
import com.example.gigatlon.vo.Status;

import java.util.ArrayList;
import java.util.List;

public class FavoritesViewModel extends RepositoryViewModel<RoutineRepository> {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<String>> mlist;
    private List<String> list;
    private final static int PAGE_SIZE = 10;

    private int routinePage = 0;
    private boolean isLastRutinePage = false;
    private final List<Routine> allRoutines = new ArrayList<>();
    private final MediatorLiveData<Resource<List<Routine>>> routines = new MediatorLiveData<>();
    private final MutableLiveData<Integer> routineId = new MutableLiveData<>();
    private final LiveData<Resource<Routine>> routine = null;
    private final MediatorLiveData<Resource<Routine>> addRoutine = new MediatorLiveData<>();

    public FavoritesViewModel(RoutineRepository repository) {
        super(repository);
        mText = new MutableLiveData<>();
        mlist = new MutableLiveData<>();

        mText.setValue("This is routines fragment");
        list = new ArrayList<>();
        mlist.setValue(list);
    }

    public LiveData<Resource<List<Routine>>> getRoutines(){
        getMoreRoutines();
        return routines;
    }
    public void getMoreRoutines() {
        if (isLastRutinePage)
            return;

        routines.addSource(repository.getFavourites(PAGE_SIZE, routinePage, "dateCreated", "asc"), resource -> {
            if (resource.status == Status.SUCCESS) {
                if ((resource.data.size() == 0) || (resource.data.size() < PAGE_SIZE))
                    isLastRutinePage = true;

                routinePage++;

                allRoutines.addAll(resource.data);
                routines.setValue(Resource.success(allRoutines));
            } else if (resource.status == Status.LOADING) {
                routines.setValue(resource);
            }
        });
    }

    public LiveData<Resource<List<Routine>>> filterRoutines(String f, String dir){
        Filter(f, dir);
        return routines;
    }
    public void Filter(String s, String dir){
        routinePage = 0;

        routines.addSource(repository.getFavourites(PAGE_SIZE, routinePage, s, dir), resource -> {
            if (resource.status == Status.SUCCESS) {
                if ((resource.data.size() == 0) || (resource.data.size() < PAGE_SIZE))
                    isLastRutinePage = true;

                routinePage++;
                allRoutines.clear();
                allRoutines.addAll(resource.data);
                routines.setValue(Resource.success(allRoutines));
            } else if (resource.status == Status.LOADING) {
                routines.setValue(resource);
            }
        });
    }

    public void addElement(String el){
        list.add(el);
    }

    public List<String> getList(){
        return list;
    }
    public LiveData<List<String>> getListData(){
        return mlist;
    }



    public LiveData<String> getText() {
        return mText;
    }
}