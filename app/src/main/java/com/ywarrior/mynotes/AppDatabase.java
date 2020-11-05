package com.ywarrior.mynotes;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities={Notes_model.class},version = 2)
@TypeConverters(DataConvertor.class)

public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME="notesdatabase.db";
    public static volatile AppDatabase instance;
    private static final Object LOCK=new Object();
    public  abstract NotesDao notesDao();
    public static  AppDatabase getInstance(Context context){
        if (instance==null){
            synchronized (LOCK){
                if (instance==null){
                    instance= Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,DATABASE_NAME)
                            .addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        }
        return instance;
    }
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE 'notes' ADD COLUMN 'url' TEXT ");

            // Since we didn't alter the table, there's nothing else to do here.
        }
    };

}
