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

import java.util.ArrayList;

public class RoutinesFragment extends Fragment {

    private RoutinesViewModel routinesViewModel;
    RoutineAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        routinesViewModel = new ViewModelProvider(this).get(RoutinesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_routines, container, false);
        final TextView textView = root.findViewById(R.id.text_routines);
        routinesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        ArrayList<String> l = new ArrayList<>();
        l.add("Horse");
        l.add("Cow");
        l.add("Dog");
        l.add("Cat");
        l.add("Shark");
        adapter = new RoutineAdapter(l);

       RecyclerView recyclerView = root.findViewById(R.id.animalList);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        recyclerView.setAdapter(adapter);

        root.findViewById(R.id.floatingBtn).setOnClickListener(v -> {
            l.add("sheep");
            adapter.notifyDataSetChanged();
        });
        return root;
    }
}