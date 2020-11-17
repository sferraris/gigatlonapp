package com.example.gigatlon.ui.account;

import android.app.AlertDialog;
import android.app.Application;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gigatlon.MyPreferences;
import com.example.gigatlon.R;
import com.example.gigatlon.vo.Status;

import java.util.Calendar;

public class AccountFragment extends Fragment {

    private AccountViewModel accountViewModel;
    private AlertDialog dialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        accountViewModel = new ViewModelProvider(this, new AccountViewModelFactory(getActivity().getApplication())).get(AccountViewModel.class);
        View root = inflater.inflate(R.layout.fragment_account, container, false);
        final TextView textView = root.findViewById(R.id.text_account);
        Application app = getActivity().getApplication();
        root.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accountViewModel.login().observe(getViewLifecycleOwner(), r ->
                {
                    if (r.getStatus() == Status.SUCCESS) {
                        Log.d("Bien ahi", "Bien ahi");
                        MyPreferences preferences = new MyPreferences(app);
                        preferences.setAuthToken(r.getData().getToken());
                    } else
                        Log.d("todo mal", "todo mal");
                });
            }
        });
        root.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accountViewModel.getCurrentUser().observe(getViewLifecycleOwner(), r -> {
                    if (r.getStatus() == Status.SUCCESS)
                        textView.setText(r.getData().getUsername());
                });
            }
        });
        root.findViewById(R.id.edit_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener onDateSetListener;
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(getContext());
                View popUp = getLayoutInflater().inflate(R.layout.popup_edit, null);
                EditText t = popUp.findViewById(R.id.editText2);
                t.setText("Micus");
                TextView date = popUp.findViewById(R.id.editText3);
                onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Log.d("UI", "OnDateSet" + i +"/" + (i1 + 1) + "/" +i2 );
                        String d = i2 + "/" + (i1 + 1) + "/" + i;
                        date.setText(d);

                    }
                };

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

        return root;
    }
}