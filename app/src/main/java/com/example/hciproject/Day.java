package com.example.hciproject;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Day {
    String dayName;
    Map<String, LocalTime> schedule = new HashMap<String, LocalTime>() ;

    public Day(String dayName) {
        this.dayName = dayName;
    }

    public Day(String dayName, Map<String, LocalTime> schedule) {
        this.dayName = dayName;
        this.schedule = schedule;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public Map<String, LocalTime> getSchedule() {
        return schedule;
    }

    public void setSchedule(Map<String, LocalTime> schedule) {
        this.schedule = schedule;
    }


}
