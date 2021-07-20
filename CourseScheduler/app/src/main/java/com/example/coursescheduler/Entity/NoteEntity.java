package com.example.coursescheduler.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class NoteEntity {
    @PrimaryKey
    private int noteID;
    private int noteCourseID;
    private String noteText;

    @Override
    public String toString() {
        return "NoteEntity{" +
                "noteID=" + noteID +
                "courseID=" + noteCourseID +
                "noteText=" + noteText +
                '}';
    }

    public NoteEntity(int noteID, int noteCourseID, String noteText) {
        this.noteID = noteID;
        this.noteCourseID = noteCourseID;
        this.noteText = noteText;
    }

    public int getNoteID() { return noteID; }

    public void setNoteID(int noteID) { this.noteID = noteID; }

    public int getNoteCourseID() { return noteCourseID; }

    public void setNoteCourseID(int noteCourseID) {this.noteCourseID = noteCourseID; }

    public String getNoteText() { return noteText; }

    public void setNoteText(String noteText) { this.noteText = noteText; }
}
