package com.example.hciproject.objects;

public class Assignment {
    String subject;
    String name;
    String dueDate;
    String dueTime;
    String description;
    String assignmentID;
    String submittedDate;
    String submittedTime;

    public Assignment(String subject,String name, String dueDate,String dueTime ,String description, String assignmentID, String submittedDate, String submittedTime) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.subject = subject;
        this.assignmentID = assignmentID;
        this.dueTime = dueTime;
        this.submittedDate = submittedDate;
        this.submittedTime = submittedTime;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getSubject() {
        return subject;
    }

    public String getAssignmentID() {
        return assignmentID;
    }

    public String getDueTime() {
        return dueTime;
    }

    public String getSubmittedDate() {
        return submittedDate;
    }

    public String getSubmittedTime() {
        return submittedTime;
    }
}
