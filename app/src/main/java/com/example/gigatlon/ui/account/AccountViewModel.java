package com.example.gigatlon.ui.account;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AccountViewModel extends ViewModel {

    private MutableLiveData<String> name;

    public AccountViewModel() {
        name = new MutableLiveData<>();
        name.setValue("Holis");
    }

    public LiveData<String> getName() {
        return name;
    }
}