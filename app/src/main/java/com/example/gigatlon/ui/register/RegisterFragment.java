package com.example.gigatlon.ui.register;

import android.os.Bundle;
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
import com.example.gigatlon.databinding.FragmentLoginBinding;
import com.example.gigatlon.databinding.RegisterAccountBinding;
import com.example.gigatlon.domain.User;
import com.example.gigatlon.repository.UserRepository;
import com.example.gigatlon.ui.MainActivity;
import com.example.gigatlon.ui.verifyViewModel;

import java.util.Date;

public class RegisterFragment extends Fragment {
    private MyApplication application;
    private MainActivity activity;
    private UserRepository userRepository;
    RegisterAccountBinding binding;
    private verifyViewModel model;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = RegisterAccountBinding.inflate(getLayoutInflater());
        return binding.getRoot();

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        application = (MyApplication)getActivity().getApplication();
        activity = (MainActivity)getActivity();
        userRepository = application.getUserRepository();
        model = new ViewModelProvider(activity).get(verifyViewModel.class);

        binding.confirmR.setOnClickListener(v ->{

                User user = new User(binding.usernameR.getText().toString(), binding.passwordR.getText().toString(), binding.nameR.getText().toString(), "other", new Date(), binding.emailR.getText().toString());

                userRepository.createUser(user).observe(getViewLifecycleOwner(), resource -> {
                    switch (resource.status){
                        case LOADING:
                            binding.confirmR.setEnabled(false);
                            break;
                        case SUCCESS:
                            binding.confirmR.setEnabled(true);
                            //To comfirm account
                            model.select(binding.emailR.getText().toString());
                            binding.usernameR.setText("");
                            binding.passwordR.setText("");
                            binding.nameR.setText("");
                            binding.emailR.setText("");


                            activity.toAwaitConfirm();

                            break;
                        case ERROR:
                            binding.confirmR.setEnabled(true);
                            Toast.makeText(getContext(), getResources().getString(R.string.errorReg), Toast.LENGTH_SHORT);
                            //error
                            break;
                    }
                });
        });




        binding.signInR.setOnClickListener(v -> activity.toLogIn());
    }


}
