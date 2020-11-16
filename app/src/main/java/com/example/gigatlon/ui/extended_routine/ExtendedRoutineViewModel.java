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




    public void addElement(Cycle el){
        list.add(el);
    }

    public List<Cycle> getList(){
        return list;
    }
    public LiveData<List<Cycle>> getListData(){
        return mlist;
    }



}