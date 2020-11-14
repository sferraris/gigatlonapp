package com.example.gigatlon.ui.routines;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class RoutinesViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<String>> mlist;
    private List<String> list;

    public RoutinesViewModel() {
        mText = new MutableLiveData<>();
        mlist = new MutableLiveData<>();

        mText.setValue("This is routines fragment");
        list = new ArrayList<>();
        mlist.setValue(list);
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