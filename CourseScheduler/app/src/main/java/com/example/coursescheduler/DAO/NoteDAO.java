package com.example.coursescheduler.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.coursescheduler.Entity.NoteEntity;

import java.util.List;

@Dao
public interface NoteDAO {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(NoteEntity note);

    @Query("DELETE FROM note_table")
    void deleteAllNotes();

    @Query("SELECT * FROM note_table ORDER BY noteID ASC")
    List<NoteEntity> getAllNotes();

}


