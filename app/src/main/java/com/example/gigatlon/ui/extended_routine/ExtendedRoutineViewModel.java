package com.example.gigatlon.ui.extended_routine;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ExtendedRoutineViewModel extends ViewModel {

    private List<Cycle> list;
    private MutableLiveData<List<Cycle>> mlist;
    public ExtendedRoutineViewModel() {

        mlist = new MutableLiveData<>();

        list = new ArrayList<>();
        mlist.setValue(list);
    }




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

        return routine;
    }

    public Integer getRoutinId(){
        return routineId.getValue();
    }

    public LiveData<Resource<List<Cycle>>> getCycles(){
        more();
        return list;
    }
    public LiveData<List<Cycle>> getListData(){
        return mlist;
    }











}