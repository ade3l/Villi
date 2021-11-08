package com.example.hciproject.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hciproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class assignmentsFragment extends Fragment {
    static FloatingActionButton fab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflatedView=inflater.inflate(R.layout.fragment_assignments, container, false);
        fab=inflatedView.findViewById(R.id.add_task);
        fab.show();

        return inflatedView;
    }
    public static void hideFab(){
        fab.hide();
    }
    public static void showFab(){
        fab.show();
    }
}