package com.example.gigatlon.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gigatlon.R;
import com.example.gigatlon.ui.RoutineAdapter;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    RoutineAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        adapter = new RoutineAdapter(homeViewModel.getList());
        homeViewModel.getListData().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                adapter = new RoutineAdapter(strings);
                RecyclerView recyclerView = root.findViewById(R.id.animalList);
                recyclerView.setHasFixedSize(true);

                recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

                recyclerView.setAdapter(adapter);
            }
        });
        if(homeViewModel.getList().isEmpty()) {
            homeViewModel.addElement("Horse");
            homeViewModel.addElement("Cat");
            homeViewModel.addElement("Shark");
            homeViewModel.addElement("Dog");
        }

        Spinner spinner = root.findViewById(R.id.planets_spinner);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getContext(), R.array.brew_array,
                        android.R.layout.simple_spinner_item);

        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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

        return root;
    }
}