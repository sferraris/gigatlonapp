package com.example.gigatlon.ui.routines;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gigatlon.api.model.RoutineModel;
import com.example.gigatlon.vo.Resource;

import java.util.ArrayList;
import java.util.List;

public class RoutinesViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<String>> mlist;
    private List<String> list;
    private final static int PAGE_SIZE = 10;

    private int sportPage = 0;
    private boolean isLastSportPage = false;
    private final List<RoutineModel> allSports = new ArrayList<>();
    private final MediatorLiveData<Resource<List<RoutineModel>>> sports = new MediatorLiveData<>();
    private final MutableLiveData<Integer> sportId = new MutableLiveData<>();
    private final LiveData<Resource<RoutineModel>> sport = null;
    private final MediatorLiveData<Resource<RoutineModel>> addSport = new MediatorLiveData<>();

    public RoutinesViewModel() {
        mText = new MutableLiveData<>();
        mlist = new MutableLiveData<>();

        mText.setValue("This is routines fragment");
        list = new ArrayList<>();
        mlist.setValue(list);
    }
    /*

    public SportViewModel(SportRepository repository) {
        super(repository);

        sport = Transformations.switchMap(sportId, sportId -> {
            if (sportId == null) {
                return AbsentLiveData.create();
            } else {
                return repository.getSport(sportId);
            }
        });
    }*/

/*
    public LiveData<Resource<List<RoutineModel>>> getRoutines(){
        getMoreRoutines();
        return sports;
    }
    public void getMoreRoutines() {
        if (isLastSportPage)
            return;

        sports.addSource(repository.getSports(sportPage, PAGE_SIZE), resource -> {
            if (resource.status == Status.SUCCESS) {
                if ((resource.data.size() == 0) || (resource.data.size() < PAGE_SIZE))
                    isLastSportPage = true;

                sportPage++;

                allSports.addAll(resource.data);
                sports.setValue(Resource.success(allSports));
            } else if (resource.status == Status.LOADING) {
                sports.setValue(resource);
            }
        });
    }

    public LiveData<Resource<Sport>> getSport() {
        return sport;
    }

    public LiveData<Resource<Sport>> addSport(Sport sport) {
        return repository.addSport(sport);
    }

    public LiveData<Resource<Sport>> modifySport(Sport sport) {
        return repository.modifySport(sport);
    }

    public LiveData<Resource<Void>> deleteSport(Sport sport) {
        return repository.deleteSport(sport);
    }

    public void setSportId(int sportId) {
        if ((this.sportId.getValue() != null) &&
                (sportId == this.sportId.getValue())) {
            return;
        }

        this.sportId.setValue(sportId);
    }*/


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