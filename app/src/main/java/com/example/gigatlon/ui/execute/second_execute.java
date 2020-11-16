package com.example.gigatlon.ui.execute;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.RatingBar;
import android.widget.TextView;


import com.example.gigatlon.R;

import com.example.gigatlon.ui.extended_routine.Cycle;


import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class second_execute extends Fragment {


    private ExecuteViewModel mViewModel;
    second_execute_item_adapter adapter;
    private static Integer current_cycle = 0;

    private AlertDialog dialog;


    Button prev;
    Button next;
    List<Cycle> cycles;
    View root;


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
         root = inflater.inflate(R.layout.fragment_second_execute_list, container, false);
        mViewModel =  new ViewModelProvider(this).get(ExecuteViewModel.class);
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
        mViewModel.getListData().observe(getViewLifecycleOwner(), new Observer<List<Cycle>>() {
            @Override
            public void onChanged(List<Cycle> strings) {
                adapter = new second_execute_item_adapter(strings.get(current_cycle).getChildItemList());
                RecyclerView recyclerView = root.findViewById(R.id.second_execute);


                recyclerView.setHasFixedSize(true);
                recyclerView.setNestedScrollingEnabled(false);

                recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

                recyclerView.setAdapter(adapter);
            }
        });
        //Get cycle list
        cycles = mViewModel.getList();

        //get cycle number from argument
        current_cycle = getArguments().getInt("cycleId");



        //get buttons
        prev = root.findViewById(R.id.exer_prev);
        next = root.findViewById(R.id.exer_next);
        //get current cycle
        Cycle current = cycles.get(current_cycle);
        //edit text view
        TextView cycleName;
        cycleName = root.findViewById(R.id.ExerCycle);
        cycleName.setText(current.getParentItemTitle());


        //check button visibility
        if(current_cycle == 0){
            prev.setVisibility(View.INVISIBLE);
        }

        if(current_cycle == cycles.size()-1  ){
            next.setText("Exit");
        }else{
            next.setText("Next");
        }


        //next action
        next.setOnClickListener(v -> {

            if(current_cycle!=cycles.size()-1) {
                current_cycle++;
                Bundle bundle = new Bundle();
                bundle.putInt("cycleId", current_cycle);
                Navigation.findNavController(root).popBackStack();
                Navigation.findNavController(root).navigate(R.id.nav_second_execute, bundle);
            }else{
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(getContext());
                View popUp = getLayoutInflater().inflate(R.layout.popup_rating, null);
                builder.setView(popUp);
                dialog = builder.create();
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                Button exit = popUp.findViewById(R.id.exit);
                Button Rate = popUp.findViewById(R.id.exit_rate);
                RatingBar ratingBar = popUp.findViewById(R.id.ratingBar);
                exit.setOnClickListener(g -> {
                    finish();
                });
                Rate.setOnClickListener(g ->{
                    //rate
                    finish();
                });

            }
        });

        prev.setOnClickListener(v -> {

           current_cycle--;

            Bundle bundle = new Bundle();
            bundle.putInt("cycleId", current_cycle);
            Navigation.findNavController(root).popBackStack();
            Navigation.findNavController(root).navigate(R.id.nav_second_execute, bundle);
        });


        return root;
    }

    private void finish(){
        dialog.dismiss();
        Navigation.findNavController(root).popBackStack();
        Navigation.findNavController(root).popBackStack();
        Navigation.findNavController(root).navigate(R.id.nav_extended_routine);

    }
}