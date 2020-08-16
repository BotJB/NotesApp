package com.example.notesapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private respository respo;
    private LiveData<List<Note>> allnotes;
    public NoteViewModel(Application application) {
        super(application);
        respo=new respository(application);
        allnotes=respo.getAllnotes();
    }
    public void Insert(Note note){
        respo.Insert(note);
    }
    public void Update(Note note){
        respo.update(note);
    }
    public void Delete(Note note){
        respo.Delete(note);
    }
    public void DeleteAll(){
        respo.deleteAllNodes();
    }

    public LiveData<List<Note>> getAllnotes() {
        return allnotes;
    }
}
