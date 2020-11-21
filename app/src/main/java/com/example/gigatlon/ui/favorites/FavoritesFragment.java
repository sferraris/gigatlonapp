package com.example.gigatlon.ui.favorites;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gigatlon.MyApplication;
import com.example.gigatlon.R;
import com.example.gigatlon.databinding.FragmentFavoritesBinding;
import com.example.gigatlon.domain.Routine;
import com.example.gigatlon.repository.RoutineRepository;
import com.example.gigatlon.ui.MainActivity;
import com.example.gigatlon.ui.RoutineAdapter;
import com.example.gigatlon.ui.routines.RoutinesViewModel;
import com.example.gigatlon.viewmodel.RepositoryViewModelFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FavoritesFragment extends Fragment {

    private FavoritesViewModel favoritesViewModel;
    RoutineAdapter adapter;
    FragmentFavoritesBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentFavoritesBinding.inflate(getLayoutInflater());

        return binding.getRoot();




    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MyApplication application = (MyApplication) getActivity().getApplication();
        MainActivity activity = (MainActivity) getActivity();
        ViewModelProvider.Factory viewModelFactory = new RepositoryViewModelFactory<>(RoutineRepository.class, application.getRoutineRepository());
        favoritesViewModel = new ViewModelProvider(this, viewModelFactory).get(FavoritesViewModel.class);

        List<Routine> routines = new ArrayList<>();
        adapter = new RoutineAdapter(routines, application.getRoutineRepository());
        favoritesViewModel.getRoutines().observe(getViewLifecycleOwner(), listResource -> {
            switch (listResource.status) {
                case LOADING:
                    // activity.showProgressBar();
                    break;
                case SUCCESS:
                    // activity.hideProgressBar();
                    routines.clear();
                    routines.addAll(listResource.data);
                    adapter.notifyDataSetChanged();
                    binding.favList.routineList.scrollToPosition(routines.size() - 1);
                    break;
            }
        });


        binding.favList.routineList.setHasFixedSize(true);
        binding.favList.routineList.setLayoutManager(new LinearLayoutManager(activity));
        binding.favList.routineList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(!binding.favList.routineList.canScrollVertically(1)){
                    favoritesViewModel.getMoreRoutines();
                }
            }
        });
        binding.favList.routineList.setAdapter(adapter);



        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getContext(), R.array.brew_array,
                        R.layout.spinner_item);


        staticAdapter
                .setDropDownViewResource(R.layout.spinner_item);
        binding.favList.orderBySpinner.setAdapter(staticAdapter);

        binding.favList.orderBySpinner.setAdapter(staticAdapter);
        binding.favList.orderBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                String s = (String) parent.getItemAtPosition(position);
                String eng_gender = translateToEnglish(s);
                String order[] = eng_gender.split(" ");
                Toast.makeText(activity, order[0] + " "+ order[1], Toast.LENGTH_SHORT).show();
                favoritesViewModel.filterRoutines(order[0], order[1]).observe(getViewLifecycleOwner(), listResource -> {
                    switch (listResource.status) {
                        case LOADING:
                            // activity.showProgressBar();
                            break;
                        case SUCCESS:
                            // activity.hideProgressBar();
                            routines.clear();
                            routines.addAll(listResource.data);
                            adapter.notifyDataSetChanged();
                            binding.favList.routineList.scrollToPosition(routines.size() - 1);
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