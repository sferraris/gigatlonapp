package com.example.gigatlon.ui.confirm_email;

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
import com.example.gigatlon.databinding.ConfirmEmailBinding;
import com.example.gigatlon.repository.UserRepository;
import com.example.gigatlon.ui.ConfrimViewModel;
import com.example.gigatlon.ui.MainActivity;
import com.example.gigatlon.ui.verifyViewModel;

public class FragmentConfirmEmail extends Fragment {
    private MyApplication application;
    private MainActivity activity;
    private UserRepository userRepository;
    private String email;
    private String code;
    ConfirmEmailBinding binding;
    ConfrimViewModel model;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = ConfirmEmailBinding.inflate(getLayoutInflater());
        return binding.getRoot();

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        application = (MyApplication)getActivity().getApplication();
        activity = (MainActivity)getActivity();
        userRepository = application.getUserRepository();
        model = new ViewModelProvider(activity).get(ConfrimViewModel.class);

        model.getEmail().observe(getViewLifecycleOwner(), r->{
            email = r;

        });
        model.getCode().observe(getViewLifecycleOwner(), r->{
            code = r;

        });



        binding.confirmC.setOnClickListener(v -> {

            application.getUserRepository().verifyEmail(email, code).observe(getViewLifecycleOwner(), r->{
                switch (r.status){
                    case LOADING:
                        binding.confirmC.setEnabled(false);
                        break;
                    case SUCCESS:
                        binding.confirmC.setEnabled(true);
                        Toast.makeText(application, getResources().getString(R.string.account_confirmed), Toast.LENGTH_SHORT).show();
                        model.voidSetGood(true);
                        activity.toLogIn();
                        break;
                    case ERROR:
                        binding.confirmC.setEnabled(true);
                        Toast.makeText(application, "Error sending verifiction, please try again later", Toast.LENGTH_SHORT).show();
                        break;
                }
            });


        });

    }

}
