package com.example.gigatlon.ui.account;

import android.app.AlertDialog;
import android.app.Application;
import android.app.DatePickerDialog;
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
import com.example.gigatlon.repository.UserRepository;
import com.example.gigatlon.ui.MainActivity;
import com.example.gigatlon.viewmodel.RepositoryViewModelFactory;
import com.example.gigatlon.vo.Status;

import java.util.Calendar;
import java.util.Date;

public class AccountFragment extends Fragment {

    private MyApplication application;
    private MainActivity activity;
    private AccountViewModel accountViewModel;
    private AlertDialog dialog;
    private FragmentAccountBinding binding;

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

        binding.get.setOnClickListener(v -> accountViewModel
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
                            String s = birthdate.getDate() + "/" + birthdate.getMonth() + "/" + birthdate.getYear();
                            binding.textDate.setText(s);
                            binding.textMail.setText(resource.data.getEmail());
                            binding.textSex.setText(resource.data.getGender());


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
        /*
        root.findViewById(R.id.edit_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener onDateSetListener;
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(getContext());
                View popUp = getLayoutInflater().inflate(R.layout.popup_edit, null);
                EditText t = popUp.findViewById(R.id.Name);
                t.setText("Micus");
                TextView date = popUp.findViewById(R.id.date);
                onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Log.d("UI", "OnDateSet" + i +"/" + (i1 + 1) + "/" +i2 );
                        String d = i2 + "/" + (i1 + 1) + "/" + i;
                        date.setText(d);

                    }
                };

                Spinner spinner = popUp.findViewById(R.id.spinnerGender);
                ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                        .createFromResource(getContext(), R.array.gender,
                                R.layout.spinner_item);


                staticAdapter
                        .setDropDownViewResource(R.layout.spinner_item);
                spinner.setAdapter(staticAdapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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


                date.setOnClickListener(v -> {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DATE);
                    DatePickerDialog dialog = new DatePickerDialog(popUp.getContext(), R.style.My_Date_Picker, onDateSetListener, year, month, day );



                    dialog.show();
                });

                builder.setView(popUp);

                dialog = builder.create();

                dialog.show();

            }
        });
*/
        //return root;
    }
}