package com.example.gigatlon.ui.account;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gigatlon.api.model.Credentials;
import com.example.gigatlon.api.model.Token;
import com.example.gigatlon.api.model.User;
import com.example.gigatlon.repository.Resource;
import com.example.gigatlon.repository.UserRepository;

public class AccountViewModel extends AndroidViewModel {

    private LiveData<Resource<User>> user;
    private LiveData<Resource<Token>> token;
    private UserRepository repo;

    public AccountViewModel(Application app, UserRepository repo) {
        super(app);
        this.repo = repo;
    }

    public LiveData<Resource<Token>> login() {
        token = repo.login(new Credentials("johndoe", "1234567890"));
        return token;
    }

    public LiveData<Resource<User>> getCurrentUser() {
        if (user == null)
            user = repo.getCurrentUser();
        return user;
    }

}