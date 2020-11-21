package com.example.gigatlon.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ConfrimViewModel extends ViewModel {
    private final MutableLiveData<String> email = new MutableLiveData<>();
    private final MutableLiveData<String> code = new MutableLiveData<>();
    private  Boolean good = false;


    public void setValues(String e, String c) {
        email.setValue(e);
        code.setValue(c);
    }
    public void voidSetGood(Boolean g){
        good = g;
    }

    public LiveData<String> getEmail() {
        return email;
    }
    public LiveData<String> getCode(){
        return code;
    }
    public Boolean getGood(){
        return good;
    }


}
