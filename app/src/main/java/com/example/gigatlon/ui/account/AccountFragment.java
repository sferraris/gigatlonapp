package com.example.gigatlon.ui.account;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.DatePickerDialog;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gigatlon.MyApplication;
import com.example.gigatlon.MyPreferences;
import com.example.gigatlon.R;

import com.example.gigatlon.databinding.FragmentAccountBinding;
import com.example.gigatlon.databinding.PopupEditBinding;
import com.example.gigatlon.domain.User;
import com.example.gigatlon.domain.Weighting;
import com.example.gigatlon.repository.UserRepository;
import com.example.gigatlon.ui.MainActivity;
import com.example.gigatlon.viewmodel.RepositoryViewModelFactory;
import com.example.gigatlon.vo.Status;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AccountFragment extends Fragment {

    private MyApplication application;
    private MainActivity activity;
    private AccountViewModel accountViewModel;
    private AlertDialog dialog;
    private FragmentAccountBinding binding;
    private PopupEditBinding popup_binding;
    public static AccountFragment create() {
        return new AccountFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        application = (MyApplication)getActivity().getApplication();
        activity = (MainActivity)getActivity();

        RepositoryViewModelFactory viewModelFactory = new RepositoryViewModelFactory(UserRepository.class, application.getUserRepository());
        accountViewModel = new ViewModelProvider(this, viewModelFactory).get(AccountViewModel.class);

        binding.login.setOnClickListener(v -> accountViewModel
                .login("johndoe", "1234567890")
                .observe(getViewLifecycleOwner(), resource -> {
                    switch (resource.status) {
                        case LOADING:
                            binding.login.setEnabled(false);
                            //activity.showProgressBar();
                            break;
                        case SUCCESS:
                            binding.login.setEnabled(true);
                            application.getPreferences().setAuthToken(resource.data);
                            //Toast.makeText(application, getString(R.string.operation_success), Toast.LENGTH_SHORT).show();
                            //callback.onLoggedIn();
                            break;
                        case ERROR:
                            binding.login.setEnabled(true);
                            //activity.hideProgressBar();
                            Toast.makeText(application, resource.message, Toast.LENGTH_SHORT).show();
                            break;
                    }
                }));

        binding.get.setOnClickListener(v ->
        {accountViewModel
                .getCurrentUser()
                .observe(getViewLifecycleOwner(), resource -> {
                    switch (resource.status) {
                        case LOADING:
                            binding.login.setEnabled(false);
                            //activity.showProgressBar();
                            break;
                        case SUCCESS:
                            binding.login.setEnabled(true);
                            binding.textAccount.setText(resource.data.getFullName());
                            Date birthdate = resource.data.getBirthdate();
                            java.text.SimpleDateFormat form = new java.text.SimpleDateFormat("dd/MM/yyyy");
                            String s = form.format(birthdate);
                            binding.textDate.setText(String.format("%s %s", getString(R.string.birthdate), s));
                            binding.textMail.setText(String.format("%s %s",getString(R.string.mail), resource.data.getEmail()));
                            int index = getPos(resource.data.getGender());

                            binding.textSex.setText(String.format("%s %s",getString(R.string.gender), getResources().getStringArray(R.array.gender)[index]));


                            //Toast.makeText(application, getString(R.string.operation_success), Toast.LENGTH_SHORT).show();
                            //callback.onLoggedIn();
                            break;
                        case ERROR:
                            binding.login.setEnabled(true);
                            //activity.hideProgressBar();
                            Toast.makeText(application, resource.message, Toast.LENGTH_SHORT).show();
                            break;
                    }
                });
    accountViewModel.getCurrentWeighting().observe(getViewLifecycleOwner(), resource -> {
        switch (resource.status) {
            case LOADING:
                binding.login.setEnabled(false);
                //activity.showProgressBar();
                break;
            case SUCCESS:
                binding.login.setEnabled(true);
                binding.textWeight.setText(String.format("%s %s", getString(R.string.weight), resource.data.get(0).getWeight().toString()));
                binding.textHeight.setText(String.format("%s %s", getString(R.string.height), resource.data.get(0).getHeight().toString()));
                //Toast.makeText(application, getString(R.string.operation_success), Toast.LENGTH_SHORT).show();
                //callback.onLoggedIn();
                break;
            case ERROR:
                binding.login.setEnabled(true);
                //activity.hideProgressBar();
                Toast.makeText(application, resource.message, Toast.LENGTH_SHORT).show();
                break;
        }
    });
        });

        binding.editProfile.setOnClickListener(

                v ->{
                    accountViewModel.getCurrentUser().observe(getViewLifecycleOwner(), resource -> {
                    switch (resource.status) {
                        case LOADING:
                            binding.login.setEnabled(false);
                            //activity.showProgressBar();
                            break;
                        case SUCCESS:
                            DatePickerDialog.OnDateSetListener onDateSetListener;
                            AlertDialog.Builder builder;
                            builder = new AlertDialog.Builder(getContext());
                            popup_binding = PopupEditBinding.inflate(getLayoutInflater());
                            binding.login.setEnabled(true);
                            popup_binding.Name.setText(resource.data.getFullName());
                            java.text.SimpleDateFormat form = new java.text.SimpleDateFormat("dd/MM/yyyy");
                            Date birthdate = resource.data.getBirthdate();
                            String s = form.format(birthdate);
                            popup_binding.date.setText(s);
                            onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                    Log.d("UI", "OnDateSet" + i + "/" + (i1 + 1) + "/" + i2);
                                    String d = i2 + "/" + (i1 + 1) + "/" + i;
                                    popup_binding.date.setText(d);

                                }
                            };
                            popup_binding.date.setOnClickListener(g -> {
                                Calendar cal = Calendar.getInstance();
                                cal.setTime(birthdate);
                                int year = cal.get(Calendar.YEAR);
                                int month = cal.get(Calendar.MONTH);
                                int day = cal.get(Calendar.DATE);
                                DatePickerDialog dialog = new DatePickerDialog(popup_binding.getRoot().getContext(), R.style.My_Date_Picker, onDateSetListener, year, month, day);

                                dialog.show();
                            });


                            ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                                    .createFromResource(getContext(), R.array.gender,
                                            R.layout.spinner_item);


                            staticAdapter
                                    .setDropDownViewResource(R.layout.spinner_item);

                            popup_binding.spinnerGender.setAdapter(staticAdapter);
                            popup_binding.spinnerGender.setSelection(getPos(resource.data.getGender()));
                            popup_binding.spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view,
                                                           int position, long id) {
                                    Log.v("item", (String) parent.getItemAtPosition(position));
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                    // TODO Auto-generated method stub
                                }
                            });

                            builder.setView(popup_binding.getRoot());
                            dialog = builder.create();
                            dialog.show();

                            accountViewModel.getCurrentWeighting().observe(getViewLifecycleOwner(), r ->{
                                switch (r.status) {
                                    case LOADING:
                                        popup_binding.submitButton.setEnabled(false);
                                        // Activity.showProgressBar();
                                        break;
                                    case SUCCESS:
                                          popup_binding.submitButton.setEnabled(true);
                                        popup_binding.height.setText(r.data.get(0).getHeight().toString());
                                        popup_binding.weight.setText(r.data.get(0).getWeight().toString());
                                        //Toast.makeText(application, getString(R.string.operation_success), Toast.LENGTH_SHORT).show();
                                        //callback.onLoggedIn();
                                        break;
                                    case ERROR:
                                        binding.login.setEnabled(true);
                                        //activity.hideProgressBar();
                                        Toast.makeText(application, r.message, Toast.LENGTH_SHORT).show();
                                        break;
                                }

                            });
                            popup_binding.submitButton.setOnClickListener(g -> {
                                String gender = popup_binding.spinnerGender.getSelectedItem().toString();
                                String eng_gender = translateToEnglish(gender);
                                String sDate1 = popup_binding.date.getText().toString();
                                Date real_date = new Date();
                                try {
                                    real_date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
                                } catch (ParseException e) {
                                    Toast.makeText(application, sDate1, Toast.LENGTH_SHORT).show();
                                }
                                Log.d("UI_frag", sDate1 + "           " + real_date);
                                Toast.makeText(application, real_date.toString(), Toast.LENGTH_SHORT).show();
                                User new_user = new User(resource.data.getUsername(), popup_binding.Name.getText().toString(), eng_gender, real_date, resource.data.getEmail(), resource.data.getAvatarUrl());
                                accountViewModel.updateCurrentUser(new_user).observe(getViewLifecycleOwner(), r -> {
                                    switch (r.status) {
                                        case LOADING:
                                            //popup_binding.submitButton.setEnabled(false);
                                            // Activity.showProgressBar();
                                            break;
                                        case SUCCESS:
                                            //  popup_binding.submitButton.setEnabled(true);
                                            binding.textAccount.setText(r.data.getFullName());
                                            Date d2 = resource.data.getBirthdate();
                                            String s2 = form.format(d2);
                                            binding.textDate.setText(s2);
                                            binding.textMail.setText(r.data.getEmail());
                                            binding.textSex.setText(r.data.getGender());
                                            dialog.dismiss();
                                            //Toast.makeText(application, getString(R.string.operation_success), Toast.LENGTH_SHORT).show();
                                            //callback.onLoggedIn();
                                            break;
                                        case ERROR:
                                            binding.login.setEnabled(true);
                                            //activity.hideProgressBar();
                                            Toast.makeText(application, r.message, Toast.LENGTH_SHORT).show();
                                            break;
                                    }
                                });

                                Weighting w = new Weighting(Double.valueOf(popup_binding.weight.getText().toString()), Double.valueOf(popup_binding.height.getText().toString()));
                                accountViewModel.updateWeighting(w).observe(getViewLifecycleOwner(), r -> {
                                    switch (r.status) {
                                        case LOADING:
                                           // popup_binding.submitButton.setEnabled(false);
                                            // Activity.showProgressBar();
                                            break;
                                        case SUCCESS:
                                            //  popup_binding.submitButton.setEnabled(true);
                                            binding.textWeight.setText(String.format("%s %s", getString(R.string.weight), r.data.getWeight()));
                                            binding.textHeight.setText(String.format("%s %s", getString(R.string.height), r.data.getHeight()));
                                            //callback.onLoggedIn();
                                            break;
                                        case ERROR:
                                            binding.login.setEnabled(true);
                                            //activity.hideProgressBar();
                                            Toast.makeText(application, r.message, Toast.LENGTH_SHORT).show();
                                            break;
                                    }

                                });
                                dialog.dismiss();
                            });


                            //Toast.makeText(application, getString(R.string.operation_success), Toast.LENGTH_SHORT).show();
                            //callback.onLoggedIn();
                            break;
                        case ERROR:
                            binding.login.setEnabled(true);
                            dialog.dismiss();
                            //activity.hideProgressBar();
                            Toast.makeText(application, resource.message, Toast.LENGTH_SHORT).show();
                            break;
                    }




                });

        }

        );



    }

    private int getPos(String gender){
        Resources res = getResources();
        Configuration config = res.getConfiguration();
        Locale savedLocale = config.locale;
        config.locale = Locale.ENGLISH; // whatever you want here
        res.updateConfiguration(config, null); // second arg null means don't change

// retrieve resources from desired locale

        List<String> genders = Arrays.asList(getResources().getStringArray(R.array.gender));
        int index = genders.indexOf(gender);
// restore original locale
        config.locale = savedLocale;
        res.updateConfiguration(config, null);

        return  index;

    }

    private String translateToEnglish(String gender){
        List<String> genders = Arrays.asList(getResources().getStringArray(R.array.gender));
        int index = genders.indexOf(gender);

        Resources res = getResources();
        Configuration config = res.getConfiguration();
        Locale savedLocale = config.locale;
        config.locale = Locale.ENGLISH; // whatever you want here
        res.updateConfiguration(config, null); // second arg null means don't change

// retrieve resources from desired locale

      String eng_gender = getResources().getStringArray(R.array.gender)[index];

// restore original locale
        config.locale = savedLocale;
        res.updateConfiguration(config, null);

        return  eng_gender;

    }
}