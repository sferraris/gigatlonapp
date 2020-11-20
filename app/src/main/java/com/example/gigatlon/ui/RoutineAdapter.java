package com.example.gigatlon.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gigatlon.R;
import com.example.gigatlon.domain.Routine;

import java.util.List;

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder> {



    List<Routine> data;

    public RoutineAdapter(List<Routine> data){
        this.data = data;
    }
    ViewGroup parent;


    @NonNull
    @Override
    public RoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        this.parent = parent;


        return new RoutineViewHolder(view);
    }






    @Override
    public void onBindViewHolder(@NonNull RoutineViewHolder holder, int position) {
        Routine r =data.get(position);
        holder.bindTo(r);

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
        ImageButton imageButton;
        private int routineId;
        RatingBar ratingBar;


        public RoutineViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            routineName = itemView.findViewById(R.id.routine_list_item_id);
            routineCreator = itemView.findViewById(R.id.routine_creator);
            routineDuration = itemView.findViewById(R.id.routine_duration);
            routineDiff = itemView.findViewById(R.id.routine_diff);
            imageButton = itemView.findViewWithTag(R.id.favButton);
            ratingBar = itemView.findViewById(R.id.ratingRoutine);





        }

        public void bindTo(Routine r){
            routineId = r.getId();
            routineName.setText(r.getName());
            routineCreator.setText(r.getCreator().getUsername());
            routineDiff.setText(r.getDifficulty());
            itemView.findViewById(R.id.favButton).setOnClickListener(v -> {
                Context context = routineName.getContext();
                String message = context.getResources().getString(R.string.Fav, getAdapterPosition());
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            });
            itemView.findViewById(R.id.shareButton).setOnClickListener(v -> {
                Context context = routineName.getContext();
                Toast.makeText(context, "Clicked Share!!", Toast.LENGTH_SHORT).show();
            });
            ratingBar.setNumStars(5);
            ratingBar.setRating((float)r.getAverageRating());



        }

        @Override
        public void onClick(View view) {
            Bundle bundle = new Bundle();
            bundle.putInt("id", routineId);
           Navigation.findNavController(view).navigate(R.id.nav_extended_routine, bundle);


        }
    }


}
