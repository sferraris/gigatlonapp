package com.example.gigatlon.ui.favorites;

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

public class FavoritesFragment extends Fragment {

    private FavoritesViewModel favoritesViewModel;
    RoutineAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoritesViewModel =
                new ViewModelProvider(this).get(FavoritesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favorites, container, false);
        final TextView textView = root.findViewById(R.id.text_favorites);
        favoritesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        adapter = new RoutineAdapter(favoritesViewModel.getList());
        favoritesViewModel.getListData().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                adapter = new RoutineAdapter(strings);
                RecyclerView recyclerView = root.findViewById(R.id.animalList);
                recyclerView.setHasFixedSize(true);

                recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

                recyclerView.setAdapter(adapter);
            }
        });
        if(favoritesViewModel.getList().isEmpty()) {
            favoritesViewModel.addElement("Horse");
            favoritesViewModel.addElement("Cat");
            favoritesViewModel.addElement("Shark");
            favoritesViewModel.addElement("Dog");
        }
        return root;
    }
}