package com.example.coursescheduler.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.coursescheduler.Database.CouseScheduleRepository;
import com.example.coursescheduler.Entity.CourseEntity;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {
    int courseID;
    private CouseScheduleRepository mRepository;
    private List<CourseEntity> mAssociatedCourses;
    private List<CourseEntity> mAllCourses;
    public CourseViewModel(Application application, int courseID){
        super(application);
        mRepository=new CouseScheduleRepository(application);
        mAssociatedCourses=mRepository.getAssociatedCourses();
    }
    public CourseViewModel(Application application){
        super(application);
        mRepository=new CouseScheduleRepository(application);
        mAllCourses=mRepository.getAllCourses();
        mAssociatedCourses=mRepository.getAssociatedCourses();
    }
    public List<CourseEntity> getAssociatedCourses(int productID){
        return mRepository.getAssociatedCourses();
    }
    public List<CourseEntity> getAllCourses(){
        return mAllCourses;
    }
    public void insert(CourseEntity courseEntity){
        mRepository.insert(courseEntity);
    }
//    public int lastID(){
//        return mAllCourses.getValue().size();
//    }
}
