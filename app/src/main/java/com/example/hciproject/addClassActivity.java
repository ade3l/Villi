package com.example.hciproject;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.hciproject.data.DataSource;
import com.example.hciproject.databinding.ActivityAddClassBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Objects;

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
        timePickerInit();
        formCTAbuttonsinit();

    }

    void setUpToolBar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add a class");
        toolbar.setNavigationOnClickListener(view -> {
//                  TODO: Confirmation message whether to leave and lose all data
                    finish();
                }
        );
    }

    private void subjectPickerInit() {
        ArrayAdapter adapter=new ArrayAdapter(this,R.layout.list_subject, DataSource.getSubjects());
        binding.SubjectautoCompleteListView.setAdapter(adapter);
        binding.addSubject.setOnClickListener(view -> createAddSubjectDialog());
        binding.SubjectautoCompleteListView.setOnItemClickListener((adapterView, view, i, l) -> {
            InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
            binding.daysAutoCompleteListView.requestFocus();
        });
        binding.SubjectautoCompleteListView.setOnKeyListener((view, i, keyEvent) -> {
            if(keyEvent.getKeyCode()==KeyEvent.KEYCODE_ENTER){
                InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
                binding.daysAutoCompleteListView.requestFocus();
                return true;
            }
            return false;
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
    private void timePickerInit() {
        binding.startTime.setShowSoftInputOnFocus(false);
        binding.endTime.setShowSoftInputOnFocus(false);
        binding.startTime.setOnClickListener(view -> createTimePicker(binding.startTime,"Select start time"));
        binding.endTime.setOnClickListener(view -> createTimePicker(binding.endTime,"Select end time"));
    }
    private void createTimePicker(EditText editText, String title){
        int hour=12, minute=10;
        if(editText.getText()!=null && !editText.getText().toString().equals("") ){
            hour=Integer.parseInt(editText.getText().toString().split(":")[0]);
            minute=Integer.parseInt(editText.getText().toString().split(":")[1]);
        }
        MaterialTimePicker picker =new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(hour)
                .setMinute(minute)
                .setTitleText(title)
                .build();
        picker.addOnPositiveButtonClickListener(view -> editText.setText(String.valueOf(picker.getHour())+":"+String.valueOf(picker.getMinute())));
        picker.show(getSupportFragmentManager(),"tag");
    }
    private void formCTAbuttonsinit() {
        binding.cancel.setOnClickListener(view -> cancel());
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
    void cancel(){
        if(
                binding.SubjectautoCompleteListView.getText().toString().equals("")
                && binding.daysAutoCompleteListView.getText().toString().equals("")
                && binding.startTime.getText().toString().equals("")
                && binding.endTime.getText().toString().equals("")
        ){
            finish();
        }
        else{
            new MaterialAlertDialogBuilder(this,R.style.cancelDialogTheme)
                    .setTitle("Delete this draft?")
                    .setMessage("You will lose the class data that you have filled. \n\nNewly created subjects will not be deleted and can be deleted by visiting My Subjects")
                    .setPositiveButton("Cancel", null)
                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .show();
        }
    }
}