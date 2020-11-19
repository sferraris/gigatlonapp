package com.example.gigatlon.ui.progress;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
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

import com.example.gigatlon.MyApplication;
import com.example.gigatlon.R;
import com.example.gigatlon.databinding.FragmentProgressBinding;
import com.example.gigatlon.domain.Weighting;
import com.example.gigatlon.repository.UserRepository;
import com.example.gigatlon.ui.MainActivity;
import com.example.gigatlon.ui.RoutineAdapter;
import com.example.gigatlon.ui.account.AccountViewModel;
import com.example.gigatlon.viewmodel.RepositoryViewModelFactory;
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

    private MyApplication application;
    private MainActivity activity;
    Double Height;

    FragmentProgressBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentProgressBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        application = (MyApplication)getActivity().getApplication();
        activity = (MainActivity)getActivity();
        RepositoryViewModelFactory viewModelFactory = new RepositoryViewModelFactory(UserRepository.class, application.getUserRepository());
        progressViewModel = new ViewModelProvider(this, viewModelFactory).get(ProgressViewModel.class);

        progressViewModel.getCurrentWeighting().observe(getViewLifecycleOwner(), listResource -> {
            switch (listResource.status) {
                case LOADING:
                    // binding.login.setEnabled(false);
                    //activity.showProgressBar();

                    break;
                case SUCCESS:
                    if(!listResource.data.isEmpty()) {
                        try {
                            DataPoint d[] = new DataPoint[listResource.data.size()];
                            for (int i = 0; i < listResource.data.size(); i++) {
                                d[i] = new DataPoint(i, Double.valueOf(listResource.data.get(i).getWeight()));
                            }
                            Height = listResource.data.get(listResource.data.size()-1).getHeight();

                            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(d);
                            binding.graphView.addSeries(series);
                            binding.graphView.getViewport().setXAxisBoundsManual(true);
                            binding.graphView.getViewport().setMinX(0);
                            binding.graphView.getViewport().setMaxX(listResource.data.size());
                            binding.graphView.setTitle("Progress");
                            binding.graphView.setTitleTextSize(100);
                            binding.graphView.getGridLabelRenderer().setHorizontalLabelsVisible(false);
                            binding.graphView.getGridLabelRenderer().setHighlightZeroLines(false);

                        } catch (IllegalArgumentException e) {

                        }
                    } else {
                        binding.graphView.setVisibility(View.INVISIBLE);
                    }

                    break;
                case ERROR:
                    // binding.login.setEnabled(true);
                    //activity.hideProgressBar();
                    Toast.makeText(application, listResource.message, Toast.LENGTH_SHORT).show();
                    break;
            }


        });

        binding.graphView.setVisibility(View.VISIBLE);

        binding.add.setOnClickListener(v -> {

            Weighting w = new Weighting(Double.valueOf(binding.addWeight.getText().toString()), Height);
            progressViewModel.updateWeighting(w).observe(getViewLifecycleOwner(), resource ->{
                switch (resource.status) {
                    case LOADING:
                        // binding.login.setEnabled(false);
                        //activity.showProgressBar()
                        break;
                    case SUCCESS:
                        binding.addWeight.setText("");

                        break;
                    case ERROR:
                        // binding.login.setEnabled(true);
                        //activity.hideProgressBar();
                        Toast.makeText(application, resource.message, Toast.LENGTH_SHORT).show();
                        break;
                }
            });



        });


    }


}