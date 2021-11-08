package com.example.hciproject.objects;

public class Classes {
    private String subject, startTime,endTime;

    public Classes(String subject, String startTime, String endTime) {
        this.subject = subject;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public String getSubject() {
        return subject;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
