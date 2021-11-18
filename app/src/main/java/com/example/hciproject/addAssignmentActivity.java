package com.example.hciproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.example.hciproject.data.DataSource;
import com.example.hciproject.databinding.ActivityAddAssignmentBinding;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

public class addAssignmentActivity extends AppCompatActivity {
    private ActivityAddAssignmentBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddAssignmentBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        setUpToolBar();
        dateTimePickerInit();
    }

    private void setUpToolBar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add an assignment");
        binding.toolbar.setNavigationOnClickListener(view ->{
            //TODO:Back button
                }
                );
        subjectPickerInit();
    }

    private void subjectPickerInit() {
        ArrayAdapter adapter= new ArrayAdapter(this,R.layout.list_subject, DataSource.getSubjects());
        binding.subjectAutoCompleteListView.setAdapter(adapter);
        binding.addSubject.setOnClickListener(view -> createAddSubjectDialog(adapter));
        binding.subjectAutoCompleteListView.setOnItemClickListener((adapterView, view, i, l) -> {
            binding.nameTextView.requestFocus();
        });
//        TODO: Fix enter key going to date instead of name
//        binding.subjectAutoCompleteListView.setOnKeyListener((view, i, keyEvent) -> {
//            if(keyEvent.getKeyCode()== KeyEvent.KEYCODE_ENTER){
//                InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
//                binding.nameTextView.requestFocus();
//                return true;
//            }
//            return false;
//        });
    }

    void createAddSubjectDialog(ArrayAdapter<?> adapter){
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.dialogTheme);
        builder.setTitle("Enter subject details");
        View inflatedView= LayoutInflater.from(this).inflate(R.layout.dialog_add_class, findViewById(android.R.id.content),false);
        builder.setView(inflatedView);
        builder.setPositiveButton("Add", (dialogInterface, i) -> {

        });
        builder.setNegativeButton("Cancel",null);
        AlertDialog dialog=builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view -> {
            EditText subjectNameTV= inflatedView.findViewById(R.id.subjectNameIp);
            String subjectName=subjectNameTV.getText().toString();
            TextInputLayout subjectNameParent=inflatedView.findViewById(R.id.subjectNameIpLayout);
            if(subjectName.equals("")){
                subjectNameParent.setError("Enter a subject name");
            }
            else{
                DataSource.addSubject(subjectName);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
                binding.subjectAutoCompleteListView.setText(subjectName);
            }
        });
    }

    private void dateTimePickerInit() {
        binding.dueDate.setShowSoftInputOnFocus(false);
        binding.dueTime.setShowSoftInputOnFocus(false);
        binding.dueDate.setOnClickListener(view -> {
            MaterialDatePicker datePicker= MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Pick a date")
                    .build();
            datePicker.addOnPositiveButtonClickListener(selection -> {
                binding.dueDate.setText(DataSource.getDateFromMillis((Long) selection));
                    }
            );
            datePicker.show(getSupportFragmentManager(), "Tag");
        });
        binding.dueTime.setOnClickListener(view ->{
            int hour=23, minute=59;
            if(binding.dueTime.getText()!=null && !binding.dueTime.getText().toString().equals("") ){
                hour=Integer.parseInt(binding.dueTime.getText().toString().split(":")[0]);
                minute=Integer.parseInt(binding.dueTime.getText().toString().split(":")[1]);
            }
            MaterialTimePicker timePicker=new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(hour)
                    .setMinute(minute)
                    .setTitleText("Select due time")
                    .build();
            timePicker.addOnPositiveButtonClickListener(view1 ->
                    binding.dueTime.setText(String.format("%02d:%02d", timePicker.getHour(), timePicker.getMinute()))
            );
            timePicker.show(getSupportFragmentManager(), "tag");
        });
    }

}