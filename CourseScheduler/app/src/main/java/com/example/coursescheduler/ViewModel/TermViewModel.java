package com.example.coursescheduler.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.coursescheduler.Database.CouseScheduleRepository;
import com.example.coursescheduler.Entity.TermEntity;

import java.util.List;

public class TermViewModel extends AndroidViewModel {
    private CouseScheduleRepository mRepository;
    private List<TermEntity> mAllTerems;
    public TermViewModel(Application application){
        super(application);
        mRepository=new CouseScheduleRepository(application);
        mAllTerems=mRepository.getAllTerms();
    }
    public List<TermEntity> getmAllTerems(){
        return mAllTerems;
    }
    public void insert(TermEntity termEntity){
        mRepository.insert(termEntity);
    }
    public void delete(TermEntity termEntity){
        mRepository.delete(termEntity);
    }
//    public int lastID(){ return mAllTerems.getValue().size();
//    }
}