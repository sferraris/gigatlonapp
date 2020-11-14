package com.example.gigatlon.ui.routines;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class RoutinesFragment extends Fragment {

    private RoutinesViewModel routinesViewModel;
    RoutineAdapter adapter;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        routinesViewModel = new ViewModelProvider(this).get(RoutinesViewModel.class);
        root = inflater.inflate(R.layout.fragment_routines, container, false);

        final TextView textView = root.findViewById(R.id.text_routines);
        adapter = new RoutineAdapter(routinesViewModel.getList());
        routinesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        routinesViewModel.getListData().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                adapter = new RoutineAdapter(strings);
                RecyclerView recyclerView = root.findViewById(R.id.animalList);
                recyclerView.setHasFixedSize(true);

                recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

                recyclerView.setAdapter(adapter);
            }
        });
        if(routinesViewModel.getList().isEmpty()) {
            routinesViewModel.addElement("Horse");
            routinesViewModel.addElement("Cat");
            routinesViewModel.addElement("Shark");
            routinesViewModel.addElement("Dog");
        }




        return root;
    }

}