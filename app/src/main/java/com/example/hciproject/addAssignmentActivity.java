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
    }
}