package com.example.gigatlon.ui.execute;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gigatlon.MyApplication;
import com.example.gigatlon.R;
import com.example.gigatlon.domain.Cycle;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Execute extends Fragment {

    private ExecuteViewModel mViewModel;

    public static Execute newInstance() {
        return new Execute();
    }

    private static Integer exerIncycle = 0;
    private static Integer cycle = 0;
    private static Integer routineId;

    private long timeLeft = 5000;
    private boolean timerRunning;
    private CountDownTimer timer;
    private AlertDialog dialog;
    private Integer cantCycles = -1;
    private Integer cantLastCycle = -1;



    ExerciseExecutionBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ExerciseExecutionBinding.inflate(getLayoutInflater());

        mViewModel =  new ViewModelProvider(this).get(ExecuteViewModel.class);
        root= inflater.inflate(R.layout.exercise_execution, container, false);
        //Set Data
        /*
        if(mViewModel.getList().isEmpty()){
            List<String> l = new ArrayList<>();
            l.add("Ex1");
            l.add("EX2");
            l.add("EX3");
            Cycle n = new Cycle("n1", l);
            Cycle n1 = new Cycle("n2", l);
            Cycle n2 = new Cycle("n3", l);
            mViewModel.addElement(n);
            mViewModel.addElement(n1);
            mViewModel.addElement(n2);

        }
        //Get cycle list
        cycles = mViewModel.getList();
        //Get exer number from argument
        exerIncycle = getArguments().getInt("ExerId");
        //get cycle number from argument
        cycle = getArguments().getInt("CycleId");


        //root
         root= inflater.inflate(R.layout.exercise_execution, container, false);

        //get buttons
        prev = root.findViewById(R.id.exer_prev);
        next = root.findViewById(R.id.exer_next);
        stop = root.findViewById(R.id.pauseTimer);
        counter = root.findViewById(R.id.exer_remaining_time);
        //get current cycle
        Cycle current = cycles.get(cycle);
        //edit text view
        TextView cycleName, exerName, reps, nextExer, detail;
        cycleName = root.findViewById(R.id.ExerCycle);
        exerName = root.findViewById(R.id.Exercise);
        reps = root.findViewById(R.id.exer_reps);
        detail = root.findViewById(R.id.exer_detail);
        nextExer = root.findViewById(R.id.next_exer);
        cycleName.setText(current.getParentItemTitle());
        exerName.setText(current.getChildItemList().get(exerIncycle));
        reps.setText("Repetition: 10");
        detail.setText("Este es el detalle de el ejercicio que se esta ejecutando en el momento");

        //check button visibility
        if(cycle == 0 && exerIncycle==0){
            prev.setVisibility(View.INVISIBLE);
        }
        if(cycle == cycles.size()-1 && exerIncycle == current.getChildItemList().size()-1 ){
            next.setVisibility(View.INVISIBLE);
            nextExer.setVisibility(View.INVISIBLE);
        }else{
            String s = "Next exer: ";
            if(exerIncycle < current.getChildItemList().size()-1){

                nextExer.setText(String.format("%s%s", s, current.getChildItemList().get(exerIncycle + 1)));
            }
            else {
                nextExer.setText(String.format("%s%s", s, cycles.get(cycle + 1).getChildItemList().get(0)));
            }

        }

        //pause action
        stop.setOnClickListener(v -> {
            if(timerRunning){
                pauseTimer();
                stop.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);


            }else {
                startTimer();
                stop.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
            }
        });

        //next action
        next.setOnClickListener(v -> {
            if(exerIncycle < current.getChildItemList().size()-1){
                exerIncycle++;
            }
            else {
                exerIncycle = 0;
                cycle++;
            }
            timer.cancel();


            Bundle bundle = new Bundle();
            bundle.putInt("ExerId", exerIncycle);
            bundle.putInt("CycleId", cycle);
            Navigation.findNavController(root).popBackStack();
            Navigation.findNavController(root).navigate(R.id.nav_execute, bundle);
        });

        prev.setOnClickListener(v -> {
            if(exerIncycle > 0){
                exerIncycle--;
            }
            else {
                cycle--;
                exerIncycle = cycles.get(cycle).getChildItemList().size()-1;

            }
            timer.cancel();

            Bundle bundle = new Bundle();
            bundle.putInt("ExerId", exerIncycle);
            bundle.putInt("CycleId", cycle);
            Navigation.findNavController(root).popBackStack();
            Navigation.findNavController(root).navigate(R.id.nav_execute, bundle);
        });




        startTimer();

        updateCountdownText();



*/
        return root;
    }

    private void pauseTimer(){
        timer.cancel();
        timerRunning = false;
    }

    private void startTimer(){ /*
        timer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long l) {
                timeLeft = l;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                if(cycle == cantCycles&& exerIncycle == cantLastCycle){
                    AlertDialog.Builder builder;
                        PopupRatingBinding binding_pop = PopupRatingBinding.inflate(getLayoutInflater());
                        builder = new AlertDialog.Builder(getContext());

                        builder.setView(binding_pop.getRoot());
                        dialog = builder.create();
                        dialog.setCancelable(false);
                       dialog.setCanceledOnTouchOutside(false);
                        dialog.show();

                        //RatingBar ratingBar = popUp.findViewById(R.id.ratingBar);
                        binding_pop.exit.setOnClickListener(v -> {
                            finish();
                        });
                        binding_pop.exitRate.setOnClickListener(v ->{
                            //rate
                            finish();
                        });


                }
            }
        }.start();
        timerRunning = true;*/
    }

    private void finish(){
        dialog.dismiss();
        Bundle bundle = new Bundle();
        bundle.putInt("id", routineId);
        mViewModel.setSave();
        Navigation.findNavController(binding.getRoot()).popBackStack();
        Navigation.findNavController(binding.getRoot()).popBackStack();
        Navigation.findNavController(binding.getRoot()).navigate(R.id.nav_extended_routine, bundle);

    }
    private void updateCountdownText(){
        int min = (int) timeLeft/ 1000 / 60;
        int sec = (int) timeLeft/ 1000 % 60;

        String timeString = String.format(Locale.getDefault(), "%02d:%02d", min, sec);
        binding.exerRemainingTime.setText(timeString);


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
        exerIncycle = getArguments().getInt("ExerId");
        //get cycle number from argument
        cycle = getArguments().getInt("CycleId");
        mViewModel.setRoutine(routineId);


        mViewModel.getCycles().observe(getViewLifecycleOwner(), listResource -> {
            switch (listResource.status){
                case LOADING:break;
                case SUCCESS:
                    binding.ExerCycle.setText(listResource.data.get(cycle).getName());
                    cantCycles = listResource.data.size()-1;
                    mViewModel.getChild(listResource.data.get(cycle).getId()).observe(getViewLifecycleOwner(), exerResource ->{
                        switch (exerResource.status){
                            case LOADING:break;
                            case SUCCESS:
                                if(exerIncycle == -1){
                                    exerIncycle = exerResource.data.size()-1;
                                }
                                if(cycle == cantCycles){
                                    cantLastCycle = exerResource.data.size()-1;
                                }
                                binding.Exercise.setText(exerResource.data.get(exerIncycle).getName());
                                binding.exerDetail.setText(exerResource.data.get(exerIncycle).getDetail());
                                binding.exerReps.setText(String.valueOf(exerResource.data.get(exerIncycle).getRepetitions()));

                                mViewModel.setTimerLeft( exerResource.data.get(exerIncycle).getDuration() * 1000, true);
                               timeLeft = mViewModel.getTimerLeft();
                                if(cycle == 0 && exerIncycle==0){
                                    binding.exerPrev.setVisibility(View.INVISIBLE);
                                }
                                if(cycle == listResource.data.size()-1 && exerIncycle == exerResource.data.size()-1 ){
                                    binding.exerNext.setVisibility(View.INVISIBLE);
                                    binding.nextExer.setVisibility(View.INVISIBLE);
                                }else{
                                    String s = "Next exer: ";
                                    if(exerIncycle <exerResource.data.size()-1){

                                        binding.nextExer.setText(String.format("%s %s", s, exerResource.data.get(exerIncycle+1).getName()));
                                    }
                                    else {
                                        binding.nextExer.setText(String.format("%s %s", "Next Cycle", listResource.data.get(cycle+1).getName()));
                                    }

                                }
                                //next action
                                binding.exerNext.setOnClickListener(v -> {
                                    if(exerIncycle < exerResource.data.size()-1 ){
                                        exerIncycle++;
                                    }
                                    else {
                                        exerIncycle = 0;
                                        cycle++;
                                    }
                                    timer.cancel();
                                    mViewModel.setSave();

                                    Bundle bundle = new Bundle();
                                    bundle.putInt("ExerId", exerIncycle);
                                    bundle.putInt("CycleId", cycle);
                                    bundle.putInt("RoutineId", routineId);
                                    Navigation.findNavController(binding.getRoot()).popBackStack();
                                    Navigation.findNavController(binding.getRoot()).navigate(R.id.nav_execute, bundle);
                                });

                                binding.exerPrev.setOnClickListener(v -> {
                                    if(exerIncycle > 0){
                                        exerIncycle--;
                                    }
                                    else {
                                        cycle--;
                                        exerIncycle = -1;

                                    }
                                    timer.cancel();
                                    mViewModel.setSave();

                                    Bundle bundle = new Bundle();
                                    bundle.putInt("ExerId", exerIncycle);
                                    bundle.putInt("CycleId", cycle);
                                    bundle.putInt("RoutineId", routineId);
                                    Navigation.findNavController(binding.getRoot()).popBackStack();
                                    Navigation.findNavController(binding.getRoot()).navigate(R.id.nav_execute, bundle);
                                });

                                if(savedInstanceState != null  ){
                                    timeLeft = savedInstanceState.getLong("timer");
                                    mViewModel.setTimerLeft(timeLeft, false);
                                    timerRunning = savedInstanceState.getBoolean("isRunning");
                                    if(!timerRunning && mViewModel.getSave()) {
                                        startTimer();
                                    }


                                }else {
                                    if(!timerRunning && mViewModel.getSave()) {
                                        startTimer();
                                    }

                                }
                                updateCountdownText();


                                break;
                            case ERROR:break;
                        }
                    });

                    break;
                case ERROR:break;
            }
        });





        //pause action
        binding.pauseTimer.setOnClickListener(v -> {
            if(timerRunning){
                pauseTimer();
                binding.pauseTimer.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);


            }else {
                startTimer();
                binding.pauseTimer.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
            }
        });





    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
      // timer.cancel();
        outState.putLong("timer", timeLeft);
        outState.putBoolean("isRunnig", timerRunning);
    }

    @Override
    public void onStop() {
        super.onStop();
        mViewModel.setTimerLeft(timeLeft, false);
        timer.cancel();

    }

    @Override
    public void onResume() {
        super.onResume();
        timeLeft = mViewModel.getTimerLeft();
        if(!timerRunning && mViewModel.getSave()) {
            startTimer();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        timer.cancel();
    }
}