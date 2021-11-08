package com.example.hciproject.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hciproject.R;
import com.example.hciproject.databinding.FragmentAssignmentsBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

        binding.addTask.show();
        showFab();
        return view;
    }
    public  void hideFab(){
        binding.addTask.hide();
    }
    public  void showFab(){
        binding.addTask.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hideFab();
        binding=null;

    }
}