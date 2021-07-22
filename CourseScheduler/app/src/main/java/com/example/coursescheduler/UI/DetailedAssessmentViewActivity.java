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

import com.example.coursescheduler.Database.CouseScheduleRepository;
import com.example.coursescheduler.Entity.AssessmentEntity;
import com.example.coursescheduler.R;
import com.example.coursescheduler.ViewModel.MyReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailedAssessmentViewActivity extends AppCompatActivity {
    private CouseScheduleRepository courseScheduleRepository;
    private DetailedCourseViewActivity detailedCourseViewActivity;
    private Spinner assessmentTypeSpinner;

    static int id3;

    int mAssessmentId;
    int mCourseId;

    String assessmentName;
    String startDate;
    String endDate;
    String assessmentType;
    String assessmentDescription;
    CalendarView picker;
    TextView mills;

    EditText mEditAssessmentName;
    EditText mEditStartDateDynamic;
    EditText mEditEndDateDynamic;
    Spinner mEditAssessmentType;
    int assessmentSelectionPosition;
    EditText mEditAssessmentDescription;

    Long dateStart;
    Long dateEnd;

    LocalDate currentDate = LocalDate.now();

    AssessmentEntity currentAssessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_assessment_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mills = findViewById(R.id.dateInMillsAssessmentHIDE);//Hidden text view (Hide after testing)
        picker = (CalendarView) findViewById(R.id.calendarViewAssessment);
        picker.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(year, month, dayOfMonth, 0, 0);
                Long dateLong = c.getTimeInMillis() / 1000;//epoch in seconds
                Long dateLongPlus24Hours = dateLong + 86400;//start date plus 24 Hours
                mills.setText(Long.toString(dateLong));
                mEditStartDateDynamic.setText(String.format(Instant.ofEpochSecond(dateLong).atZone(ZoneId.systemDefault()).toLocalDate().toString()));
                mEditEndDateDynamic.setText(String.format(Instant.ofEpochSecond(dateLongPlus24Hours).atZone(ZoneId.systemDefault()).toLocalDate().toString()));

                //Use this spcae below to track the start and end of an assessment for ALERTS:
//                startDate = LocalDate.parse(mEditStartDateDynamic.getText());
//                endDate = LocalDate.parse(mEditEndDateDynamic.getText());
//                if((startDate.isEqual(currentDate) || startDate.isBefore(currentDate)) && (endDate.isAfter(currentDate) || endDate.isEqual(currentDate))){
//                    mIsCurrentTerm.setVisibility(View.VISIBLE);
//                }
//                else{
//                    mIsCurrentTerm.setVisibility(View.INVISIBLE);
//                }
            }
        });

        //Type Spinner setup
        assessmentTypeSpinner = findViewById(R.id.spinnerAssessment);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assessmentTypeSpinner.setAdapter(adapter);
        assessmentTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                assessmentType = assessmentTypeSpinner.getSelectedItem().toString();
                assessmentSelectionPosition = assessmentTypeSpinner.getSelectedItemPosition();
                Toast.makeText(DetailedAssessmentViewActivity.this, assessmentType, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                assessmentType = "Performance";
                Toast.makeText(DetailedAssessmentViewActivity.this, "Defaulting to Performance Assessment", Toast.LENGTH_LONG).show();
            }
        });

        mAssessmentId = getIntent().getIntExtra("assessmentID", -1);
        mCourseId = getIntent().getIntExtra("courseID", -1);
        assessmentName = getIntent().getStringExtra("assessmentName");
        startDate = getIntent().getStringExtra("assessmentStartDate");
        endDate = getIntent().getStringExtra("assessmentEndDate");
        assessmentType = getIntent().getStringExtra("assessmentType");
        assessmentDescription = getIntent().getStringExtra("descriptionText");
        assessmentSelectionPosition = getIntent().getIntExtra("assessmentSelectionPosition", -1);

        id3 = mCourseId;

        courseScheduleRepository = new CouseScheduleRepository(getApplication());
        List<AssessmentEntity> allAssessments = courseScheduleRepository.getAllAssessments();

        for (AssessmentEntity a:allAssessments) {
            if (a.getAssessmentID() == mAssessmentId) currentAssessment = a;
        }

        mEditAssessmentName = findViewById(R.id.assessmentTitle);
        mEditStartDateDynamic = findViewById(R.id.startDateDynamicAssessment);
        mEditEndDateDynamic = findViewById(R.id.endDateDynamicAssessment);
        mEditAssessmentType = findViewById(R.id.spinnerAssessment);
        mEditAssessmentDescription = findViewById(R.id.assessmentDescription);

        if (mAssessmentId != -1) {
            mEditAssessmentName.setText(assessmentName);
            mEditStartDateDynamic.setText(startDate);
            mEditEndDateDynamic.setText(endDate);
            mEditAssessmentType.setSelection(assessmentSelectionPosition);
            mEditAssessmentDescription.setText(assessmentDescription);
        }

        courseScheduleRepository = new CouseScheduleRepository(getApplication());
        courseScheduleRepository.getAllAssessments();// this is really just to set up the database if there isn't one on your device yet-otherwise synch errors later
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_assessment, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.SaveAssessment:
                addAssessmentFromScreen();
                Toast.makeText(getApplicationContext(), "Assessment saved", Toast.LENGTH_LONG).show();
                return true;
            case R.id.DeleteAssessment:
                courseScheduleRepository.delete(currentAssessment);
                Toast.makeText(getApplicationContext(), "Assessment deleted", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(DetailedAssessmentViewActivity.this, DetailedCourseViewActivity.class);
                startActivity(intent);
                return true;
            case R.id.AssessmentStartNotification:
                Intent intent0=new Intent(DetailedAssessmentViewActivity.this, MyReceiver.class);
                intent0.putExtra("key","Your assessment: " + mEditAssessmentName.getText().toString() + " starts today.");
                PendingIntent sender= PendingIntent.getBroadcast(DetailedAssessmentViewActivity.this,++DetailedCourseViewActivity.numAlert,intent0,0);
                AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                String dateFromScreen=mEditStartDateDynamic.getText().toString();
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
            case R.id.AssessmentEndNotification:
                Intent intent1=new Intent(DetailedAssessmentViewActivity.this, MyReceiver.class);
                intent1.putExtra("key","Your assessment: " + mEditAssessmentName.getText().toString() + " ends today.");
                PendingIntent sender2= PendingIntent.getBroadcast(DetailedAssessmentViewActivity.this,++DetailedCourseViewActivity.numAlert,intent1,0);
                AlarmManager alarmManager2=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                String dateFromScreen2=mEditEndDateDynamic.getText().toString();
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
        }
        return super.onOptionsItemSelected(item);
    }

    public void addAssessmentFromScreen() {
        AssessmentEntity a;

        List<AssessmentEntity> allAssessments = courseScheduleRepository.getAllAssessments();

        if(mAssessmentId == -1) {
            if(allAssessments.isEmpty())
                mAssessmentId = 0;
            else
                mAssessmentId = allAssessments.get(allAssessments.size()-1).getAssessmentID();
            a = new AssessmentEntity(++mAssessmentId, mCourseId, mEditAssessmentName.getText().toString(), LocalDate.parse(mEditStartDateDynamic.getText().toString()), LocalDate.parse(mEditEndDateDynamic.getText().toString()), mEditAssessmentType.getSelectedItem().toString(), assessmentSelectionPosition, mEditAssessmentDescription.getText().toString());
            courseScheduleRepository.insert(a);
        }
        else {
            a = new AssessmentEntity(mAssessmentId, mCourseId, mEditAssessmentName.getText().toString(), LocalDate.parse(mEditStartDateDynamic.getText().toString()), LocalDate.parse(mEditEndDateDynamic.getText().toString()), mEditAssessmentType.getSelectedItem().toString(), assessmentSelectionPosition, mEditAssessmentDescription.getText().toString());
            courseScheduleRepository.update(a);
        }

//        Intent intent = new Intent(DetailedAssessmentViewActivity.this, DetailedCourseViewActivity.class);
//        intent.putExtra("courseID", mCourseId);
//        startActivity(intent);
        this.finish();
    }
}