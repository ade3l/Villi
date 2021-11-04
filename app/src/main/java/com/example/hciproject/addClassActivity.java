package com.example.hciproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListAdapter;

import com.example.hciproject.data.DataSource;
import com.example.hciproject.databinding.ActivityAddClassBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class addClassActivity extends AppCompatActivity {
    private ActivityAddClassBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddClassBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        setUpToolBar();
        subjectPickerInit();
        dayPickerInit();

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
    private void subjectPickerInit() {
        ArrayAdapter adapter=new ArrayAdapter(this,R.layout.list_subject, DataSource.getSubjects());
        binding.SubjectautoCompleteListView.setAdapter(adapter);
        binding.addSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAddSubjectDialog();
            }
        });
        binding.SubjectautoCompleteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
                binding.daysAutoCompleteListView.requestFocus();
            }
        });
        binding.SubjectautoCompleteListView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getKeyCode()==KeyEvent.KEYCODE_ENTER){
                    InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
                    binding.daysAutoCompleteListView.requestFocus();
                    return true;
                }
                return false;
            }
        });
        binding.SubjectautoCompleteListView.setOnFocusChangeListener(focusChangeListener);
    }
    void createAddSubjectDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.dialogTheme);
        builder.setTitle("Enter subject details");
        View inflatedView= LayoutInflater.from(this).inflate(R.layout.dialog_add_class,(ViewGroup) findViewById(android.R.id.content),false);
        builder.setView(inflatedView);
        builder.setPositiveButton("Add", null);
        builder.setNegativeButton("Cancel",null);
        AlertDialog dialog=builder.create();
        dialog.show();
    }
    private void dayPickerInit() {
        AutoCompleteTextView subjectTV= binding.daysAutoCompleteListView;
        ArrayAdapter adapter=new ArrayAdapter(this,R.layout.list_subject, DataSource.getDays());
        subjectTV.setAdapter(adapter);
    }
    View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if(!binding.SubjectautoCompleteListView.hasFocus()){
                InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
//                binding.SubjectautoCompleteListView.setOnFocusChangeListener(null);
            }
        }
    };
}