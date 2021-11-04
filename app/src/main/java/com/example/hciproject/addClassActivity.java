package com.example.hciproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.hciproject.data.DataSource;

import java.util.ArrayList;
import java.util.List;

public class addClassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        setUpToolBar();

        AutoCompleteTextView at= findViewById(R.id.autoCompleteListView);
        ArrayAdapter adapter=new ArrayAdapter(this,R.layout.list_subject, DataSource.getSubjects());
        at.setAdapter(adapter);

        Button addNewSub= findViewById(R.id.addSubject);
        addNewSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog();
            }
        });
    }

    void setUpToolBar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add a class");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    void createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.dialogTheme);
        builder.setTitle("Enter subject details");
        View inflatedView= LayoutInflater.from(this).inflate(R.layout.dialog_add_class,(ViewGroup) findViewById(android.R.id.content),false);
        builder.setView(inflatedView);
        builder.setPositiveButton("Add", null);
        builder.setNegativeButton("Cancel",null);
        AlertDialog dialog=builder.create();
        dialog.show();
    }
}