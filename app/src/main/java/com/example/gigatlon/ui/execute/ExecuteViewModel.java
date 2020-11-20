package com.example.gigatlon.ui.execute;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gigatlon.domain.Cycle;

import java.util.ArrayList;
import java.util.List;

public class ExecuteViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private List<Cycle> list;
    private MutableLiveData<List<Cycle>> mlist;

    public ExecuteViewModel() {

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