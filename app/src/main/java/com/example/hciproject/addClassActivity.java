package com.example.hciproject;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.google.android.material.textfield.TextInputLayout;
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
    //Code to remove focus from the edit texts when clicked outside
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
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
        binding.addSubject.setOnClickListener(view -> createAddSubjectDialog(adapter));
        binding.SubjectautoCompleteListView.setOnItemClickListener((adapterView, view, i, l) -> {
            InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
            binding.daysAutoCompleteListView.requestFocus();
//            binding.subjectTIlayout.setError(null);
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

    void createAddSubjectDialog(ArrayAdapter adapter){
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.dialogTheme);
        builder.setTitle("Enter subject details");
        View inflatedView= LayoutInflater.from(this).inflate(R.layout.dialog_add_class,(ViewGroup) findViewById(android.R.id.content),false);
        builder.setView(inflatedView);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("Cancel",null);
        AlertDialog dialog=builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText subjectNameTV= inflatedView.findViewById(R.id.subjectNameIp);
                String subjectName=subjectNameTV.getText().toString();
                TextInputLayout subjectNameParent=inflatedView.findViewById(R.id.subjectNameIpLayout);
                if(subjectName.equals("")){
                    subjectNameParent.setError("Enter a subject name");
                }
                else{
                    DataSource.addSubject(subjectName);
                    adapter.notifyDataSetChanged();
                    binding.SubjectautoCompleteListView.setText(subjectName);
                }
            }
        });
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
        binding.addClass.setOnClickListener(view -> {
            validateForm();
        });
    }

    private boolean validateForm() {
        String day=binding.daysAutoCompleteListView.getText().toString();
        String subjectName=binding.SubjectautoCompleteListView.getText().toString();
        String startTime=binding.startTime.getText().toString();
        String endTime=binding.endTime.getText().toString();
        boolean isValid=true;
        if(day.equals("")){
            binding.dayTIlayout.setError("Enter a day");
            isValid=false;
        }
        else if(!DataSource.getDays().contains(day)){
            binding.dayTIlayout.setError("Please select a valid day");
            isValid=false;
        }
        if(subjectName.equals("")){
            binding.subjectTIlayout.setError("Enter a subject");
            isValid=false;
        }
        else if(!DataSource.getSubjects().contains(subjectName)){
            binding.subjectTIlayout.setError("Could not find the subject");
            isValid=false;
        }
        if(startTime.equals("")){
            binding.startTimeTIlayout.setError("Enter a start time");
            isValid=false;
        }
        if(endTime.equals("")){
            binding.endTimeTIlayout.setError("Enter an end time");
            isValid=false;
        }
        if(!startTime.equals("")&&!endTime.equals("")){
            //Make sure that end time is after start time
            //This is done be converting the times to an integer
            //i.e 08:00 = 0800. Then comparing them
            if(Integer.parseInt(startTime.replace(":",""))>Integer.parseInt(endTime.replace(":",""))){
                binding.endTimeTIlayout.setError("End time must be after start time");
                isValid=false;
            }
        }
        return isValid;
    }

    View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if(!binding.SubjectautoCompleteListView.hasFocus()){
                InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
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
            AlertDialog.Builder builder= new MaterialAlertDialogBuilder(this,R.style.cancelDialogTheme)
                    .setTitle("Delete this draft?")
                    .setMessage("You will lose the class data that you have filled. \n\nNewly created subjects will not be deleted and can be deleted by visiting My Subjects")
                    .setPositiveButton("Cancel", null)
                    .setNegativeButton("Ok",  null)
                    ;
            AlertDialog dialog=builder.create();
            dialog.show();
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {
                             finish(); dialog.cancel();
                         }
                     }
                    );
        }
    }
}