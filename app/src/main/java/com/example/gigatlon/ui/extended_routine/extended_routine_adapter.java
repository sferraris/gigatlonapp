package com.example.gigatlon.ui.extended_routine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gigatlon.R;

import java.util.List;

public class extended_routine_adapter extends RecyclerView.Adapter<extended_routine_adapter.RoutineExtendedViewHolder>
{

    private RecyclerView.RecycledViewPool
            viewPool
            = new RecyclerView
            .RecycledViewPool();
    private List<Cycle> itemList;

    private Integer id;

    extended_routine_adapter(List<Cycle> list, Integer id){
        this.itemList = list;
        this.id = id;
    }

    @NonNull
    @Override
    public RoutineExtendedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.cycle_item,
                        parent, false);

        return new RoutineExtendedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineExtendedViewHolder holder, int position) {
        // Create an instance of the ParentItem
        // class for the given position
        Cycle parentItem = itemList.get(position);

        // For the created instance,
        // get the title and set it
        // as the text for the TextView
        holder.cycleName.setText(parentItem.getParentItemTitle());


        // Create a layout manager
        // to assign a layout
        // to the RecyclerView.

        // Here we have assigned the layout
        // as LinearLayout with vertical orientation
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(
                holder
                        .exerciceRecyclerView
                        .getContext(),
                LinearLayoutManager.VERTICAL,
                false);

        // Since this is a nested layout, so
        // to define how many child items
        // should be prefetched when the
        // child RecyclerView is nested
        // inside the parent RecyclerView,
        // we use the following method
        layoutManager
                .setInitialPrefetchItemCount(
                        parentItem
                                .getChildItemList()
                                .size());

        // Create an instance of the child
        // item view adapter and set its
        // adapter, layout manager and RecyclerViewPool

        cycle_routine_adapter childItemAdapter
                = new cycle_routine_adapter(
                parentItem
                        .getChildItemList());
        holder
                .exerciceRecyclerView
                .setLayoutManager(layoutManager);
        holder
                .exerciceRecyclerView
                .setAdapter(childItemAdapter);

        holder
                .exerciceRecyclerView
                .setRecycledViewPool(viewPool);


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class RoutineExtendedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView cycleName;
        RecyclerView exerciceRecyclerView;


        public RoutineExtendedViewHolder(@NonNull View itemView) {
            super(itemView);
            cycleName = itemView.findViewById(R.id.cycleName);
            exerciceRecyclerView = itemView.findViewById(R.id.exerciseItem);







        }

        @Override
        public void onClick(View view) {


        }
    }
}
