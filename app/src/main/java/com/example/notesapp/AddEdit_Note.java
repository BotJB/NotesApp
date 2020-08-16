package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class AddEdit_Note extends AppCompatActivity {
    public static final String Extra_Id="com.example.notesapp.title.Id";
    public static final String Extra_title=
            "com.example.notesapp.title";
    public static final String Extra_description=
            "com.example.notesapp.description";
    public static final String Extra_priority=
            "com.example.notesapp.priority";
private EditText title;
private EditText description;
private NumberPicker priority;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__note);
        title=findViewById(R.id.et_Title);
        description=findViewById(R.id.et_Description);
        priority=findViewById(R.id.np_Priority);
        priority.setMinValue(1);
        priority.setMaxValue(10);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent=getIntent();
        if(intent.hasExtra(Extra_Id)){
            setTitle("Edit Note");
            title.setText(intent.getStringExtra(Extra_title));
            description.setText(intent.getStringExtra(Extra_description));
            priority.setValue(intent.getIntExtra(Extra_priority,1));
        }else{
setTitle("ADD NOTE");}
    }
    public void saveitem(){
        String ktitle=title.getText().toString();
        String kdescrip=description.getText().toString();
        int kpri=priority.getValue();
   if(ktitle.trim().isEmpty()||kdescrip.trim().isEmpty()){
       Toast.makeText(this,"Title or description cannot be empty",Toast.LENGTH_LONG).show();
   }
   Intent data=new Intent();
   data.putExtra(Extra_title,ktitle);
   data.putExtra(Extra_description,kdescrip);
   data.putExtra(Extra_priority,kpri);
   int id=getIntent().getIntExtra(Extra_Id,-1);
   if(id!=-1){
       data.putExtra(Extra_Id,id);
   }
   setResult(RESULT_OK,data);
   finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case (R.id.item_save):
                saveitem();
                return true;
            default:return super.onOptionsItemSelected(item);
        }

    }
}