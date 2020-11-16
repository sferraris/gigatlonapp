package com.example.gigatlon.ui.execute;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.gigatlon.R;
import com.example.gigatlon.ui.extended_routine.Cycle;
import com.example.gigatlon.ui.extended_routine.ExtendedRoutineViewModel;

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

    private long timeLeft = 5000;
    private boolean timerRunning;
    private CountDownTimer timer;
    private AlertDialog dialog;


    Button prev;
    Button next;
    ImageButton stop;
    TextView counter;
    List<Cycle> cycles;
    View root;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel =  new ViewModelProvider(this).get(ExecuteViewModel.class);
        //Set Data
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




        return root;
    }

    private void pauseTimer(){
        timer.cancel();
        timerRunning = false;
    }

    private void startTimer(){
        timer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long l) {
                timeLeft = l;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                if(cycle == cycles.size()-1 && exerIncycle == cycles.get(cycle).getChildItemList().size()-1){
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
                        exit.setOnClickListener(v -> {
                            finish();
                        });
                        Rate.setOnClickListener(v ->{
                            //rate
                            finish();
                        });


                }
            }
        }.start();
        timerRunning = true;
    }

    private void finish(){
        dialog.dismiss();
        Navigation.findNavController(root).popBackStack();
        Navigation.findNavController(root).popBackStack();
        Navigation.findNavController(root).navigate(R.id.nav_extended_routine);

    }
    private void updateCountdownText(){
        int min = (int) timeLeft/ 1000 / 60;
        int sec = (int) timeLeft/ 1000 % 60;

        String timeString = String.format(Locale.getDefault(), "%02d:%02d", min, sec);
        counter.setText(timeString);


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ExecuteViewModel.class);
        // TODO: Use the ViewModel
    }

}