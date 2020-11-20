package com.example.gigatlon.ui.extended_routine;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gigatlon.R;
import com.example.gigatlon.domain.Cycle;
import com.example.gigatlon.domain.Exercise;
import com.example.gigatlon.repository.RoutineRepository;
import com.example.gigatlon.ui.RoutineAdapter;
import com.example.gigatlon.vo.Resource;

import java.util.ArrayList;
import java.util.List;

public class extended_routine_adapter extends RecyclerView.Adapter<extended_routine_adapter.RoutineExtendedViewHolder>
{

    private RecyclerView.RecycledViewPool
            viewPool
            = new RecyclerView
            .RecycledViewPool();
    private List<Cycle> data;
RoutineRepository repository;

    private Integer routineId;
    private final static int PAGE_SIZE = 10;
    MediatorLiveData<Resource<List<Exercise>>> list = new MediatorLiveData<>();
    List<Exercise> exer = new ArrayList<>();
    ViewGroup p;


    extended_routine_adapter(List<Cycle> list, Integer id, RoutineRepository repo){
        this.data = list;
        this.routineId = id;
         this.repository = repo;
    }

    @NonNull
    @Override
    public RoutineExtendedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.cycle_item,
                        parent, false);

        this.p = parent;

        return new RoutineExtendedViewHolder(view);
    }

    private LiveData<Resource<List<Exercise>>>getChild(Integer id){
        more(id);
        return list;
    }

    private void more(Integer id){
        list.addSource( repository.getExercises(routineId, id, PAGE_SIZE, 0, "id", "asc"), r ->{
            switch (r.status){
                case LOADING:
                    //Log.d("UI", "");
                    break;
                case SUCCESS:
                   exer.clear();
                   exer.addAll(r.data);
                   list.setValue(Resource.success(exer));
                    break;

                case ERROR:
                    Log.d("UI", r.message);

            }
        });

    }

    @Override
    public void onBindViewHolder(@NonNull RoutineExtendedViewHolder holder, int position) {
        // Create an instance of the ParentItem
        // class for the given position
        Cycle parentItem = data.get(position);

        // For the created instance,
        // get the title and set it
        // as the text for the TextView
        holder.bindCycle(parentItem);

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

        getChild(parentItem.getId()).observe((LifecycleOwner) p.getContext(), r ->{
            switch (r.status){
                case LOADING:
                  //  Log.d("UI", r.message);
                    break;
                case SUCCESS:
                    layoutManager
                            .setInitialPrefetchItemCount(
                                    r.data.size());

                    // Create an instance of the child
                    // item view adapter and set its
                    // adapter, layout manager and RecyclerViewPool

                    cycle_routine_adapter childItemAdapter
                            = new cycle_routine_adapter(
                            r.data);
                    holder.setExerciceRecyclerView(layoutManager, childItemAdapter);

                    break;

                case ERROR:
                    Log.d("UI", r.message);
            }
        });


        // Since this is a nested layout, so
        // to define how many child items
        // should be prefetched when the
        // child RecyclerView is nested
        // inside the parent RecyclerView,
        // we use the following method





    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RoutineExtendedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView cycleName;
        RecyclerView exerciceRecyclerView;


        public RoutineExtendedViewHolder(@NonNull View itemView) {
            super(itemView);
            cycleName = itemView.findViewById(R.id.cycleName);
            exerciceRecyclerView = itemView.findViewById(R.id.exerciseItem);


        }

        public void setExerciceRecyclerView(RecyclerView.LayoutManager m, cycle_routine_adapter adapter){
            exerciceRecyclerView.setLayoutManager(m);
            exerciceRecyclerView.setAdapter(adapter);
            exerciceRecyclerView.setRecycledViewPool(viewPool);
        }
        public void bindCycle(Cycle cycle){
            cycleName.setText(cycle.getName());
        }

        @Override
        public void onClick(View view) {


        }
    }
}
