package com.example.coursescheduler.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursescheduler.Database.CouseScheduleRepository;
import com.example.coursescheduler.Entity.AssessmentEntity;
import com.example.coursescheduler.Entity.CourseEntity;
import com.example.coursescheduler.R;
import com.example.coursescheduler.UI.Adapters.AssessmentAdapter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DetailedCourseViewActivity extends AppCompatActivity {
    private CouseScheduleRepository courseScheduleRepository;

    static int id2;

    int mCourseId;
    int mTermId;

    String courseName;
    String startDate;
    String endDate;
    String courseStatus;
    String mentor;
    String mentorPhone;
    String mentorEmail;
    CalendarView picker;
    TextView mills;

    EditText mEditName;
    EditText mEditStartDate;
    EditText mEditEndDate;
    EditText mEditStatus;
    EditText mEditMentor;
    EditText mEditMentorPhone;
    EditText mEditMentorEmail;

    LocalDate currentDate = LocalDate.now();

    CourseEntity currentCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_course_view);

        mills=findViewById(R.id.dateInMillsCourseHIDE);//Hidden text view (Hide after testing)
        picker=(CalendarView)findViewById(R.id.calendarViewCourse);
        picker.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(year, month, dayOfMonth, 0, 0);
                Long dateLong = c.getTimeInMillis() / 1000;//epoch in seconds
                Long dateLongPlusSixMonths = dateLong + 15778458;//start date plus 6 months at 30.44 days each
                mills.setText(Long.toString(dateLong));
                mEditStartDate.setText(String.format(Instant.ofEpochSecond(dateLong).atZone(ZoneId.systemDefault()).toLocalDate().toString()));
                mEditEndDate.setText(String.format(Instant.ofEpochSecond(dateLongPlusSixMonths).atZone(ZoneId.systemDefault()).toLocalDate().toString()));
            }
        });

        mCourseId = getIntent().getIntExtra("courseID", -1);
        courseName = getIntent().getStringExtra("courseName");
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");
        courseStatus = getIntent().getStringExtra("courseStatus");
        mentor = getIntent().getStringExtra("mentorName");
        mentorPhone = getIntent().getStringExtra("mentorPhone");
        mentorEmail = getIntent().getStringExtra("mentorEmail");
        mTermId = getIntent().getIntExtra("termID", -1);

        id2 = mTermId;

        if (mCourseId == -1) mCourseId = DetailedAssessmentViewActivity.id3;

        courseScheduleRepository = new CouseScheduleRepository(getApplication());
        List<CourseEntity> allCourses = courseScheduleRepository.getAllCourses();

        for (CourseEntity p:allCourses) {
            if (p.getCourseID() == mCourseId) currentCourse = p;
        }
        mEditName = findViewById(R.id.courseTitle);
        mEditStartDate = findViewById(R.id.startDateDynamicCourse);
        mEditEndDate = findViewById(R.id.endDateDynamicCourse);
        mEditStatus = findViewById(R.id.statusDynamicCourse);
        mEditMentor = findViewById(R.id.mentorName);
        mEditMentorPhone = findViewById(R.id.mentorPhone);
        mEditMentorEmail = findViewById(R.id.mentorEmail);

        if (currentCourse != null) {
            courseName = currentCourse.getCourseName();
            startDate = currentCourse.getStartDate().toString();
            endDate = currentCourse.getEndDate().toString();
            courseStatus = currentCourse.getCourseStatus();
            mentor = currentCourse.getCourseMentor();
            mentorPhone = currentCourse.getCourseMentorPhone();
            mentorEmail = currentCourse.getCourseMentorEmail();
        }
        if(mCourseId != -1) {
            mEditName.setText(courseName);
            mEditStartDate.setText(startDate);
            mEditEndDate.setText(endDate);
            mEditStatus.setText(courseStatus);
            mEditMentor.setText(mentor);
            mEditMentorPhone.setText(mentorPhone);
            mEditMentorEmail.setText(mentorEmail);
        }

        courseScheduleRepository = new CouseScheduleRepository(getApplication());
        courseScheduleRepository.getAllAssessments();// this is really just to set up the database if there isn't one on your device yet-otherwise synch errors later
        RecyclerView recyclerView = findViewById(R.id.listOfAssessments);

        final AssessmentAdapter adapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<AssessmentEntity> filteredAssessments = new ArrayList<>();
        for(AssessmentEntity c:courseScheduleRepository.getAllAssessments()){
            if(c.getAssessmentID()==mCourseId)filteredAssessments.add(c);
        }
        adapter.setWords(filteredAssessments);
    }

    public void addAssessmentPlusButton(View view) {
        Intent intent = new Intent(DetailedCourseViewActivity.this, DetailedAssessmentViewActivity.class);
        intent.putExtra("courseId", mCourseId);
        startActivity(intent);
    }

    public void addCourseFromScreen(View view) {
        CourseEntity c;

        if(mCourseId != -1)
            c = new CourseEntity(mCourseId, mTermId, mEditName.getText().toString(), LocalDate.parse(mEditStartDate.getText().toString()), LocalDate.parse(mEditEndDate.getText().toString()), mEditMentor.getText().toString(), mEditMentorPhone.getText().toString(), mEditMentorEmail.getText().toString(), mEditStatus.getText().toString());
        else {
            List<CourseEntity> allCourses = courseScheduleRepository.getAllCourses();
            if(allCourses.isEmpty())
                mCourseId = 0;
            else
                mCourseId = allCourses.get(allCourses.size()-1).getCourseID();
            c = new CourseEntity(++mCourseId, mTermId, mEditName.getText().toString(), LocalDate.parse(mEditStartDate.getText().toString()), LocalDate.parse(mEditEndDate.getText().toString()), mEditMentor.getText().toString(), mEditMentorPhone.getText().toString(), mEditMentorEmail.getText().toString(), mEditStatus.getText().toString());
        }
        courseScheduleRepository.insert(c);

        Intent intent = new Intent(DetailedCourseViewActivity.this, CourseActivity.class);

        startActivity(intent);
    }
    public void noteDetail(View view) {
    }
}