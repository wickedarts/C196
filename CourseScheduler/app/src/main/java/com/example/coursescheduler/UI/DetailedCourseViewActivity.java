package com.example.coursescheduler.UI;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursescheduler.Database.CouseScheduleRepository;
import com.example.coursescheduler.Entity.AssessmentEntity;
import com.example.coursescheduler.Entity.CourseEntity;
import com.example.coursescheduler.Entity.NoteEntity;
import com.example.coursescheduler.R;
import com.example.coursescheduler.UI.Adapters.AssessmentAdapter;
import com.example.coursescheduler.ViewModel.MyReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailedCourseViewActivity extends AppCompatActivity {
    private CouseScheduleRepository courseScheduleRepository;
    private Spinner courseStatusSpinner;
    public static int numAlert;

    static int id2;

    int mCourseId;
    int mCourseTermID;
    int mNoteId;

    String courseName;
    String startDate;
    String endDate;
    String courseStatus;
    String mentor;
    String mentorPhone;
    String mentorEmail;
    CalendarView picker;
    TextView mills;
    TextView mCourseTermIDHidden;

    EditText mEditName;
    EditText mEditStartDate;
    EditText mEditEndDate;
    Spinner mEditStatus;
    EditText mEditMentor;
    EditText mEditMentorPhone;
    EditText mEditMentorEmail;
    int courseStatusSelectionPosition;

    Long dateStart;
    Long dateEnd;

    LocalDate currentDate = LocalDate.now();

    CourseEntity currentCourse;
    AssessmentEntity currentAssessment;
    NoteEntity currentNote;

    public static int numAssessments;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_course_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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

        //Type Spinner setup
        courseStatusSpinner = findViewById(R.id.statusSpinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseStatusSpinner.setAdapter(adapter2);
        courseStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                courseStatus = courseStatusSpinner.getSelectedItem().toString();
                courseStatusSelectionPosition = courseStatusSpinner.getSelectedItemPosition();
                Toast.makeText(DetailedCourseViewActivity.this, courseStatus, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                courseStatus = "Plan To Take";
                Toast.makeText(DetailedCourseViewActivity.this, "Defaulting to Plan To Take", Toast.LENGTH_LONG).show();
            }
        });

        mCourseId = getIntent().getIntExtra("courseID", -1);
//        if (mCourseId == -1) mCourseId = DetailedAssessmentViewActivity.id3;
        mCourseTermID = getIntent().getIntExtra("termID", -1);
        courseName = getIntent().getStringExtra("courseName");
        startDate = getIntent().getStringExtra("courseStartDate");
        endDate = getIntent().getStringExtra("courseEndDate");
        courseStatus = getIntent().getStringExtra("courseStatus");
        courseStatusSelectionPosition = getIntent().getIntExtra("courseStatusSelectionPosition", -1);
        mentor = getIntent().getStringExtra("courseMentorName");
        mentorPhone = getIntent().getStringExtra("courseMentorPhone");
        mentorEmail = getIntent().getStringExtra("courseMentorEmail");

        id2 = mCourseTermID;

        courseScheduleRepository = new CouseScheduleRepository(getApplication());
        List<CourseEntity> allCourses = courseScheduleRepository.getAllCourses();

        for (CourseEntity p:allCourses)
            if (p.getCourseID() == mCourseId) currentCourse = p;

        mEditName = findViewById(R.id.courseTitle);
        mEditStartDate = findViewById(R.id.startDateDynamicCourse);
        mEditEndDate = findViewById(R.id.endDateDynamicCourse);
        mEditStatus = findViewById(R.id.statusSpinner);
        mEditMentor = findViewById(R.id.mentorName);
        mEditMentorPhone = findViewById(R.id.mentorPhone);
        mEditMentorEmail = findViewById(R.id.mentorEmail);
        mCourseTermIDHidden = findViewById(R.id.courseTermIDHidden);

        if(mCourseId != -1) {
            mEditName.setText(courseName);
            mEditStartDate.setText(startDate);
            mEditEndDate.setText(endDate);
            mEditStatus.setSelection(courseStatusSelectionPosition);
            mEditMentor.setText(mentor);
            mEditMentorPhone.setText(mentorPhone);
            mEditMentorEmail.setText(mentorEmail);
            mCourseTermIDHidden.setText(Integer.toString(mCourseTermID));
        }

        courseScheduleRepository = new CouseScheduleRepository(getApplication());
        courseScheduleRepository.getAllAssessments();// this is really just to set up the database if there isn't one on your device yet-otherwise synch errors later
        RecyclerView recyclerView = findViewById(R.id.listOfAssessments);

        final AssessmentAdapter adapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<AssessmentEntity> filteredAssessments = new ArrayList<>();
        for(AssessmentEntity c:courseScheduleRepository.getAllAssessments()){
            if(c.getAssessmentCourseID()==mCourseId)filteredAssessments.add(c);
        }
        adapter.setWords(filteredAssessments);

        numAssessments = filteredAssessments.size();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_course, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.SaveCourse:
                addCourseFromScreen();
                return true;
            case R.id.DeleteCourse://Deletes the course and all of its assessments and its note
                courseScheduleRepository = new CouseScheduleRepository(getApplication());
                courseScheduleRepository.delete(currentCourse);
                for(AssessmentEntity a:courseScheduleRepository.getAllAssessments())
                    if(a.getAssessmentCourseID() == mCourseId)
                        courseScheduleRepository.delete(a);
                for(NoteEntity n:courseScheduleRepository.getAllNotes())
                    if(n.getNoteCourseID() == mCourseId)
                        courseScheduleRepository.delete(n);
                this.finish();
                return true;
            case R.id.CourseNote:
                goToNote();
                return true;
            case R.id.CourseStartNotification:
                Intent intent2=new Intent(DetailedCourseViewActivity.this, MyReceiver.class);
                intent2.putExtra("key", "Your course: " + mEditName.getText().toString() + " starts today. Contact your course instructor: " + mEditMentor.getText().toString() + " via email at: " + mEditMentorEmail.getText().toString() + " with any questions about getting started.");
                PendingIntent sender= PendingIntent.getBroadcast(DetailedCourseViewActivity.this,++numAlert,intent2,0);
                AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                String dateFromScreen=mEditStartDate.getText().toString();
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                Date myDate=null;
                try {
                    myDate=sdf.parse(dateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dateStart=myDate.getTime();
                alarmManager.set(AlarmManager.RTC_WAKEUP, dateStart, sender);
                return true;
            case R.id.CourseEndNotification:
                Intent intent3=new Intent(DetailedCourseViewActivity.this, MyReceiver.class);
                intent3.putExtra("key","Your course: " + mEditName.getText().toString() + " ends today. Contact your course instructor: " + mEditMentor.getText().toString() + "via email at: " + mEditMentorEmail.getText().toString() + "with any questions about finishing this course.");
                PendingIntent sender2= PendingIntent.getBroadcast(DetailedCourseViewActivity.this,++numAlert,intent3,0);
                AlarmManager alarmManager2=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                String dateFromScreen2=mEditEndDate.getText().toString();
                String myFormat2 = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf2 = new SimpleDateFormat(myFormat2, Locale.US);
                Date myDate2=null;
                try {
                    myDate2=sdf2.parse(dateFromScreen2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dateEnd=myDate2.getTime();
                alarmManager2.set(AlarmManager.RTC_WAKEUP, dateEnd, sender2);
                return true;
            case R.id.ReloadAssessments:
                refreshList();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addAssessmentPlusButton(View view) {
        Intent intent = new Intent(DetailedCourseViewActivity.this, DetailedAssessmentViewActivity.class);
        intent.putExtra("courseID", mCourseId);
        startActivity(intent);
    }

    public void goToNote() {
        Intent intent = new Intent(DetailedCourseViewActivity.this, DetailedNoteViewActivity.class);
        intent.putExtra("courseID", mCourseId);
        mNoteId = mCourseId;
        intent.putExtra("noteID", mNoteId);
        startActivity(intent);
    }

    public static class Example{
        public static boolean validateJavaDate(String strDate)
        {
            /* Check if date is 'null' */
            if (strDate.trim().equals(""))
            {
                return false;
            }
            /* Date is not 'null' */
            else
            {
                /*
                 * Set preferred date format,
                 * For example MM-dd-yyyy, MM.dd.yyyy,dd.MM.yyyy etc.*/
                SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd");
                sdfrmt.setLenient(false);
                /* Create Date object
                 * parse the string into date
                 */
                try
                {
                    Date javaDate = sdfrmt.parse(strDate);
                    System.out.println(strDate+" is valid date format");
                }
                /* Date format is invalid */
                catch (ParseException e)
                {
                    System.out.println(strDate+" is Invalid Date format");
                    return false;
                }
                /* Return true if date format is valid */
                return true;
            }
        }
    }

    public void addCourseFromScreen() {
        if(Example.validateJavaDate(mEditStartDate.getText().toString()) == false || Example.validateJavaDate(mEditEndDate.getText().toString()) == false)
            Toast.makeText(getApplicationContext(), "Set Valid Dates (YYYY-MM-DD)", Toast.LENGTH_SHORT).show();
        else {
            CourseEntity c;
            List<CourseEntity> allCourses = courseScheduleRepository.getAllCourses();
            if (mCourseId == -1) {
                if (allCourses.isEmpty())
                    mCourseId = 0;
                else
                    mCourseId = allCourses.get(allCourses.size() - 1).getCourseID();
                c = new CourseEntity(++mCourseId, mCourseTermID, mEditName.getText().toString(), LocalDate.parse(mEditStartDate.getText().toString()), LocalDate.parse(mEditEndDate.getText().toString()), mEditMentor.getText().toString(), mEditMentorPhone.getText().toString(), mEditMentorEmail.getText().toString(), mEditStatus.getSelectedItem().toString(), courseStatusSelectionPosition);
                courseScheduleRepository.insert(c);
            } else {
                c = new CourseEntity(mCourseId, mCourseTermID, mEditName.getText().toString(), LocalDate.parse(mEditStartDate.getText().toString()), LocalDate.parse(mEditEndDate.getText().toString()), mEditMentor.getText().toString(), mEditMentorPhone.getText().toString(), mEditMentorEmail.getText().toString(), mEditStatus.getSelectedItem().toString(), courseStatusSelectionPosition);
                courseScheduleRepository.update(c);
            }

            Toast.makeText(getApplicationContext(), "Course saved", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(DetailedCourseViewActivity.this, CourseActivity.class);
            startActivity(intent);
        }
    }

    private void refreshList(){
        RecyclerView recyclerView = findViewById(R.id.listOfAssessments);
        final AssessmentAdapter adapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<AssessmentEntity> filteredAssessments = new ArrayList<>();
        for(AssessmentEntity c:courseScheduleRepository.getAllAssessments()){
            if(c.getAssessmentCourseID()==mCourseId)filteredAssessments.add(c);
        }
        adapter.setWords(filteredAssessments);
    }
}