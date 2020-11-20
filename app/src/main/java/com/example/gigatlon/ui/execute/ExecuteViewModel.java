package com.example.gigatlon.ui.execute;

import android.util.Log;
import android.view.ViewGroup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gigatlon.domain.Cycle;
import com.example.gigatlon.domain.Exercise;
import com.example.gigatlon.domain.Routine;
import com.example.gigatlon.repository.RoutineRepository;
import com.example.gigatlon.viewmodel.RepositoryViewModel;
import com.example.gigatlon.vo.AbsentLiveData;
import com.example.gigatlon.vo.Resource;

import java.util.ArrayList;
import java.util.List;

public class ExecuteViewModel extends RepositoryViewModel<RoutineRepository> {
    // TODO: Implement the ViewModel


    private RecyclerView.RecycledViewPool
            viewPool
            = new RecyclerView
            .RecycledViewPool();



    private Integer routineId = null;
    private Integer cycleId;
    private final static int PAGE_SIZE = 500;
    boolean save = true;
    MediatorLiveData<Resource<List<Exercise>>> listExer = new MediatorLiveData<>();

    private long timerLeft;

    private  LiveData<Resource<Routine>> routine = null;
    MediatorLiveData<Resource<List<Cycle>>> listCycle = new MediatorLiveData<>();
    List<Cycle> allCycle = new ArrayList<>();
    List<Exercise> allExer = new ArrayList<>();

    public ExecuteViewModel(RoutineRepository repository) {
        super(repository);

        if(routineId == null) {
            routine = AbsentLiveData.create();
        }else{
            routine = repository.getRoutine(routineId);
        }


    }
    public LiveData<Resource<Routine>> getRoutine() {
        return routine;
    }

    public Integer getRoutinId(){
        return routineId;
    }

    public LiveData<Resource<List<Cycle>>> getCycles(){
        more();
        return listCycle;
    }

    public long getTimerLeft() {
        return timerLeft;
    }

    public void setSave() {
        this.save = true;
    }

    public boolean getSave(){
        return save;
    }

    public void setTimerLeft(long timerLeft, boolean f) {
        if(save || !f) {
            this.timerLeft = timerLeft;
        }
        save = false;
    }

    public void more(){

        listCycle.addSource(repository.getCycles(routineId,PAGE_SIZE ,0, "order", "asc" ), r->{
            switch (r.status){
                case LOADING:break;
                case SUCCESS:
                    allCycle.clear();
                    allCycle.addAll(r.data);
                    listCycle.setValue(Resource.success(allCycle));
                    break;
                case ERROR:
                    break;
            }
        });
    }

    public void setRoutine(int routineId) {
        if ((this.routineId != null) &&
                (routineId == this.routineId)) {
            return;
        }

        this.routineId = routineId;
    }

    public LiveData<Resource<List<Exercise>>>getChild(Integer id){
        moreExer(id);
        return listExer;
    }

    private void moreExer(Integer id){
        listExer.addSource( repository.getExercises(routineId, id, PAGE_SIZE, 0, "id", "asc"), r ->{
            switch (r.status){
                case LOADING:
                    //Log.d("UI", "");
                    break;
                case SUCCESS:
                    allExer.clear();
                    allExer.addAll(r.data);
                    listExer.setValue(Resource.success(allExer));
                    break;

                case ERROR:
                    Log.d("UI", r.message);

            }
        });

    }




}