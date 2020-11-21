package com.example.gigatlon.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gigatlon.R;
import com.example.gigatlon.domain.Routine;
import com.example.gigatlon.repository.RoutineRepository;
import com.example.gigatlon.vo.Resource;

import java.util.ArrayList;
import java.util.List;

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder> {



    List<Routine> data;
    RoutineRepository repository;
    ViewGroup parent;
    MediatorLiveData<Resource<List<Routine>>> listFav = new MediatorLiveData<>();
    List<Routine> favData = new ArrayList<>();
    private final static int PAGE_SIZE = 500;


    public RoutineAdapter(List<Routine> data, RoutineRepository r){

        this.data = data;
        this.repository = r;
    }

    public LiveData<Resource<List<Routine>>> getFav(){
        moreFav();
        return  listFav;
    }
    public void moreFav(){
        listFav.addSource( repository.getFavourites( PAGE_SIZE, 0, "id", "asc"), r ->{
            switch (r.status){
                case LOADING:
                    //Log.d("UI", "");
                    break;
                case SUCCESS:
                    favData.clear();
                    favData.addAll(r.data);
                    listFav.setValue(Resource.success(favData));
                    break;

                case ERROR:
                    Log.d("UI", r.message);

            }
        });
    }





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

        getFav().observe((LifecycleOwner) parent.getContext(), resource ->{
            switch (resource.status){
                case LOADING:
                    //  Log.d("UI", r.message);
                    break;
                case SUCCESS:
                    if(resource.data.contains(r)){
                        holder.setFav(true);
                    }else{
                       holder.setFav(false);
                    }
                    holder.fav.setOnClickListener(v ->{
                       if( resource.data.contains(r)){
                           repository.deleteFavourite(r.getId()).observe((LifecycleOwner) parent.getContext(), res ->{
                               switch (res.status){
                                   case LOADING:break;
                                   case SUCCESS:
                                       Toast.makeText(parent.getContext(), holder.itemView.getResources().getString(R.string.fave_bye), Toast.LENGTH_SHORT).show();
                                       holder.setFav(false);
                                            break;
                                   case ERROR:break;
                               }
                           });
                       }else{
                           repository.setFavourite(r.getId()).observe((LifecycleOwner) parent.getContext(), res ->{
                               switch (res.status){
                                   case LOADING:break;
                                   case SUCCESS:
                                       Toast.makeText(parent.getContext(), holder.itemView.getResources().getString(R.string.fave_good) , Toast.LENGTH_SHORT).show();
                                       holder.setFav(true);
                                       break;
                                   case ERROR:break;
                               }
                           });
                       }

                    });


                    break;

                case ERROR:
                    Log.d("UI", resource.message);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class RoutineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView routineName;
        TextView routineCreator;

        TextView routineDiff;
        ImageButton fav;
        ImageButton share;
        private int routineId;
        RatingBar ratingBar;


        public RoutineViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            routineName = itemView.findViewById(R.id.routine_list_item_id);
            routineCreator = itemView.findViewById(R.id.routine_creator);

            routineDiff = itemView.findViewById(R.id.routine_diff);
            this.fav = (ImageButton) itemView.findViewById(R.id.favButton);
            share = itemView.findViewById(R.id.shareButton);
            ratingBar = itemView.findViewById(R.id.ratingRoutine);




        }
        public void setFav(boolean isFav){
            if(isFav) {
                fav.setImageResource(R.drawable.ic_baseline_favorite_24);
            }else{
                fav.setImageResource(R.drawable.ic_baseline_favorite_border_24);
            }
        }



        public void bindTo(Routine r){
            routineId = r.getId();
            routineName.setText(r.getName());
            routineCreator.setText(String.format("%s %s", itemView.getResources().getString(R.string.creator),r.getCreator()));
            routineDiff.setText(String.format("%s %s", itemView.getResources().getString(R.string.routine_diff),r.getDifficulty()));

            ratingBar.setNumStars(5);
            ratingBar.setRating((float)r.getAverageRating());
            share.setOnClickListener(v->{
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                String id = String.valueOf(R.id.nav_extended_routine);
                StringBuilder s = new StringBuilder();
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Shared routine");
                s.append("http://www.example.com/id/").append(id).append("/").append(r.getId());
                sendIntent.putExtra(Intent.EXTRA_TEXT, s.toString());
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                parent.getContext().startActivity(shareIntent);
            });



        }


        @Override
        public void onClick(View view) {
            Bundle bundle = new Bundle();
            bundle.putInt("id", routineId);
           Navigation.findNavController(view).navigate(R.id.nav_extended_routine, bundle);


        }
    }


}
