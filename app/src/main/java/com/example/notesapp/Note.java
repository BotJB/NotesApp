package com.example.notesapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_Table")
public class Note {
    @PrimaryKey(autoGenerate =true)
    private int id;
    private String subject;
    private String message;
    private int priority;

    public void setId(int id) {
        this.id = id;
    }

    public Note(String subject, String message, int priority) {
        this.subject = subject;
        this.message = message;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public int getPriority() {
        return priority;
    }
}
