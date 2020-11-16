package com.example.gigatlon.ui.extended_routine;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gigatlon.R;

import java.util.List;

public class cycle_routine_adapter extends RecyclerView.Adapter<cycle_routine_adapter.CycleRoutineViewHolder>
{

    List<String> itemList;

    cycle_routine_adapter(List<String> list){
        this.itemList = list;
    }

    @NonNull
    @Override
    public CycleRoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.exercise_tem,
                        parent, false);

        return new CycleRoutineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CycleRoutineViewHolder holder, int position) {
        // Create an instance of the ChildItem
        // class for the given position
        String cycleName
                = itemList.get(position);

        // For the created instance, set title.
        // No need to set the image for
        // the ImageViews because we have
        // provided the source for the images
        // in the layout file itself
        holder
                .exerciseName
                .setText(cycleName);
        holder.exerciseType.setText("Exercise");
        holder.duration.setText("Duration: 15s");
        holder.reps.setText("Repetitions: 2");

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class CycleRoutineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView exerciseName;
        TextView exerciseType;
        TextView duration;
        TextView reps;



        public CycleRoutineViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exerciseName);
            exerciseType = itemView.findViewById(R.id.exerciseType);
            duration = itemView.findViewById(R.id.Duration);
            reps = itemView.findViewById(R.id.Reps);



        }

        @Override
        public void onClick(View view) {


        }
    }

}
