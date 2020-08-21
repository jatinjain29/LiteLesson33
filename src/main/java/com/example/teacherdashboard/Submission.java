package com.example.teacherdashboard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONArray;

class Submission {
    String optional_message,student,subject,submitted_on;
    JSONArray attach_files;

    public Submission(String optional_message, String student, String subject, String submitted_on, JSONArray attach_files) {
        this.optional_message = optional_message;
        this.student = student;
        this.subject = subject;
        this.submitted_on = submitted_on;
        this.attach_files = attach_files;
    }
@Nullable
    public String getOptional_message() {
        return optional_message;
    }

    public void setOptional_message(String optional_message) {
        this.optional_message = optional_message;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }



    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubmitted_on() {
        return submitted_on;
    }

    public void setSubmitted_on(String submitted_on) {
        this.submitted_on = submitted_on;
    }

    public JSONArray getAttach_files() {
        return attach_files;
    }

    public void setAttach_files(JSONArray attach_files) {
        this.attach_files = attach_files;
    }
}
