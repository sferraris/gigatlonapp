package com.example.gigatlon.ui.logIn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gigatlon.MyApplication;
import com.example.gigatlon.R;
import com.example.gigatlon.databinding.ActivityLoginBinding;
import com.example.gigatlon.databinding.FragmentLoginBinding;
import com.example.gigatlon.repository.UserRepository;
import com.example.gigatlon.ui.MainActivity;

public class fragment_logIn  extends Fragment {
    private OnLoginListener callback;
    private MyApplication application;
    private MainActivity activity;
    private UserRepository userRepository;
    private FragmentLoginBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentLoginBinding.inflate(getLayoutInflater());
        return binding.getRoot();

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        application = (MyApplication)getActivity().getApplication();
        activity = (MainActivity)getActivity();
        userRepository = application.getUserRepository();

        binding.confirm.setOnClickListener(v -> userRepository
                .login(binding.username.getText().toString(), binding.password.getText().toString())
                .observe(getViewLifecycleOwner(), resource -> {
                    switch (resource.status) {
                        case LOADING:
                            binding.confirm.setEnabled(false);
                            //activity.showProgressBar();
                            break;
                        case SUCCESS:
                            binding.username.setText("");
                            binding.password.setText("");
                            binding.confirm.setEnabled(true);
                            application.getPreferences().setAuthToken(resource.data);
                           // Toast.makeText(application, getString(R.string.operation_success), Toast.LENGTH_SHORT).show();
                            activity.onLoggedIn();
                            break;
                        case ERROR:
                            binding.confirm.setEnabled(true);
                            //activity.hideProgressBar();
                            Toast.makeText(application, resource.message, Toast.LENGTH_SHORT).show();
                            break;
                    }
                }));
    }

    public void setOnLoginListener(OnLoginListener callback) {
        this.callback = callback;
    }

    public interface OnLoginListener {
        void onLoggedIn();
    }
}
