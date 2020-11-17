package com.example.gigatlon.ui.account;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gigatlon.api.model.CredentialsModel;
import com.example.gigatlon.api.model.TokenModel;
import com.example.gigatlon.api.model.UserModel;
import com.example.gigatlon.vo.Resource;
import com.example.gigatlon.repository.UserRepository;

public class AccountViewModel extends AndroidViewModel {

    private LiveData<Resource<UserModel>> user;
    private LiveData<Resource<TokenModel>> token;
    private UserRepository repo;

    public AccountViewModel(Application app, UserRepository repo) {
        super(app);
        this.repo = repo;
    }

    public LiveData<Resource<TokenModel>> login() {
        token = repo.login(new CredentialsModel("johndoe", "1234567890"));
        return token;
    }

    public LiveData<Resource<UserModel>> getCurrentUser() {
        if (user == null)
            user = repo.getCurrentUser();
        return user;
    }

}