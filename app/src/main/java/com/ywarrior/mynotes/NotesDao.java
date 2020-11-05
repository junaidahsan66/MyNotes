package com.ywarrior.mynotes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertNote(Notes_model notes_model);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InertAll(List<Notes_model>list);

    @Delete
    void deleteNote(Notes_model notes_model);

    @Query("SELECT * FROM notes WHERE id= :id")
    Notes_model getNote(int id);

    @Query("SELECT * FROM notes ORDER BY date DESC")
   LiveData<List<Notes_model>>   getNotesList();

    @Query("DELETE FROM notes")
    int deleteAllNotes();

    @Query("SELECT COUNT(*) FROM notes")
    int getCount();




}
