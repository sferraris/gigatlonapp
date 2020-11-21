package com.example.gigatlon.ui.account;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.List;

import com.example.gigatlon.domain.User;
import com.example.gigatlon.domain.Weighting;
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

    public LiveData<Resource<String>> login(String username, String password) { //TODO sacarlo
        return repository.login(username, password);
    }

    public LiveData<Resource<User>> getCurrentUser() {
        return repository.getCurrentUser();
    }

    public LiveData<Resource<User>> updateCurrentUser(User user) {
        return repository.updateCurrentUser(user);
    }

    public LiveData<Resource<List<Weighting>>> getCurrentWeighting() {
        LiveData<Resource<List<Weighting>>> r = repository.getWeightings(0, 1, "date", "desc");
        //Log.d("UI", r.getValue().data.get(0).getWeight().toString());
        return r;
    }

    public LiveData<Resource<Weighting>> updateWeighting(Weighting weighting) {
        return repository.createWeighting(weighting);
    }
}