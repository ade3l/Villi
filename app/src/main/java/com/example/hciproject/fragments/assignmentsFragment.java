package com.example.hciproject.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.hciproject.adapters.assignmentsAdapter;
import com.example.hciproject.addAssignmentActivity;
import com.example.hciproject.data.DataSource;
import com.example.hciproject.databinding.FragmentAssignmentsBinding;
import com.example.hciproject.objects.Assignment;

import java.util.List;

public class assignmentsFragment extends Fragment {
    private FragmentAssignmentsBinding binding;
    View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentAssignmentsBinding.inflate(getLayoutInflater(),container,false);
        // Inflate the layout for this fragment
        view=binding.getRoot();

        binding.addAssignment.show();
        binding.addAssignment.setOnClickListener(view -> addAssignment());
        showFab();
        return view;
    }

    private void initAssignmentsRecycler() {
        List<Assignment> assignments= DataSource.getPendingAssignments();
        binding.assignmentsRV.setAdapter(new assignmentsAdapter(assignments,false));
        binding.assignmentsRV.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(getContext()));
        if(assignments.size()==1){
            binding.numberOfAssignments.setText("1 assignment");
        }else{
            binding.numberOfAssignments.setText(assignments.size()+" assignments");
        }

        //Subitted recycler view init
        List<Assignment> submittedAssignments=DataSource.getSubmittedAssignments();
        binding.submittedAssignmentsRV.setAdapter(new assignmentsAdapter(submittedAssignments,true));
        binding.submittedAssignmentsRV.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(getContext()));

    }

    private void addAssignment() {
        Intent intent=new Intent(getContext(), addAssignmentActivity.class);
        startActivity(intent);
    }

    public  void hideFab(){
        binding.addAssignment.hide();
    }
    public  void showFab(){
        binding.addAssignment.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hideFab();
        binding=null;
    }

    @Override
    public void onResume() {
        super.onResume();
        initAssignmentsRecycler();
    }
}