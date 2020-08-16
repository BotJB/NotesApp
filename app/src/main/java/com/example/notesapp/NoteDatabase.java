package com.example.notesapp;

import android.content.Context;
import android.os.AsyncTask;
import android.text.NoCopySpan;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.jetbrains.annotations.NotNull;

@Database(entities = Note.class, version = 1,exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {
    public static NoteDatabase instance;

    public abstract Notedao notedao();

    public synchronized static NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomcallback)
                    .build();
        }
        return instance;

    }
    private static RoomDatabase.Callback roomcallback=new RoomDatabase.Callback(){
        @Override
        public void onCreate (@NotNull SupportSQLiteDatabase db){
            super.onCreate(db);
            new PopulateDatabase(instance).execute();
        }
    };
    private static class PopulateDatabase extends AsyncTask<Void,Void,Void>{
        Notedao notedao;

        public PopulateDatabase(NoteDatabase db) {
            this.notedao = db.notedao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            notedao.insert(new Note("Study","Remember to study Android ",4));
            notedao.insert(new Note("Study","Remember to study java ",3));
            notedao.insert(new Note("Study","Remember to play fortnite ",2));
            return null;
        }
    }



}
