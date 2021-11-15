package com.example.hciproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.example.hciproject.data.DataSource;
import com.example.hciproject.databinding.ActivityAddAssignmentBinding;
import com.google.android.material.textfield.TextInputLayout;

public class addAssignmentActivity extends AppCompatActivity {
    private ActivityAddAssignmentBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddAssignmentBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        setUpToolBar();
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
}