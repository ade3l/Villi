package com.example.hciproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.hciproject.databinding.ActivityAddAssignmentBinding;

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
    }
}