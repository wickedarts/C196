package com.example.coursescheduler.DAO;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.coursescheduler.Entity.CourseEntity;

import java.util.List;

@Dao
public interface CourseDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CourseEntity course);
    @Update
    void update(CourseEntity course);

    @Delete
    void delete(CourseEntity course);

    @Query("DELETE FROM course_table")
    void deleteAllCourses();

    @Query("SELECT * FROM course_table ORDER BY courseID ASC")
    List<CourseEntity>getAllCourses();

    @Query("SELECT * FROM course_table WHERE courseTermID= :coursTermID ORDER BY courseID ASC")
    List<CourseEntity> getAllAssociatedCourses(int coursTermID);
}