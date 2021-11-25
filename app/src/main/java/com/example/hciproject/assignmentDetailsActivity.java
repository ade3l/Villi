package com.example.hciproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.example.hciproject.data.DataSource;
import com.example.hciproject.databinding.ActivityAssignmentDetailsBinding;
import com.example.hciproject.objects.Assignment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.transition.platform.MaterialContainerTransform;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;

public class assignmentDetailsActivity extends AppCompatActivity {
    private ActivityAssignmentDetailsBinding binding;
    static Assignment assignment;
    Window window;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        window = this.getWindow();
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        super.onCreate(savedInstanceState);
        binding = ActivityAssignmentDetailsBinding.inflate(getLayoutInflater());
        setUpTransitions();
        setContentView(binding.getRoot());
        assignment=new Assignment(getIntent().getStringExtra("subject"),getIntent().getStringExtra("name"),getIntent().getStringExtra("due date"), getIntent().getStringExtra("due time"),
                getIntent().getStringExtra("description"),getIntent().getStringExtra("id"),getIntent().getStringExtra("submitted date"),getIntent().getStringExtra("submitted time"),getIntent().getBooleanExtra("completed",false));
        initToolbar();
        initDetails();
        binding.markAsDone.setOnClickListener(v -> completeAssignment());
        binding.editAssignment.setOnClickListener(v -> edit());
        binding.deleteAssignment.setOnClickListener(v -> deleteAssignment());
    }

    private void edit() {
        Intent intent = new Intent(this, editAssignmentActivity.class);
        startActivity(intent);
    }
    private void setUpTransitions() {
        findViewById(android.R.id.content).setTransitionName("detailsTransition");
        setEnterSharedElementCallback(new MaterialContainerTransformSharedElementCallback());
        window.setSharedElementEnterTransition(new MaterialContainerTransform().addTarget(android.R.id.content).setDuration(500));
        window.setSharedElementReturnTransition(new MaterialContainerTransform().addTarget(android.R.id.content).setDuration(350));
    }

    private void initToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v -> finishAfterTransition());
//        getSupportActionBar().setTitle(assignment.getName());
    }

    private void initDetails() {
//        Get data from the intent
//        Then set the data to the views

        String subject="Subject: " + assignment.getSubject();
        String dueDate=DataSource.getDayFromMillis(Long.parseLong(assignment.getDueDate())) + ", " +
                DataSource.getDateFromMillis(Long.parseLong(assignment.getDueDate()))+ " \u2022 "+
                assignment.getDueTime();
        String description="Description: " +assignment.getDescription();
        String submitted;
        if(assignment.isCompleted()){
            submitted= "Submitted: " + DataSource.getDateFromMillis(Long.parseLong(assignment.getSubmittedDate()))+" \u2022 "+assignment.getSubmittedTime();
            binding.markAsDone.setText("Mark as not done");
            binding.markAsDone.setTextColor(getResources().getColor(R.color.grey_active,getTheme()));
        }
        else{
            submitted="Submitted: Pending";
            binding.markAsDone.setText("Mark as done");
            binding.markAsDone.setTextColor(getResources().getColor(R.color.blue_inactive,getTheme()));

        }

        binding.assignmentName.setText(assignment.getName());

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

    private void completeAssignment(){
        Log.i("mine","clicked");
        if(assignment.isCompleted()){
//            If the assignment is already completed, then mark it as not completed
            DataSource.uncompleteAssignment(getIntent().getStringExtra("id"));
        }
        else{
//            If the assignment is not completed, then mark it as completed
            DataSource.completeAssignment(getIntent().getStringExtra("id"));
        }
        assignment=DataSource.getAssignmentById(getIntent().getStringExtra("id"));
        initDetails();
    }

    private void deleteAssignment() {
        AlertDialog.Builder builder=new MaterialAlertDialogBuilder(this,R.style.cancelDialogTheme);
        builder.setTitle("Delete Assignment");
        builder.setMessage("Are you sure you want to delete this assignment?");
        builder.setPositiveButton("No", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.setNegativeButton("Yes", (dialog, which) -> {
            DataSource.deleteAssignment(assignment.getId());
            finish();

        });
        builder.create();
        builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDetails();
    }
}
