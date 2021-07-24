package com.example.coursescheduler.UI;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coursescheduler.DAO.AssessmentDAO;
import com.example.coursescheduler.DAO.CourseDAO;
import com.example.coursescheduler.DAO.NoteDAO;
import com.example.coursescheduler.DAO.TermDAO;
import com.example.coursescheduler.Database.CourseScheduleDatabase;
import com.example.coursescheduler.R;
import com.example.coursescheduler.ViewModel.MyReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.ClearDatabase:

        }
        return super.onOptionsItemSelected(item);
    }

    public void setSupportActionBar(@Nullable androidx.appcompat.widget.Toolbar toolbar) {
        getDelegate().setSupportActionBar(toolbar);
    }

    public void allTermsScreen(View view) {
        Intent intent = new Intent(MainActivity.this, ListOfTermsActivity.class);
        startActivity(intent);
    }
}