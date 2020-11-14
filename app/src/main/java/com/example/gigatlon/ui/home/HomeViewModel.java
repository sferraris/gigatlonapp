package com.example.gigatlon.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<String>> mlist;
    private List<String> list;
    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mlist = new MutableLiveData<>();

        mText.setValue("This is Home fragment");
        list = new ArrayList<>();
        mlist.setValue(list);
    }

    public LiveData<String> getText() {
        return mText;
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





}