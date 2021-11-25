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
    Boolean completed;

    public Assignment(String subject,String name, String dueDate,String dueTime ,String description, String assignmentID, String submittedDate, String submittedTime, Boolean completed) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.subject = subject;
        this.assignmentID = assignmentID;
        this.dueTime = dueTime;
        this.submittedDate = submittedDate;
        this.submittedTime = submittedTime;
        this.completed = completed;
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

    public Boolean isCompleted() {
        return completed;
    }

    public String getId() {
        return assignmentID;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
