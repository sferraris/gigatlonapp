package com.example.gigatlon.ui.await_confirm;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gigatlon.MyApplication;
import com.example.gigatlon.R;
import com.example.gigatlon.databinding.AwaitConfirmBinding;
import com.example.gigatlon.domain.User;
import com.example.gigatlon.repository.UserRepository;
import com.example.gigatlon.ui.MainActivity;
import com.example.gigatlon.ui.verifyViewModel;

import java.util.Date;

public class FragmentAwaitConfirm extends Fragment {

    private MyApplication application;
    private MainActivity activity;
    private UserRepository userRepository;
    private String email;
    AwaitConfirmBinding binding;
    verifyViewModel model;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = AwaitConfirmBinding.inflate(getLayoutInflater());
        return binding.getRoot();

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        application = (MyApplication)getActivity().getApplication();
        activity = (MainActivity)getActivity();
        userRepository = application.getUserRepository();
        model = new ViewModelProvider(activity).get(verifyViewModel.class);

        model.getSelected().observe(getViewLifecycleOwner(), r->{
            email = r;

        });


        binding.resend.setOnClickListener(v -> {
                application.getUserRepository().resendEmail(email).observe(getViewLifecycleOwner(), r->{
                    switch (r.status){
                        case LOADING:
                            binding.resend.setEnabled(false);
                            break;
                        case SUCCESS:
                            binding.resend.setEnabled(true);
                            Toast.makeText(application,  getResources().getString(R.string.code_sent), Toast.LENGTH_SHORT).show();
                            break;
                        case ERROR:
                            binding.resend.setEnabled(true);
                            Toast.makeText(application,  getResources().getString(R.string.code_not_set), Toast.LENGTH_SHORT).show();
                            break;
                    }
                });


                });

        binding.toLogInt.setOnClickListener(v -> activity.toLogIn());
    }

}
