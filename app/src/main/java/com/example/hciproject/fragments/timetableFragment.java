package com.example.hciproject.fragments;

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
    static View view;
    private static FragmentTimetableBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentTimetableBinding.inflate(inflater, container, false);
        view=binding.getRoot();
        binding.addClass.setOnClickListener(view -> addClass());
        setClasses(0);
        binding.dayRadioGrp.setOnCheckedChangeListener((radioGroup, i) -> {
            int day=getRadioButton(i);
            setClasses(day);
        });
        return view;
    }

    public static void hideFab() {
        binding.addClass.hide();
    }
    public static void showFab() {
        binding.addClass.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        setClasses(getRadioButton(binding.dayRadioGrp.getCheckedRadioButtonId()));
    }
    public static void refreshClasses() {
        getRadioButton(binding.dayRadioGrp.getCheckedRadioButtonId());
    }
    private static int getRadioButton(int i) {
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

    void setClasses(int day){
        String dayName= DataSource.getDays().get(day);
        binding.day.setText(dayName);
        List<Classes> listOfClasses=DataSource.getClasses(dayName);
        binding.numberOfClasses.setText(getString(R.string.numClass,listOfClasses.size()));
        classesAdapter adapter=new classesAdapter(getContext(),listOfClasses);
        binding.classesRV.setAdapter(adapter);
        binding.classesRV.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    void addClass(){
        Intent intent = new Intent(getContext(), addClassActivity.class);
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