package com.example.gigatlon.ui.execute;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gigatlon.R;

import java.util.List;


public class second_execute_item_adapter extends RecyclerView.Adapter<second_execute_item_adapter.ViewHolder> {

    private final List<String> mValues;

    public second_execute_item_adapter(List<String> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_tem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.exerName.setText(holder.mItem);
        holder.exerType.setVisibility(View.INVISIBLE);
        holder.exerDur.setVisibility(View.INVISIBLE);
        holder.exerRep.setText("Repetitions: 10");
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView exerName;
        public final TextView exerType;
        public final TextView exerRep;
        public final TextView exerDur;
        public String mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            exerName = (TextView) view.findViewById(R.id.exerciseName);
            exerType = (TextView) view.findViewById(R.id.exerciseType);
            exerRep= (TextView) view.findViewById(R.id.Reps);
            exerDur =(TextView) view.findViewById(R.id.Duration);
        }


    }
}