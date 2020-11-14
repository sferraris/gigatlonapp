package com.example.gigatlon.ui.account;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.gigatlon.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Calendar;
import java.util.Date;

public class AccountFragment extends Fragment {

    private AccountViewModel accountViewModel;
    private AlertDialog dialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        View root = inflater.inflate(R.layout.fragment_account, container, false);
        final TextView textView = root.findViewById(R.id.text_account);
        accountViewModel.getName().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
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