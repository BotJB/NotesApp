package com.example.notesapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class respository {
    private Notedao notedao;
    private LiveData<List<Note>> allnotes;
    respository(Application application){
        NoteDatabase noteDatabase=NoteDatabase.getInstance( application);
        notedao=noteDatabase.notedao();
        allnotes=notedao.getallnodes();

    }
    public void Insert(Note note){
new InsertTask(notedao).execute(note);
    }
    public void Delete(Note note){
        new DeleteTask(notedao).execute(note);
    }
    public void update(Note note){
new UpdateTask(notedao).execute(note);
    }
    public void deleteAllNodes(){
        new DeleteallTask(notedao).execute();
    }
    public LiveData<List<Note>> getAllnotes(){
        return allnotes;
    }
    private static class InsertTask extends AsyncTask<Note,Void,Void>{
private Notedao notedao;

        public InsertTask(Notedao notedao) {
            this.notedao = notedao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            notedao.insert(notes[0]);
            return null;
        }
    }
    private static class DeleteTask extends AsyncTask<Note,Void,Void>{
        Notedao notedao;

        public DeleteTask(Notedao notedao) {
            this.notedao = notedao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            notedao.delete(notes[0]);
            return null;
        }
    }
    private static class UpdateTask extends AsyncTask<Note,Void,Void>{
        Notedao notedao;

        public UpdateTask(Notedao notedao) {
            this.notedao = notedao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            notedao.update(notes[0]);
            return null;
        }
    }
    private static class DeleteallTask extends AsyncTask<Void,Void,Void>{


        Notedao notedao;

        public DeleteallTask(Notedao notedao) {
            this.notedao = notedao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            notedao.deleteall();
            return null;
        }


    }
}
