package com.example.gigatlon.ui.extended_routine;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.gigatlon.MyApplication;
import com.example.gigatlon.R;
import com.example.gigatlon.databinding.ExtendedRoutineViewBinding;
import com.example.gigatlon.domain.Cycle;
import com.example.gigatlon.domain.Routine;
import com.example.gigatlon.repository.RoutineRepository;
import com.example.gigatlon.ui.MainActivity;
import com.example.gigatlon.ui.RoutineAdapter;
import com.example.gigatlon.ui.home.HomeViewModel;
import com.example.gigatlon.viewmodel.RepositoryViewModelFactory;
import com.example.gigatlon.vo.Resource;

import java.util.ArrayList;
import java.util.List;

public class Extended_routine extends Fragment {

    private ExtendedRoutineViewModel ViewModel;
    extended_routine_adapter adapter;
    ExtendedRoutineViewBinding binding;

    private AlertDialog dialog;

    public static Extended_routine newInstance() {
        return new Extended_routine();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ExtendedRoutineViewBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    /*
        mViewModel = new ViewModelProvider(this).get(ExtendedRoutineViewModel.class);

        View root = inflater.inflate(R.layout.extended_routine_view, container, false);

        adapter = new extended_routine_adapter(mViewModel.getList(), getArguments().getInt("id"));
        Log.d("UI",String.valueOf( getArguments().getInt("id")));
        mViewModel.getListData().observe(getViewLifecycleOwner(), new Observer<List<Cycle>>() {
            @Override
            public void onChanged(List<Cycle> strings) {
                adapter = new extended_routine_adapter(strings, getArguments().getInt("id"));
                RecyclerView recyclerView = root.findViewById(R.id.cycleList);


                recyclerView.setHasFixedSize(true);
                recyclerView.setNestedScrollingEnabled(false);

                recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

                recyclerView.setAdapter(adapter);
            }
        });
        if(mViewModel.getList().isEmpty()) {
            List<String> list= new ArrayList<String>();
            list.add("Exer1");
            list.add("Exer2");
            list.add("exer3");
            Cycle n = new Cycle("Cycle 1", list);
            Cycle n1 = new Cycle("Cycle 2", list);
            Cycle n2 = new Cycle("Cycle 3", list);
            Cycle n3 = new Cycle("Cycle 4", list);
            mViewModel.addElement(n);
            mViewModel.addElement(n1);
            mViewModel.addElement(n2);
            mViewModel.addElement(n3);


        }

        Bundle bundle = new Bundle();
        bundle.putInt("RoutineId", 1);
        bundle.putInt("CycleID", 0);
        bundle.putInt("ExerId", 0);
        Button execute =(Button) root.findViewById(R.id.execute_exer);
        execute.setOnClickListener(v ->{

            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(getContext());
            View popUp = getLayoutInflater().inflate(R.layout.popup_execute, null);
            builder.setView(popUp);
            dialog = builder.create();

            dialog.show();
            Button simple = popUp.findViewById(R.id.simple);
            Button detailed = popUp.findViewById(R.id.detailed);
            simple.setOnClickListener(g -> {
                dialog.dismiss();
                Navigation.findNavController(v).navigate(R.id.nav_second_execute, bundle);
            });
            detailed.setOnClickListener(g -> {
                dialog.dismiss();
                Navigation.findNavController(v).navigate(R.id.nav_execute, bundle);
            });



        });


        return root;*/

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MyApplication application = (MyApplication) getActivity().getApplication();
        MainActivity activity = (MainActivity) getActivity();
        ViewModelProvider.Factory viewModelFactory = new RepositoryViewModelFactory<>(RoutineRepository.class, application.getRoutineRepository());
         ViewModel= new ViewModelProvider(this, viewModelFactory).get(ExtendedRoutineViewModel.class);


      ViewModel.getRoutine().observe(getViewLifecycleOwner(), routineResource -> {
            switch (routineResource.status){
                case LOADING:break;
                case SUCCESS:
                    binding.RoutineName.setText(routineResource.data.getName());
                    binding.routineCreator.setText(routineResource.data.getCreator());
                    binding.routineDiff.setText(routineResource.data.getDifficulty());
                    binding.ratingRoutine.setNumStars(5);

                    binding.ratingRoutine.setRating((float) routineResource.data.getAverageRating());
                    break;
                case ERROR:break;
            }
        });
        List<Cycle> cycles = new ArrayList<>();
        adapter = new extended_routine_adapter(cycles, getArguments().getInt("id"), application.getRoutineRepository());
        ViewModel.setRoutine(getArguments().getInt("id"));
        ViewModel.getCycles().observe(getViewLifecycleOwner(), listResource -> {
            switch (listResource.status) {
                case LOADING:
                    //  Toast.makeText(activity, "LOADING", Toast.LENGTH_SHORT).show();
                    break;
                case SUCCESS:
                    // activity.hideProgressBar();
                    cycles.clear();
                    cycles.addAll(listResource.data);
                    adapter.notifyDataSetChanged();
                    binding.cycleList.scrollToPosition(cycles.size() - 1);
                    binding.cycleList.setHasFixedSize(true);
                    binding.cycleList.setLayoutManager(new LinearLayoutManager(activity));
                    binding.cycleList.setAdapter(adapter);

                    break;
                case ERROR:
                    Toast.makeText(activity, "ERROR", Toast.LENGTH_SHORT).show();
                    break;
            }
        });


        binding.cycleList.setHasFixedSize(true);
        binding.cycleList.setLayoutManager(new LinearLayoutManager(activity));

        binding.cycleList.setAdapter(adapter);


    }



}