package com.example.coursescheduler.UI;

import android.content.Intent;
import android.os.Bundle;
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

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;

public class DetailedAssessmentViewActivity extends AppCompatActivity {
    private CouseScheduleRepository couseScheduleRepository;
    private Spinner assessmentTypeSpinner;

    static int id3=-1;

    int mAssessmentId;
    int mCourseId;

    String assessmentName;
    LocalDate startDate;
    String assessmentType;
    CalendarView picker;
    TextView mills;

    EditText mEditAssessmentName;
    EditText mEditStartDateDynamic;
    Spinner mEditAssessmentType;


    LocalDate currentDate = LocalDate.now();

    AssessmentEntity currentAssessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_assessment_view);

        mills = findViewById(R.id.dateInMillsCourseHIDE);//Hidden text view (Hide after testing)
        picker = (CalendarView) findViewById(R.id.calendarViewTerm);
        picker.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(year, month, dayOfMonth, 0, 0);
                Long dateLong = c.getTimeInMillis() / 1000;//epoch in seconds
                Long dateLongPlusSixMonths = dateLong + 15778458;//start date plus 6 months at 30.44 days each
                mills.setText(Long.toString(dateLong));
                mEditStartDateDynamic.setText(String.format(Instant.ofEpochSecond(dateLong).atZone(ZoneId.systemDefault()).toLocalDate().toString()));
//                mEditEndDateDynamic.setText(String.format(Instant.ofEpochSecond(dateLongPlusSixMonths).atZone(ZoneId.systemDefault()).toLocalDate().toString()));

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
                String assessmentTag = assessmentTypeSpinner.getSelectedItem().toString();
                Toast.makeText(DetailedAssessmentViewActivity.this, "I got this far", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mAssessmentId = getIntent().getIntExtra("assessmentID", -1);
        mCourseId = getIntent().getIntExtra("courseID", -1);

        id3 = mCourseId;

        mEditAssessmentName = findViewById(R.id.assessmentTitle);
        mEditStartDateDynamic = findViewById(R.id.startDateDynamicAssessment);
        mEditAssessmentType = findViewById(R.id.spinnerAssessment);

        if (currentAssessment != null) {
            assessmentName = currentAssessment.getAssessmentName();
            startDate = currentAssessment.getStartDate();
            assessmentType = currentAssessment.getAssessmentType();
        }

        if (mAssessmentId != -1) {
            mEditAssessmentName.setText(assessmentName);
            mEditStartDateDynamic.setText(startDate.toString());
//            mEditAssessmentType.set???????(assessmentType);//getSpinnerIndex line 270
        }

        couseScheduleRepository = new CouseScheduleRepository(getApplication());
        couseScheduleRepository.getAllAssessments();// this is really just to set up the database if there isn't one on your device yet-otherwise synch errors later
    }

        public void addAssessmentFromScreen(View view) {
        AssessmentEntity a;

        if(mAssessmentId!=-1) {
//            a = new AssessmentEntity(mAssessmentId, mCourseId, mEditAssessmentName.getText().toString(), LocalDate.parse(startDate.toString()), mEditAssessmentType.?????);
        }
        else {
            List<AssessmentEntity> allAssessments = couseScheduleRepository.getAllAssessments();
            if(allAssessments.isEmpty())
                mAssessmentId = 0;
            else
                mAssessmentId = allAssessments.get(allAssessments.size()-1).getAssessmentID();
//            a = new AssessmentEntity(mAssessmentId, mCourseId, mEditAssessmentName.getText().toString(), LocalDate.parse(startDate.toString()), mEditAssessmentType.?????);
        }
//        couseScheduleRepository.insert(a);

        Intent intent = new Intent(DetailedAssessmentViewActivity.this, DetailedCourseViewActivity.class);
        startActivity(intent);
    }
}