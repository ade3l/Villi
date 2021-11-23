package com.example.hciproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.google.android.material.transition.platform.MaterialContainerTransform;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;

public class assignmentDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = this.getWindow();
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        super.onCreate(savedInstanceState);
        findViewById(android.R.id.content).setTransitionName("detailsTransition");
        setEnterSharedElementCallback(new MaterialContainerTransformSharedElementCallback());
        window.setSharedElementEnterTransition(new MaterialContainerTransform().addTarget(android.R.id.content).setDuration(500));
        window.setSharedElementReturnTransition(new MaterialContainerTransform().addTarget(android.R.id.content).setDuration(250));
        setContentView(R.layout.activity_assignment_details);
        TextView name=findViewById(R.id.assignment_name);
        name.setText(getIntent().getStringExtra("assignment name"));
    }

}
