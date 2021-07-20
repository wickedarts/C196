package com.example.coursescheduler.UI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coursescheduler.Database.CouseScheduleRepository;
import com.example.coursescheduler.Entity.AssessmentEntity;
import com.example.coursescheduler.Entity.NoteEntity;
import com.example.coursescheduler.R;

import java.util.List;

public class DetailedNoteViewActivity extends AppCompatActivity {
    private CouseScheduleRepository couseScheduleRepository;
    int mNoteId;
    int mCourseId;

    String noteText;

    EditText mNoteText;

    NoteEntity currentNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_note_view);

        mNoteId = getIntent().getIntExtra("noteID", -1);
        mCourseId = getIntent().getIntExtra("courseID", -1);
        noteText = getIntent().getStringExtra("noteText");

    couseScheduleRepository = new CouseScheduleRepository(getApplication());
    List<NoteEntity> allNotes = couseScheduleRepository.getAllNotes();

    for (NoteEntity n:allNotes) {
        if (n.getNoteID() == mNoteId) currentNote = n;
    }

    mNoteText = findViewById(R.id.editTextNote);

    if (currentNote != null) {
        noteText = currentNote.getNoteText();
    }

    if (mNoteId != -1){
        mNoteText.setText(noteText);
    }

    couseScheduleRepository = new CouseScheduleRepository(getApplication());
    couseScheduleRepository.getAllNotes();// this is really just to set up the database if there isn't one on your device yet-otherwise synch errors later
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addNoteFromScreen(View view) {
        NoteEntity n;

        if(mNoteId!=-1) {
            n = new NoteEntity(mNoteId, mCourseId, mNoteText.getText().toString());
        }
        else {
            List<NoteEntity> allNotes = couseScheduleRepository.getAllNotes();

            mNoteId = allNotes.get(allNotes.size()-1).getNoteID();
            n = new NoteEntity(mNoteId, mCourseId, mNoteText.getText().toString());
        }

        couseScheduleRepository.insert(n);

        this.finish();
    }
}