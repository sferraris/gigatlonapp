package com.example.gigatlon.ui.progress;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gigatlon.R;
import com.example.gigatlon.ui.RoutineAdapter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ProgressFragment extends Fragment {

    private ProgressViewModel progressViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        progressViewModel =
                new ViewModelProvider(this).get(ProgressViewModel.class);
        View root = inflater.inflate(R.layout.fragment_progress, container, false);
        final TextView textView = root.findViewById(R.id.text_progress);
        progressViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        GraphView graph = root.findViewById(R.id.graphView);
        graph.setVisibility(View.VISIBLE);
        Button button = root.findViewById(R.id.add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText text =root.findViewById(R.id.addWeight);
                String Imput = text.getText().toString();
                progressViewModel.addElement(Double.valueOf(Imput));
                graph.setVisibility(View.VISIBLE);

                progressViewModel.getListData().observe(getViewLifecycleOwner(), new Observer<List<Double>>() {
                    @Override
                    public void onChanged(List<Double> strings) {
                        try {
                            DataPoint d[] = new DataPoint[strings.size()];
                            for(int i=0;i < strings.size();i++){
                                 d[i] = new DataPoint(i, strings.get(i));
                            }

                            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(d);

                            graph.addSeries(series);
                            graph.getViewport().setXAxisBoundsManual(true);
                            graph.getViewport().setMinX(0);
                            graph.getViewport().setMaxX(strings.size());
                            graph.setTitle("Progress");
                            graph.setTitleTextSize(100);
                            graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
                            graph.getGridLabelRenderer().setHighlightZeroLines(false);





                        } catch (IllegalArgumentException e) {

                        }
                    }
                });
            }
        });

        if(progressViewModel.getListData().getValue().isEmpty()) {
           graph.setVisibility(View.INVISIBLE);
        }


        return root;
    }
}