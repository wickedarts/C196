package com.example.coursescheduler.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class NoteEntity {
    @PrimaryKey
    private int noteID;
    private String noteName;
    private String noteText;

    @Override
    public String toString() {
        return "NoteEntity{" +
                "noteID" + noteID +
                "noteName" + noteName +
                "noteText" + noteText +
                '}';
    }

    public NoteEntity(int noteID, String noteName, String noteText) {
        this.noteID = noteID;
        this.noteName = noteName;
        this.noteText = noteText;
    }

    public int getNoteID() { return noteID; }

    public void setNoteID(int noteID) { this.noteID = noteID; }

    public String getNoteName() { return noteName; }

    public void setNoteName(String noteName) { this.noteName = noteName; }

    public String getNoteText() { return noteText; }

    public void setNoteText(String noteText) { this.noteText = noteText; }
}
