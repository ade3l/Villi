package com.example.hciproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hciproject.data.DataSource;
import com.example.hciproject.databinding.ActivityAddAssignmentBinding;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

public class addAssignmentActivity extends AppCompatActivity {
    private ActivityAddAssignmentBinding binding;
    SharedPreferences pref;
    Long dateInMillis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddAssignmentBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        pref=getSharedPreferences("com.example.villi",Context.MODE_PRIVATE);
        setContentView(view);
        setUpToolBar();
        dateTimePickerInit();
        formCTAButtonsInit();
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
                dateInMillis= (Long) selection;
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

    private void formCTAButtonsInit(){
        binding.cancel.setOnClickListener(view -> cancel());
        binding.addAssignment.setOnClickListener(view -> {
            //Get the data from the form
            String subject=binding.subjectAutoCompleteListView.getText().toString();
            String name=binding.nameTextView.getText().toString();
            String dueDate= String.valueOf(dateInMillis);
            String dueTime=binding.dueTime.getText().toString();
            String notes=binding.notes.getText().toString();
            Log.i("mine","Here 1");
            if(validateForm(subject,name,dueDate,dueTime)){
                Log.i("mine","Here 2");
                int assignmentId=pref.getInt("assignment id",1000);
                if(DataSource.addAssignment(String.valueOf(assignmentId),subject,name,dueDate,dueTime,notes)) {
                    pref.edit().putInt("assignment id", assignmentId + 1).apply();
                    Toast.makeText(this, "Assignment added", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Assignment add failed", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }

    private boolean validateForm(String subject, String name, String dueDate, String dueTime) {
        boolean isValid=true;
        if(!DataSource.getSubjects().contains(subject) && !subject.equals("")){
            binding.subjectAutoCompleteListView.setError("Subject not found");
            isValid=false;
        }
        if(name.equals("")){
            binding.nameTextView.setError("Enter a name");
            isValid=false;
        }
        if(dueDate.equals("")){
            binding.dueDate.setError("Enter a due date");
            isValid=false;
        }
        if(dueTime.equals("")){
            binding.dueTime.setError("Enter a due time");
            isValid=false;
        }
        return isValid;
    }

    private void cancel() {
        if(binding.subjectAutoCompleteListView.getText().toString().equals("")
                && binding.nameTextView.getText().toString().equals("")
                && binding.dueDate.getText().toString().equals("")
                && binding.dueTime.getText().toString().equals("")
        ){
            finish();
        }
        else{
            AlertDialog.Builder builder=new AlertDialog.Builder(this,R.style.cancelDialogTheme)
                    .setTitle("Delete this draft?")
                    .setMessage("Are you sure you want to delete this draft?")
                    .setPositiveButton("Cancel", null)
                    .setNegativeButton("Ok", null);
            AlertDialog dialog=builder.create();
            dialog.show();
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(view -> {
                finish();
            });
        }
    }
}