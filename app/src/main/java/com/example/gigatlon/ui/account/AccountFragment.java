package com.example.gigatlon.ui.account;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
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
    String username;



    public static AccountFragment create() {
        return new AccountFragment();
    }

    int images[] = {
            R.mipmap.avatar_pic_1,   R.mipmap.avatar_pic_2, R.mipmap.avatar_pic_3,   R.mipmap.avatar_pic_4,
            R.mipmap.avatar_pic_5,   R.mipmap.avatar_pic_6, R.mipmap.avatar_pic_7,   R.mipmap.avatar_pic_8,
            R.mipmap.avatar_pic_9,   R.mipmap.avatar_pic_10, R.mipmap.avatar_pic_11,   R.mipmap.avatar_pic_12,

    };
    ViewPagerAdapter mViewPager;

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

        accountViewModel
                .getCurrentUser()
                .observe(getViewLifecycleOwner(), resource -> {
                    switch (resource.status) {
                        case LOADING:
                            break;
                        case SUCCESS:
                            if(resource.data.getFullName() != null) {
                                binding.textAccount.setText(resource.data.getFullName());
                            }
                            if(!resource.data.getBirthdate().equals(new Date(0))) {
                                Date birthdate = resource.data.getBirthdate();
                                java.text.SimpleDateFormat form = new java.text.SimpleDateFormat("dd/MM/yyyy");
                                String s = form.format(birthdate);
                                binding.textDate.setText(String.format("%s %s", getString(R.string.birthdate), s));
                            }else{
                                binding.textDate.setText(String.format("%s %s", getString(R.string.birthdate), "---"));
                            }
                            binding.textMail.setText(String.format("%s %s",getString(R.string.mail), resource.data.getEmail()));
                           username = resource.data.getUsername();
                            int index = getPos(resource.data.getGender());
                            binding.textSex.setText(String.format("%s %s",getString(R.string.gender), getResources().getStringArray(R.array.gender)[index]));

                           binding.avatarAccount.setImageResource(Integer.parseInt(resource.data.getAvatarUrl()));

                            break;
                        case ERROR:
                            Toast.makeText(application, getResources().getString(R.string.Error), Toast.LENGTH_SHORT).show();
                            break;
                    }
                });
    accountViewModel.getCurrentWeighting().observe(getViewLifecycleOwner(), resource -> {
        switch (resource.status) {
            case LOADING:

                break;
            case SUCCESS:

                if(!resource.data.isEmpty()) {
                    binding.textWeight.setText(String.format("%s %s", getString(R.string.weight), resource.data.get(0).getWeight().toString()));
                    binding.textHeight.setText(String.format("%s %s", getString(R.string.height), resource.data.get(0).getHeight().toString()));
                }else{
                    binding.textWeight.setText(String.format("%s %s", getString(R.string.weight), "---"));
                    binding.textHeight.setText(String.format("%s %s", getString(R.string.height), "---"));
                }
                break;
            case ERROR:

                Toast.makeText(application, getResources().getString(R.string.Error), Toast.LENGTH_SHORT).show();
                break;
        }
    });




        binding.editProfile.setOnClickListener(

                v ->{
                    AlertDialog.Builder builder;
                    builder = new AlertDialog.Builder(getContext());
                    popup_binding = PopupEditBinding.inflate(getLayoutInflater());

                            DatePickerDialog.OnDateSetListener onDateSetListener;

                            popup_binding.Name.setText(binding.textAccount.getText().toString());

                            popup_binding.date.setText(binding.textDate.getText().toString().split(": ")[1]);
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
                                Date d = new Date(0);
                                try {
                                     d = new SimpleDateFormat("dd/MM/yyyy").parse(binding.textDate.getText().toString().split(": ")[1]);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                cal.setTime(d);
                                int year = cal.get(Calendar.YEAR);
                                int month = cal.get(Calendar.MONTH);
                                int day = cal.get(Calendar.DATE);
                                DatePickerDialog cal_popup = new DatePickerDialog(popup_binding.getRoot().getContext(), R.style.My_Date_Picker, onDateSetListener, year, month, day);

                                cal_popup.show();
                            });

                            ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                                    .createFromResource(getContext(), R.array.gender,
                                            R.layout.spinner_item);


                            staticAdapter
                                    .setDropDownViewResource(R.layout.spinner_item);

                            String eng = translateToEnglish(binding.textSex.getText().toString().split(" ")[1]);


                            popup_binding.spinnerGender.setAdapter(staticAdapter);
                            popup_binding.spinnerGender.setSelection(getPos(eng));
                            popup_binding.spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view,
                                                           int position, long id) {

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                    // TODO Auto-generated method stub
                                }
                            });

                            mViewPager = new ViewPagerAdapter(getContext(), images);
                            popup_binding.viewPager.setAdapter(mViewPager);

                            popup_binding.submitButton.setEnabled(true);
                            popup_binding.height.setText(binding.textHeight.getText().toString().split(" ")[1]);
                            popup_binding.weight.setText(binding.textWeight.getText().toString().split(" ")[1]);
                            //Toast.makeText(application, getString(R.string.operation_success), Toast.LENGTH_SHORT).show();
                            //callback.onLoggedIn();

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
                                User new_user = new User(username, popup_binding.Name.getText().toString(), eng_gender, real_date, binding.textMail.getText().toString().split(" ")[1], String.valueOf(images[popup_binding.viewPager.getCurrentItem()]));
                                accountViewModel.updateCurrentUser(new_user).observe(getViewLifecycleOwner(), resource -> {
                                    switch (resource.status) {
                                        case LOADING:
                                            // binding.login.setEnabled(false);
                                            //activity.showProgressBar()
                                            break;
                                        case SUCCESS:
                                           Toast.makeText(application, getResources().getString(R.string.succes), Toast.LENGTH_SHORT).show();

                                            break;
                                        case ERROR:
                                            // binding.login.setEnabled(true);
                                            //activity.hideProgressBar();
                                            Toast.makeText(application, getResources().getString(R.string.errorN), Toast.LENGTH_SHORT).show();
                                            break;
                                    }
                                });

                                Weighting w = new Weighting(Double.valueOf(popup_binding.weight.getText().toString()), Double.valueOf(popup_binding.height.getText().toString()));
                                accountViewModel.updateWeighting(w).observe(getViewLifecycleOwner(), resource ->{
                                    switch (resource.status) {
                                        case LOADING:
                                            // binding.login.setEnabled(false);
                                            //activity.showProgressBar()
                                            break;
                                        case SUCCESS:
                                            Toast.makeText(application,  getResources().getString(R.string.succes), Toast.LENGTH_SHORT).show();

                                            break;
                                        case ERROR:
                                            // binding.login.setEnabled(true);
                                            //activity.hideProgressBar();
                                            Toast.makeText(application,  getResources().getString(R.string.errorN), Toast.LENGTH_SHORT).show();
                                            break;
                                    }
                                });
                                dialog.dismiss();


                            });


                    builder.setView(popup_binding.getRoot());

                    dialog = builder.create();

                    dialog.show();
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