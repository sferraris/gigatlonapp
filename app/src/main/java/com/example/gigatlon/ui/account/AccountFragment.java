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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.gigatlon.R;

import java.util.Calendar;

public class AccountFragment extends Fragment {

    private AlertDialog dialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_account, container, false);
        final TextView textView = root.findViewById(R.id.text_account);
        Application app = getActivity().getApplication();

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

        return root;
    }
}