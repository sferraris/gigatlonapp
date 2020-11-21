package com.example.gigatlon.ui.execute;

import androidx.appcompat.view.menu.MenuView;
import androidx.lifecycle.MediatorLiveData;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gigatlon.R;
import com.example.gigatlon.domain.Cycle;
import com.example.gigatlon.domain.Exercise;
import com.example.gigatlon.repository.RoutineRepository;
import com.example.gigatlon.vo.Resource;

import java.util.ArrayList;
import java.util.List;


public class second_execute_item_adapter extends RecyclerView.Adapter<second_execute_item_adapter.ViewHolder> {



    private List<Exercise> data;

    public second_execute_item_adapter(List<Exercise> list) {
       this.data = list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_tem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Exercise c = data.get(position);
        holder.bindata(c);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView exerName;

        public final TextView exerRep;
        public final TextView exerDur;


        public ViewHolder(View view) {
            super(view);
            exerName = (TextView) view.findViewById(R.id.exerciseName);

            exerRep= (TextView) view.findViewById(R.id.Reps);
            exerDur =(TextView) view.findViewById(R.id.Duration);
        }

        public void bindata(Exercise exercise){
            exerName.setText(String.format("%s %s", itemView.getResources().getString(R.string.exerName),  exercise.getName()));

            exerRep.setText(String.format("%s %s", itemView.getResources().getString(R.string.exerName),String.valueOf(exercise.getRepetitions())));
            exerDur.setVisibility(View.INVISIBLE);
        }


    }
}