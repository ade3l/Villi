package com.example.hciproject;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Day {
    public String dayName;
    public SortedMap<LocalTime,String> schedule = new TreeMap<LocalTime,String>();

    public Day(String dayName) {
        this.dayName = dayName;
    }

    public Day(String dayName, SortedMap<LocalTime,String> schedule) {
        this.dayName = dayName;
        this.schedule = schedule;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public SortedMap<LocalTime,String> getSchedule() {
        return schedule;
    }

    public void setSchedule(SortedMap<LocalTime,String> schedule) {
        this.schedule = schedule;
    }
    public  void addToSchedule( LocalTime t,String s){ schedule.put(t,s); }

}
