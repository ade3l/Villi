package com.example.hciproject.objects;

public class Classes {
    private String subject, startTime,endTime,classId;

    public Classes(String subject, String startTime, String endTime, String classId) {
        this.subject = subject;
        this.startTime = startTime;
        this.endTime = endTime;
        this.classId = classId;
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

    public String getClassId() {
        return classId;
    }
}
