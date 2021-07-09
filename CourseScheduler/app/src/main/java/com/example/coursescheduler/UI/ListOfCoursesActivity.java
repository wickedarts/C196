package com.example.coursescheduler.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursescheduler.Database.CouseScheduleRepository;
import com.example.coursescheduler.R;
import com.example.coursescheduler.UI.Adapters.CourseAdapter;

public class ListOfCoursesActivity extends AppCompatActivity {
    private CouseScheduleRepository couseScheduleRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_list_of_courses);
        couseScheduleRepository = new CouseScheduleRepository(getApplication());
        couseScheduleRepository.getAllCourses();
        RecyclerView recyclerView = findViewById(R.id.listOfTerms);

        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setWords(couseScheduleRepository.getAllCourses());
    }

    public void addCourse(View view) {
        Intent intent = new Intent(ListOfCoursesActivity.this, DetailedCourseViewActivity.class);
        startActivity(intent);
    }
}



//public class ListOfTermsActivity extends AppCompatActivity {
//    private CouseScheduleRepository couseScheduleRepository;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_list_of_terms);
//        couseScheduleRepository = new CouseScheduleRepository(getApplication());
//        couseScheduleRepository.getAllTerms();
//        RecyclerView recyclerView = findViewById(R.id.listOfTerms);
//
//        final TermAdapter adapter = new TermAdapter(this);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter.setWords(couseScheduleRepository.getAllTerms());
//    }
//
//    public void addTerm(View view)
//    {
//        Intent intent = new Intent(ListOfTermsActivity.this, CourseActivity.class);
//        startActivity(intent);
//    }
//}