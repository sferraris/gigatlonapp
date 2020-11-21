package com.example.gigatlon.ui.home;

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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gigatlon.MyApplication;
import com.example.gigatlon.R;
import com.example.gigatlon.databinding.FragmentFavoritesBinding;
import com.example.gigatlon.databinding.FragmentHomeBinding;
import com.example.gigatlon.domain.Routine;
import com.example.gigatlon.repository.RoutineRepository;
import com.example.gigatlon.ui.MainActivity;
import com.example.gigatlon.ui.RoutineAdapter;
import com.example.gigatlon.ui.favorites.FavoritesViewModel;
import com.example.gigatlon.viewmodel.RepositoryViewModelFactory;
import com.example.gigatlon.vo.Resource;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    RoutineAdapter adapter;
    FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());

        return binding.getRoot();



    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MyApplication application = (MyApplication) getActivity().getApplication();
        MainActivity activity = (MainActivity) getActivity();
        ViewModelProvider.Factory viewModelFactory = new RepositoryViewModelFactory<>(RoutineRepository.class, application.getRoutineRepository());
        homeViewModel = new ViewModelProvider(this, viewModelFactory).get(HomeViewModel.class);



        List<Routine> routines = new ArrayList<>();
        adapter = new RoutineAdapter(routines, application.getRoutineRepository());
        homeViewModel.getRoutines().observe(getViewLifecycleOwner(), listResource -> {
            switch (listResource.status) {
                case LOADING:
                  //  Toast.makeText(activity, "LOADING", Toast.LENGTH_SHORT).show();
                    break;
                case SUCCESS:
                    // activity.hideProgressBar();
                    routines.clear();
                    routines.addAll(listResource.data);
                    adapter.notifyDataSetChanged();
                    binding.homeList.routineList.scrollToPosition(routines.size() - 1);
                    binding.homeList.routineList.setHasFixedSize(true);
                    binding.homeList.routineList.setLayoutManager(new LinearLayoutManager(activity));
                    binding.homeList.routineList.setAdapter(adapter);
                    break;
                case ERROR:
                    Toast.makeText(activity, "ERROR", Toast.LENGTH_SHORT).show();
                    break;
            }
        });


        binding.homeList.routineList.setHasFixedSize(true);
        binding.homeList.routineList.setLayoutManager(new LinearLayoutManager(activity));
        binding.homeList.routineList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(!binding.homeList.routineList.canScrollVertically(1)){
                    homeViewModel.getMoreRoutines();
                }
            }
        });
        binding.homeList.routineList.setAdapter(adapter);



        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getContext(), R.array.brew_array,
                        R.layout.spinner_item);


        staticAdapter
                .setDropDownViewResource(R.layout.spinner_item);
        binding.homeList.orderBySpinner.setAdapter(staticAdapter);

        binding.homeList.orderBySpinner.setAdapter(staticAdapter);
        binding.homeList.orderBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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






    }

}