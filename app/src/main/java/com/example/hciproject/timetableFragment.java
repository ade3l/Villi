package com.example.hciproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.hciproject.databinding.FragmentTimetableBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class timetableFragment extends Fragment {
    FloatingActionButton addClassButton;
    View view;
    private FragmentTimetableBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentTimetableBinding.inflate(inflater, container, false);
        view=binding.getRoot();
        addClassButton =binding.addClass;
        addClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addClass();
            }
        });
        return view;
    }

    void addClass(){
        Intent intent = new Intent(getContext(),addClassActivity.class);
        intent.putExtra("day",getSelectedDay());
        startActivity(intent);
    }

    private int getSelectedDay() {
        RadioGroup radioGroup= binding.dayRadioGrp;
        RadioButton radioButton=view.findViewById(radioGroup.getCheckedRadioButtonId());
        int day=radioButton.getId();
        if(day==binding.monday.getId()){
            return 0;
        } else if(day==binding.tuesday.getId()){
            return 1;
        } else if(day==binding.wednesday.getId()){
            return 2;
        } else if(day==binding.thursday.getId()){
            return 3;
        } else if(day==binding.friday.getId()){
            return 4;
        } else if(day==binding.saturday.getId()){
            return 5;
        } else if(day==binding.sunday.getId()){
            return 6;
        }
        return 0;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}