package com.example.gigatlon.ui.progress;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gigatlon.domain.User;
import com.example.gigatlon.domain.Weighting;
import com.example.gigatlon.repository.UserRepository;
import com.example.gigatlon.viewmodel.RepositoryViewModel;
import com.example.gigatlon.vo.Resource;

import java.util.ArrayList;
import java.util.List;

public class ProgressViewModel extends RepositoryViewModel<UserRepository> {

    private final LiveData<Resource<User>> user;

    public ProgressViewModel(UserRepository userRepository) {
        super(userRepository);
        user = getCurrentUser();


    }
    public LiveData<Resource<User>> getCurrentUser() {
        repository.getCurrentUser();
        return user;
    }

    public LiveData<Resource<List<Weighting>>> getCurrentWeighting() {
        LiveData<Resource<List<Weighting>>> r = repository.getWeightings(0, 5000, "date", "asc");
        return r;

    }

    public LiveData<Resource<Weighting>> updateWeighting(Weighting weighting) {
        return repository.createWeighting(weighting);
    }
}