package com.ywarrior.mynotes;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {
    LiveData<List<Notes_model>> notes_models;
    private Executor mExecutor= Executors.newSingleThreadExecutor();
    public static  AppRepository ourInstance;
    public static AppRepository getInstance(Context context){
        return ourInstance=new AppRepository(context);
    }
    AppDatabase appDatabase;
    private AppRepository(Context context){
        appDatabase=AppDatabase.getInstance(context);
        notes_models=getAllNotes();

    }

    public void addData(final List<Notes_model> notes_model) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.notesDao().InertAll(notes_model);
            }
        });

    }
    public void addSingleData(final Notes_model notes_model){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.notesDao().InsertNote(notes_model);
            }
        });
    }
    private LiveData<List<Notes_model>>getAllNotes(){
        return appDatabase.notesDao().getNotesList();
    }
    public void DeleteNote(final Notes_model notes_model) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.notesDao().deleteNote(notes_model);
            }
        });

    }
    public void Updaten(final Notes_model notes_model){
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.notesDao().updateNode(notes_model);
            }
        });
    }
}
