package com.example.gigatlon.ui.routines;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gigatlon.R;

import java.util.List;

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder> {


    List<String> data;
    public RoutineAdapter(List<String> data){
        this.data = data;
    }

    @NonNull
    @Override
    public RoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.routine_list_item, parent, false);

        return new RoutineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineViewHolder holder, int position) {
        String name =data.get(position);
        holder.routineName.setText(name);
        holder.routineCreator.setText("Micus");
        holder.routineDiff.setText("Difficulty: Rookie");
        holder.routineDuration.setText("Duration: 15:05s");


    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class RoutineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView routineName;
        TextView routineCreator;
        TextView routineDuration;
        TextView routineDiff;
        public RoutineViewHolder(@NonNull View itemView) {
            super(itemView);
            routineName = itemView.findViewById(R.id.routine_list_item_id);
            itemView.setOnClickListener(this);
            routineCreator = itemView.findViewById(R.id.routine_creator);
            routineDuration = itemView.findViewById(R.id.routine_duration);
            routineDiff = itemView.findViewById(R.id.routine_diff);


        }

        @Override
        public void onClick(View view) {

        }
    }


}
