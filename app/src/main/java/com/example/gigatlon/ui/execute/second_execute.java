package com.example.gigatlon.ui.execute;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.gigatlon.MyApplication;
import com.example.gigatlon.R;
import com.example.gigatlon.databinding.FragmentSecondExecuteListBinding;
import com.example.gigatlon.databinding.PopupRatingBinding;
import com.example.gigatlon.domain.Cycle;
import com.example.gigatlon.repository.RoutineRepository;
import com.example.gigatlon.ui.MainActivity;
import com.example.gigatlon.viewmodel.RepositoryViewModelFactory;


import java.util.ArrayList;
import java.util.List;


/**
 * A fragment representing a list of Items.
 */
public class second_execute extends Fragment {


    private ExecuteViewModel mViewModel;
    second_execute_item_adapter adapter;
    private static Integer current_cycle = 0;
    private static Integer routineId;

    private AlertDialog dialog;


    FragmentSecondExecuteListBinding binding;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public second_execute() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static second_execute newInstance(int columnCount) {
        second_execute fragment = new second_execute();

        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =  FragmentSecondExecuteListBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
        MyApplication application = (MyApplication) getActivity().getApplication();
        MainActivity activity = (MainActivity) getActivity();
        ViewModelProvider.Factory viewModelFactory = new RepositoryViewModelFactory<>(RoutineRepository.class, application.getRoutineRepository());
        mViewModel= new ViewModelProvider(this, viewModelFactory).get(ExecuteViewModel.class);
        routineId = getArguments().getInt("RoutineId");
        //get cycle number from argument
        current_cycle = getArguments().getInt("CycleId");
        mViewModel.setRoutine(routineId);

        mViewModel.getCycles().observe(getViewLifecycleOwner(), listResource -> {
            switch (listResource.status){
                case LOADING:break;
                case SUCCESS:
                    binding.ExerCycle.setText(String.format("%s %s", getResources().getString(R.string.cycleName), listResource.data.get(current_cycle).getName()));
                    mViewModel.getChild(listResource.data.get(current_cycle).getId()).observe(getViewLifecycleOwner(), exer ->{
                        switch (exer.status){
                            case LOADING:break;
                            case SUCCESS:
                                adapter = new second_execute_item_adapter(exer.data);

                                binding.secondExecute.setHasFixedSize(true);
                                binding.secondExecute.setNestedScrollingEnabled(false);

                                binding.secondExecute.setLayoutManager(new LinearLayoutManager(getContext()));

                                binding.secondExecute.setAdapter(adapter);

                                if(current_cycle == 0){
                                    binding.exerPrev.setVisibility(View.INVISIBLE);
                                }

                                if(current_cycle == listResource.data.size()-1 ){
                                    binding.exerNext.setText(getResources().getString(R.string.exit));
                                }else{
                                    binding.exerNext.setText(getResources().getString(R.string.next));
                                }

                                binding.exerNext.setOnClickListener(v -> {

                                    if(current_cycle!=listResource.data.size()-1) {
                                        current_cycle++;
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("CycleId", current_cycle);
                                        bundle.putInt("RoutineId", routineId);
                                        Navigation.findNavController(binding.getRoot()).popBackStack();
                                        Navigation.findNavController(binding.getRoot()).navigate(R.id.nav_second_execute, bundle);
                                    }else{
                                        AlertDialog.Builder builder;
                                        builder = new AlertDialog.Builder(getContext());
                                        PopupRatingBinding binding_pop = PopupRatingBinding.inflate(getLayoutInflater());
                                        builder.setView(binding_pop.getRoot());
                                        dialog = builder.create();
                                        dialog.setCancelable(false);
                                        dialog.setCanceledOnTouchOutside(false);
                                        dialog.show();

                                    //    RatingBar ratingBar = binding_pop.findViewById(R.id.ratingBar);
                                        binding_pop.exit.setOnClickListener(g -> {
                                            finish();
                                        });
                                        binding_pop.exitRate.setOnClickListener(g ->{

                                            Integer r = (int) binding_pop.ratingBar.getRating();
                                            application.getRoutineRepository().setRating(r, routineId).observe(getViewLifecycleOwner(), resource->{
                                                switch (resource.status){
                                                    case ERROR:break;
                                                    case SUCCESS:
                                                        Toast.makeText(getContext(), getResources().getString(R.string.rating_not_sent), Toast.LENGTH_SHORT);
                                                        break;
                                                    case LOADING:
                                                        Toast.makeText(getContext(), getResources().getString(R.string.rating_sent), Toast.LENGTH_SHORT);
                                                        break;
                                                }
                                            });
                                            finish();
                                            finish();
                                        });

                                    }
                                });

                                binding.exerPrev.setOnClickListener(v -> {

                                    current_cycle--;

                                    Bundle bundle = new Bundle();
                                    bundle.putInt("CycleId", current_cycle);
                                    bundle.putInt("RoutineId", routineId);
                                    Navigation.findNavController(binding.getRoot()).popBackStack();
                                    Navigation.findNavController(binding.getRoot()).navigate(R.id.nav_second_execute, bundle);
                                });
                                break;
                            case ERROR:break;
                        }
                    });

                    break;
                case ERROR:break;
            }
        });






    }


    private void finish(){
        dialog.dismiss();
        Bundle bundle = new Bundle();
        bundle.putInt("id", routineId);
        Navigation.findNavController(binding.getRoot()).popBackStack();
        Navigation.findNavController(binding.getRoot()).popBackStack();
        Navigation.findNavController(binding.getRoot()).navigate(R.id.nav_extended_routine, bundle);

    }
}