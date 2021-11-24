package com.example.hciproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Window;

import com.example.hciproject.data.DataSource;
import com.example.hciproject.databinding.ActivityAssignmentDetailsBinding;
import com.google.android.material.transition.platform.MaterialContainerTransform;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;

public class assignmentDetailsActivity extends AppCompatActivity {
    private ActivityAssignmentDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = this.getWindow();
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        super.onCreate(savedInstanceState);
        binding = ActivityAssignmentDetailsBinding.inflate(getLayoutInflater());
        findViewById(android.R.id.content).setTransitionName("detailsTransition");
        setEnterSharedElementCallback(new MaterialContainerTransformSharedElementCallback());
        window.setSharedElementEnterTransition(new MaterialContainerTransform().addTarget(android.R.id.content).setDuration(500));
        window.setSharedElementReturnTransition(new MaterialContainerTransform().addTarget(android.R.id.content).setDuration(250));
        setContentView(binding.getRoot());
        initToolbar();
        initDetails();
    }

    private void initDetails() {
//        Get data from the intent
//        Then set the data to the views

        String subject="Subject: " + getIntent().getStringExtra("subject");
        String dueDate=DataSource.getDayFromMillis(Long.parseLong(getIntent().getStringExtra("due date"))) + ", " +
                DataSource.getDateFromMillis(Long.parseLong(getIntent().getStringExtra("due date")))+ " \u2022 "+
                getIntent().getStringExtra("due time");
        String description="Description: " +getIntent().getStringExtra("description");
        String submitted;
        if(getIntent().getBooleanExtra("completed",false)){
            submitted= "Submitted: " + getIntent().getStringExtra("submitted date")+" \u2022 "+getIntent().getStringExtra("submitted time");
        }
        else{
            submitted="Submitted: Pending";
        }

        binding.assignmentName.setText(getIntent().getStringExtra("name"));
        SpannableString subjectSpannable=new SpannableString(subject);
        subjectSpannable.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),0,9,0);
        binding.assignmentSubject.setText(subjectSpannable);

        SpannableString dueDateSpannable=new SpannableString(dueDate);
        binding.assignmentDueDate.setText(dueDateSpannable);

        SpannableString descriptionSpannable=new SpannableString(description);
        descriptionSpannable.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),0,12,0);
        binding.assignmentDescription.setText(descriptionSpannable);

        SpannableString submittedSpannable=new SpannableString(submitted);
        submittedSpannable.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),0,9,0);
        binding.assignmentSubmittedDate.setText(submittedSpannable);

    }

    private void initToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra(""));
    }

}
