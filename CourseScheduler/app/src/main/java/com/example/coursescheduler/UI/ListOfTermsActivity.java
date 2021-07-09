package com.example.coursescheduler.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursescheduler.Database.CouseScheduleRepository;
import com.example.coursescheduler.R;
import com.example.coursescheduler.UI.Adapters.TermAdapter;

public class ListOfTermsActivity extends AppCompatActivity {
    private CouseScheduleRepository courseScheduleRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DetailedCourseViewActivity.id2=-1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_terms);
        courseScheduleRepository = new CouseScheduleRepository(getApplication());
        courseScheduleRepository.getAllTerms();// this is really just to set up the database if there isn't one on your device yet-otherwise synch errors later
        RecyclerView recyclerView = findViewById(R.id.listOfTerms);

        final TermAdapter adapter = new TermAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setWords(courseScheduleRepository.getAllTerms());
    }

    public void addTerm(View view)
    {
        Intent intent = new Intent(ListOfTermsActivity.this, CourseActivity.class);
        startActivity(intent);
    }
}