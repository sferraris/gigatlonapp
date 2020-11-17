package com.example.gigatlon.ui.account;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.gigatlon.repository.UserRepository;

public class AccountViewModelFactory implements ViewModelProvider.Factory {
    private Application application;

    public AccountViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AccountViewModel(application, new UserRepository(application));
    }
}
