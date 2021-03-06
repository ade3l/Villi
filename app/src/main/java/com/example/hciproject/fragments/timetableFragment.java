package com.example.hciproject.fragments;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.hciproject.R;
import com.example.hciproject.adapters.classesAdapter;
import com.example.hciproject.addClassActivity;
import com.example.hciproject.data.DataSource;
import com.example.hciproject.databinding.FragmentTimetableBinding;
import com.example.hciproject.objects.Classes;

import java.util.List;

public class timetableFragment extends Fragment {
    View view;
    private FragmentTimetableBinding binding;
    public static int day=0;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentTimetableBinding.inflate(inflater, container, false);
        view=binding.getRoot();
        binding.addClass.setOnClickListener(view -> addClass());
        setClasses(day);
        binding.dayRadioGrp.setOnCheckedChangeListener((radioGroup, i) -> {
            day=getRadioButton(i);
            setClasses(day);
        });
        showFab();
        return view;
    }

    public void hideFab() {
        binding.addClass.hide();
    }
    public void showFab() {
        binding.addClass.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        setClasses(getRadioButton(binding.dayRadioGrp.getCheckedRadioButtonId()));
    }

    private int getRadioButton(int i) {
        if (binding.monday.equals(view.findViewById(i))) {
            return 0;
        }
        if (binding.tuesday.equals(view.findViewById(i))) {
            return 1;
        }
        if (binding.wednesday.equals(view.findViewById(i))) {
            return 2;
        }
        if (binding.thursday.equals(view.findViewById(i))) {
            return 3;
        }
        if (binding.friday.equals(view.findViewById(i))) {
            return 4;
        }
        if (binding.saturday.equals(view.findViewById(i))) {
            return 5;
        }
        return 6;
    }
    public static List<Classes> listOfClasses;
    void setClasses(int day){
        String dayName= DataSource.getDays().get(day);
        binding.day.setText(dayName);
        listOfClasses=DataSource.getClasses(dayName);
        binding.numberOfClasses.setText(getString(R.string.numClass,listOfClasses.size()));
        classesAdapter classAdapter=new classesAdapter(getContext(),listOfClasses);
        binding.classesRV.setAdapter(classAdapter);
        binding.classesRV.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    void addClass(){
        Intent intent = new Intent(getContext(), addClassActivity.class);
        intent.putExtra("day",getSelectedDay());
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) getContext(),
                binding.addClass, "addClassTransition");
        startActivity(intent, options.toBundle());
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
        hideFab();
        binding=null;
    }
}