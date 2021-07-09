package com.example.coursescheduler.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coursescheduler.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void setSupportActionBar(@Nullable androidx.appcompat.widget.Toolbar toolbar) {
        getDelegate().setSupportActionBar(toolbar);
    }

    public void allTermsScreen(View view) {
        Intent intent = new Intent(MainActivity.this, ListOfTermsActivity.class);
        startActivity(intent);
    }
}