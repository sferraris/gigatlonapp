package com.example.gigatlon.ui.routines;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gigatlon.MyApplication;
import com.example.gigatlon.R;
import com.example.gigatlon.domain.Routine;
import com.example.gigatlon.repository.RoutineRepository;
import com.example.gigatlon.ui.MainActivity;
import com.example.gigatlon.ui.RoutineAdapter;
import com.example.gigatlon.viewmodel.RepositoryViewModelFactory;
import com.example.gigatlon.databinding.FragmentRoutinesBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class RoutinesFragment extends Fragment {

    private RoutinesViewModel routinesViewModel;
    RoutineAdapter adapter;
    View root;
   FragmentRoutinesBinding binding;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRoutinesBinding.inflate(getLayoutInflater());

        return binding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MyApplication application = (MyApplication) getActivity().getApplication();
        MainActivity activity = (MainActivity) getActivity();
        ViewModelProvider.Factory viewModelFactory = new RepositoryViewModelFactory<>(RoutineRepository.class, application.getRoutineRepository());
        routinesViewModel = new ViewModelProvider(this, viewModelFactory).get(RoutinesViewModel.class);

        List<Routine> routines = new ArrayList<>();
        adapter = new RoutineAdapter(routines, application.getRoutineRepository());

        routinesViewModel.getRoutines().observe(getViewLifecycleOwner(), listResource -> {
            switch (listResource.status) {
                case LOADING:
                   // activity.showProgressBar();
                    break;
                case SUCCESS:
                   // activity.hideProgressBar();
                    routines.clear();
                    routines.addAll(listResource.data);
                    adapter.notifyDataSetChanged();
                    binding.fRoutineList.routineList.scrollToPosition(routines.size() - 1);
                    break;
            }
        });


        binding.fRoutineList.routineList.setHasFixedSize(true);
        binding.fRoutineList.routineList.setLayoutManager(new LinearLayoutManager(activity));
        binding.fRoutineList.routineList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(!binding.fRoutineList.routineList.canScrollVertically(1)){
                    routinesViewModel.getMoreRoutines();
                }
            }
        });
        binding.fRoutineList.routineList.setAdapter(adapter);



        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getContext(), R.array.brew_array,
                        R.layout.spinner_item);


        staticAdapter
                .setDropDownViewResource(R.layout.spinner_item);
        binding.fRoutineList.orderBySpinner.setAdapter(staticAdapter);

        binding.fRoutineList.orderBySpinner.setAdapter(staticAdapter);
        binding.fRoutineList.orderBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                String s = (String) parent.getItemAtPosition(position);
                String eng_gender = translateToEnglish(s);
                String order[] = eng_gender.split(" ");
                Toast.makeText(activity, order[0] + " "+ order[1], Toast.LENGTH_SHORT).show();
                routinesViewModel.filterRoutines(order[0], order[1]).observe(getViewLifecycleOwner(), listResource -> {
                    switch (listResource.status) {
                        case LOADING:
                            // activity.showProgressBar();
                            break;
                        case SUCCESS:
                            // activity.hideProgressBar();
                            routines.clear();
                            routines.addAll(listResource.data);
                            adapter.notifyDataSetChanged();
                            binding.fRoutineList.routineList.scrollToPosition(routines.size() - 1);
                            break;
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });



    }




    private String translateToEnglish(String filter) {
        List<String> genders = Arrays.asList(getResources().getStringArray(R.array.brew_array));
        int index = genders.indexOf(filter);
        Log.d("UI", String.valueOf(index));

        switch (index) {
            case 0:   return "date desc";
            case 1:
                return "date asc";
            case 2:
            case 3:
                return "difficulty asc";
            case 4:  return "averageRating asc";
            case 5:
                return "averageRating desc";
        }
        return "id asc";


    }


}