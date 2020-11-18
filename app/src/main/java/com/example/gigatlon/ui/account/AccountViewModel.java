package com.example.gigatlon.ui.account;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.List;

import com.example.gigatlon.domain.User;
import com.example.gigatlon.repository.UserRepository;
import com.example.gigatlon.viewmodel.RepositoryViewModel;
import com.example.gigatlon.vo.AbsentLiveData;
import com.example.gigatlon.vo.Resource;
import com.example.gigatlon.vo.Status;

public class AccountViewModel extends RepositoryViewModel<UserRepository> {
    private final LiveData<Resource<User>> user;

    public AccountViewModel(UserRepository repository) {
        super(repository);
        user = null; //TODO cambiarlo
    }

<<<<<<< Updated upstream
    public LiveData<Resource<TokenModel>> login() {
        return null;
    }

    public LiveData<Resource<UserModel>> getCurrentUser() {

        return null;
=======
    public LiveData<Resource<String>> login(String username, String password) { //TODO sacarlo
        return repository.login(username, password);
    }

    public LiveData<Resource<User>> getCurrentUser() {
        return repository.getCurrentUser();
>>>>>>> Stashed changes
    }

    public LiveData<Resource<User>> updateCurrentUser(User user) {
        return repository.updateCurrentUser(user);
    }
}