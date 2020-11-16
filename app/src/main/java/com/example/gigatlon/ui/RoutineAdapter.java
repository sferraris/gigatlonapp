package com.example.gigatlon.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gigatlon.MyApplication;
import com.example.gigatlon.R;

import java.util.List;

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder> {


    List<String> data;
    public RoutineAdapter(List<String> data){
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
        String name =data.get(position);
        holder.routineName.setText(name);
        holder.routineCreator.setText("By: Micus");
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
        ImageButton imageButton;

        public RoutineViewHolder(@NonNull View itemView) {
            super(itemView);
            routineName = itemView.findViewById(R.id.routine_list_item_id);
            itemView.setOnClickListener(this);
            routineCreator = itemView.findViewById(R.id.routine_creator);
            routineDuration = itemView.findViewById(R.id.routine_duration);
            routineDiff = itemView.findViewById(R.id.routine_diff);
            imageButton = itemView.findViewWithTag(R.id.favButton);
            itemView.findViewById(R.id.favButton).setOnClickListener(v -> {
                Context context = routineName.getContext();
                String message = context.getResources().getString(R.string.Fav, getAdapterPosition());
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();


            });
            itemView.findViewById(R.id.shareButton).setOnClickListener(v -> {
                Context context = routineName.getContext();
                Toast.makeText(context, "Clicked Share!!", Toast.LENGTH_SHORT).show();
            });

            Bundle bundle = new Bundle();
            bundle.putInt("id", getAdapterPosition());
            itemView.setOnClickListener( Navigation.createNavigateOnClickListener(R.id.nav_extended_routine, bundle));



        }

        @Override
        public void onClick(View view) {
            Context context = routineName.getContext();
            String message = context.getResources().getString(R.string.Message, getAdapterPosition());
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();



        }
    }


}
