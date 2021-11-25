package com.example.hciproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hciproject.data.DataSource;
import com.example.hciproject.databinding.ActivityEditAssignmentBinding;
import com.example.hciproject.objects.Assignment;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

public class editAssignmentActivity extends AppCompatActivity {
    ActivityEditAssignmentBinding binding;
    View view;
    Assignment assignment;
    Long dateInMillis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditAssignmentBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        assignment=new Assignment(getIntent().getStringExtra("subject"),getIntent().getStringExtra("name"),getIntent().getStringExtra("due date"), getIntent().getStringExtra("due time"),
                getIntent().getStringExtra("description"),getIntent().getStringExtra("id"),getIntent().getStringExtra("submitted date"),getIntent().getStringExtra("submitted time"),getIntent().getBooleanExtra("completed",false));
        setUpToolBar();
        initViews();
        dateTimePickerInit();
        formCTAButtonsInit();
    }

    private void setUpToolBar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit assignment");
        binding.toolbar.setNavigationOnClickListener(view ->{
                    cancel();
                }
        );
        subjectPickerInit();
    }

    private void initViews() {
        binding.nameTextView.setText(assignment.getName());
        binding.notes.setText(assignment.getDescription());
        binding.dueDate.setText(DataSource.getDateFromMillis(Long.parseLong(assignment.getDueDate())));
        binding.dueTime.setText(assignment.getDueTime());
        binding.subjectAutoCompleteListView.setText(assignment.getSubject());
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

    private void dateTimePickerInit() {
        binding.dueDate.setShowSoftInputOnFocus(false);
        binding.dueTime.setShowSoftInputOnFocus(false);
        binding.dueDate.setOnClickListener(view -> {
            MaterialDatePicker datePicker= MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Pick a date")
                    .setSelection(Long.valueOf(assignment.getDueDate()))
                    .build();
            datePicker.addOnPositiveButtonClickListener(selection -> {
                        dateInMillis= (Long) selection;
                        binding.dueDate.setText(DataSource.getDateFromMillis((Long) selection));
                    }
            );

            datePicker.show(getSupportFragmentManager(), "Tag");
        });
        binding.dueTime.setOnClickListener(view ->{
            int hour= Integer.parseInt(assignment.getDueTime().substring(0,2)),
                    minute=Integer.parseInt(assignment.getDueTime().substring(3));
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
        binding.save.setOnClickListener(view -> {
            //Get the data from the form
            String subject=binding.subjectAutoCompleteListView.getText().toString();
            String name=binding.nameTextView.getText().toString();
            String dueDate= String.valueOf(dateInMillis);
            String dueTime=binding.dueTime.getText().toString();
            String notes=binding.notes.getText().toString();
            Log.i("mine","Here 1");
            if(validateForm(subject,name,dueDate,dueTime)){
                //TODO: Edit the assignment
                assignment.setSubject(subject);
                assignment.setName(name);
                assignment.setDueDate(dueDate);
                assignment.setDueTime(dueTime);
                DataSource.updateAssignment(assignment);
            }
        });
    }
    private void cancel() {
        if(binding.subjectAutoCompleteListView.getText().toString().equals(assignment.getSubject())
                && binding.nameTextView.getText().toString().equals(assignment.getName())
                && binding.dueDate.getText().toString().equals(DataSource.getDateFromMillis(Long.parseLong(assignment.getDueDate())))
                && binding.dueTime.getText().toString().equals(assignment.getDueTime())
        ){
            finish();
        }
        else{
            AlertDialog.Builder builder=new AlertDialog.Builder(this,R.style.cancelDialogTheme)
                    .setMessage("Discard changes to this assignment?")
                    .setPositiveButton("Keep editing", null)
                    .setNegativeButton("Discard", null);
            AlertDialog dialog=builder.create();
            dialog.show();
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(view -> {
                finish();
            });
        }
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
}