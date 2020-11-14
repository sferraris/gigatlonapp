package com.example.gigatlon.ui.progress;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProgressViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private List<Double> list;
    private MutableLiveData<List<Double>> mlist;

    public ProgressViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is progress fragment");
        list = new ArrayList<>();
        mlist = new MutableLiveData<>();
        mlist.setValue(list);

    }

    public void addElement(Double el){
        list.add(el);
    }

    public LiveData<List<Double>> getListData(){
        return mlist;
    }

    public LiveData<String> getText() {
        return mText;
    }
}