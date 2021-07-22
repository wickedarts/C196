package com.example.coursescheduler.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.coursescheduler.Entity.NoteEntity;
import com.example.coursescheduler.Entity.TermEntity;

import java.util.List;

@Dao
public interface NoteDAO {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(NoteEntity note);

    @Delete
    void delete(NoteEntity term);

    @Update
    void update(NoteEntity note);

    @Query("DELETE FROM note_table")
    void deleteAllNotes();

    @Query("SELECT * FROM note_table ORDER BY noteID ASC")
    List<NoteEntity> getAllNotes();

}



