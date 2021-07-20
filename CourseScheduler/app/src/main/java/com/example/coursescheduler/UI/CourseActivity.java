package com.example.coursescheduler.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursescheduler.Database.CouseScheduleRepository;
import com.example.coursescheduler.Entity.AssessmentEntity;
import com.example.coursescheduler.Entity.CourseEntity;
import com.example.coursescheduler.Entity.TermEntity;
import com.example.coursescheduler.R;
import com.example.coursescheduler.UI.Adapters.AssessmentAdapter;
import com.example.coursescheduler.UI.Adapters.CourseAdapter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CourseActivity extends AppCompatActivity {
    private CouseScheduleRepository courseScheduleRepository;

    int mTermId;

    String termTitle;
    LocalDate startDate;
    LocalDate endDate;
    Integer isCurrentTerm = 0;
    CalendarView picker;
    TextView mills;

    EditText mEditTermTitle;
    EditText mEditStartDateDynamic;
    EditText mEditEndDateDynamic;
    TextView mIsCurrentTerm;

    LocalDate currentDate = LocalDate.now();

    TermEntity currentTerm;

    public static int numCourses;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mills=findViewById(R.id.dateInMillsCourseHIDE);//Hidden text view (Hide after testing)
        picker=(CalendarView)findViewById(R.id.calendarViewTerm);
        picker.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(year, month, dayOfMonth, 0, 0);
                Long dateLong = c.getTimeInMillis() / 1000;//epoch in seconds
                Long dateLongPlusSixMonths = dateLong + 15778458;//start date plus 6 months at 30.44 days each
                mills.setText(Long.toString(dateLong));
                mEditStartDateDynamic.setText(String.format(Instant.ofEpochSecond(dateLong).atZone(ZoneId.systemDefault()).toLocalDate().toString()));
                mEditEndDateDynamic.setText(String.format(Instant.ofEpochSecond(dateLongPlusSixMonths).atZone(ZoneId.systemDefault()).toLocalDate().toString()));

                startDate = LocalDate.parse(mEditStartDateDynamic.getText());
                endDate = LocalDate.parse(mEditEndDateDynamic.getText());
                if((startDate.isEqual(currentDate) || startDate.isBefore(currentDate)) && (endDate.isAfter(currentDate) || endDate.isEqual(currentDate))){
                    mIsCurrentTerm.setVisibility(View.VISIBLE);
                    isCurrentTerm = 1;
                }
                else{
                    mIsCurrentTerm.setVisibility(View.INVISIBLE);
                    isCurrentTerm = 0;
                }
            }
        });

        mTermId = getIntent().getIntExtra("termID", -1);
        if (mTermId == -1) mTermId = DetailedCourseViewActivity.id2;
        courseScheduleRepository = new CouseScheduleRepository(getApplication());
        List<TermEntity> allTerms = courseScheduleRepository.getAllTerms();

        for (TermEntity p:allTerms) {
            if (p.getTermID() == mTermId) currentTerm = p;
        }
        mEditTermTitle = findViewById(R.id.termTitle);
        mEditStartDateDynamic = findViewById(R.id.startDateDynamicTerm);
        mEditEndDateDynamic = findViewById(R.id.endDateDynamicTerm);
        mIsCurrentTerm = findViewById(R.id.currentTermText);

        if (currentTerm != null) {
            termTitle = currentTerm.getTermName();
            startDate = currentTerm.getStartDate();
            endDate = currentTerm.getEndDate();
            isCurrentTerm = currentTerm.getCurrentTerm();
        }
        if (mTermId != -1) {
            mEditTermTitle.setText(termTitle);
            mEditStartDateDynamic.setText(startDate.toString());
            mEditEndDateDynamic.setText(endDate.toString());
            if((startDate.isEqual(currentDate) || startDate.isBefore(currentDate)) && (endDate.isAfter(currentDate) || endDate.isEqual(currentDate))){
                mIsCurrentTerm.setVisibility(View.VISIBLE);
                isCurrentTerm = 1;
            }
            else{
                mIsCurrentTerm.setVisibility(View.INVISIBLE);
                isCurrentTerm = 0;
            }
        }
        courseScheduleRepository = new CouseScheduleRepository(getApplication());
        courseScheduleRepository.getAllCourses();// this is really just to set up the database if there isn't one on your device yet-otherwise synch errors later
        RecyclerView recyclerView = findViewById(R.id.listOfCourse);

        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<CourseEntity> filterCourses = new ArrayList<>();
        for (CourseEntity p : courseScheduleRepository.getAllCourses()) {
            if (p.getCourseTermID() == mTermId) filterCourses.add(p);
        }
        adapter.setWords(filterCourses);

        numCourses=filterCourses.size();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_activity, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.DeleteTerm:
                if (numCourses == 0) {
                    courseScheduleRepository.delete(currentTerm);
                    Toast.makeText(getApplicationContext(), "Term deleted", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(CourseActivity.this, ListOfAssessmentsActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Can't delete a Term with Courses", Toast.LENGTH_LONG).show();// make another toast
                }
            case R.id.ReloadCourses:
                refreshList();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addCoursePlusButton(View view) {
        Intent intent = new Intent(CourseActivity.this, DetailedCourseViewActivity.class);
        intent.putExtra("termID", mTermId);
        startActivity(intent);
    }

    public void addTermFromScreen(View view) {
        TermEntity p;
        List<TermEntity> allTerms = courseScheduleRepository.getAllTerms();

        if((startDate.isEqual(currentDate) || startDate.isBefore(currentDate)) && (endDate.isAfter(currentDate) || endDate.isEqual(currentDate))){
            isCurrentTerm = 1;
        }
        else{
            isCurrentTerm = 0;
        }

        if(mTermId == -1) {
            if (allTerms.isEmpty())
                mTermId = 0;
            else
                mTermId = allTerms.get(allTerms.size()-1).getTermID();
            p = new TermEntity(++mTermId, mEditTermTitle.getText().toString(), LocalDate.parse(mEditStartDateDynamic.getText()), LocalDate.parse(mEditEndDateDynamic.getText()), isCurrentTerm);
            courseScheduleRepository.insert(p);
        }
        else {
            p = new TermEntity(mTermId, mEditTermTitle.getText().toString(), LocalDate.parse(mEditStartDateDynamic.getText()), LocalDate.parse(mEditEndDateDynamic.getText()), isCurrentTerm);
            courseScheduleRepository.update(p);
        }
//        if(mTermId!=-1) {
//            p = new TermEntity(mTermId, mEditTermTitle.getText().toString(), LocalDate.parse(mEditStartDateDynamic.getText()), LocalDate.parse(mEditEndDateDynamic.getText()), isCurrentTerm);
//        }
//        else {
//            List<TermEntity> allTerms = courseScheduleRepository.getAllTerms();
//            if (allTerms.isEmpty())
//                mTermId = 0;
//            else
//                mTermId = allTerms.get(allTerms.size()-1).getTermID();
//            p = new TermEntity(++mTermId, mEditTermTitle.getText().toString(), LocalDate.parse(mEditStartDateDynamic.getText()), LocalDate.parse(mEditEndDateDynamic.getText()), isCurrentTerm);
//        }
//        courseScheduleRepository.insert(p);

        Intent intent = new Intent(CourseActivity.this, ListOfTermsActivity.class);
        startActivity(intent);
//        this.finish();
    }

    public void deleteTermFromScreen(View view) {
        if (numCourses == 0) {
            courseScheduleRepository.delete(currentTerm);
            Toast.makeText(getApplicationContext(), "Term deleted", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(CourseActivity.this, ListOfTermsActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Can't delete a Term with Courses", Toast.LENGTH_LONG).show();// make another toast
        }
    }

    private void refreshList(){
        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<CourseEntity> filteredCourses = new ArrayList<>();
        for(CourseEntity c:courseScheduleRepository.getAllCourses()){
            if(c.getCourseTermID()==mTermId)filteredCourses.add(c);
        }
        adapter.setWords(filteredCourses);
    }
}