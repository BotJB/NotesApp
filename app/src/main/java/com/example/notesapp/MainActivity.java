package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private NoteViewModel noteViewModel;
    public static final int resultcodecheck = 1;
    public static final int editcodecheck = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = findViewById(R.id.fab_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEdit_Note.class);
                startActivityForResult(intent, resultcodecheck);

            }
        });
        RecyclerView recyclerView = findViewById(R.id.rv_Main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final NoteAdapter noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);
        noteViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(NoteViewModel.class);
        noteViewModel.getAllnotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteAdapter.submitList(notes);
            }
        });
     ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
         @Override
         public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
             return false;
         }

         @Override
         public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
             noteViewModel.Delete(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
             Toast.makeText(MainActivity.this,"Note Deleted",Toast.LENGTH_LONG).show();

         }
     });
     itemTouchHelper.attachToRecyclerView(recyclerView);
     noteAdapter.setONItemClickListner(new NoteAdapter.onItemClickListner() {
         @Override
         public void onItemclick(Note note) {
             Intent intent=new Intent(MainActivity.this, AddEdit_Note.class);
             intent.putExtra(AddEdit_Note.Extra_Id,note.getId());
             intent.putExtra(AddEdit_Note.Extra_title,note.getSubject());
             intent.putExtra(AddEdit_Note.Extra_description,note.getMessage());
             intent.putExtra(AddEdit_Note.Extra_priority,note.getPriority());
             startActivityForResult(intent,editcodecheck);

         }
     });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == resultcodecheck && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEdit_Note.Extra_title);
            String des = data.getStringExtra(AddEdit_Note.Extra_description);
            int pri = data.getIntExtra(AddEdit_Note.Extra_priority, 1);
            Note note = new Note(title, des, pri);
            noteViewModel.Insert(note);
            Toast.makeText(this, "New Note added", Toast.LENGTH_LONG).show();
        } else if(requestCode == editcodecheck && resultCode == RESULT_OK){
            int id=data.getIntExtra(AddEdit_Note.Extra_Id,-1);
            if(id==-1){
                Toast.makeText(this, "Note cannot be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(AddEdit_Note.Extra_title);
            String des = data.getStringExtra(AddEdit_Note.Extra_description);
            int pri = data.getIntExtra(AddEdit_Note.Extra_priority, 1);
            Note note=new Note(title,des,pri);
            note.setId(id);
            noteViewModel.Update(note);
            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Note not added", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=new MenuInflater(MainActivity.this);
        menuInflater.inflate(R.menu.mian1,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_del: noteViewModel.DeleteAll();
            Toast.makeText(MainActivity.this,"All nodes deleted",Toast.LENGTH_LONG).show();
            return true;
            default:return super.onOptionsItemSelected(item);
        }

    }
}