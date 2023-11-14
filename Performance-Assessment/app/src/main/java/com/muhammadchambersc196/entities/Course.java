package com.muhammadchambersc196.entities;

import android.widget.EditText;
import android.widget.Spinner;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses")
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int courseID;
    private String title;
    private String status;
    private String information;
    private String startDate;
    private String endDate;

    //Foreign Keys
    private int termID;
    private int instructorID;

    public Course(int courseID, String title, String status, String information, String startDate, String endDate, int termID, int instructorID) {
        this.setCourseID(courseID);
        this.setTitle(title);
        this.setStatus(status);
        this.setInformation(information);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
        this.setTermID(termID);
        this.setInstructorID(instructorID);
    }

    @Ignore
    public Course(String title, String status, String information, String startDate, String endDate, int termID, int instructorID) {
        this.setTitle(title);
        this.setStatus(status);
        this.setInformation(information);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
        this.setTermID(termID);
        this.setInstructorID(instructorID);
    }


    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public void setInstructorID(int courseInstructorID) {
        this.instructorID = courseInstructorID;
    }

    public int getCourseID() {
        return this.courseID;
    }

    public int getTermID() {
        return this.termID;
    }

    public int getInstructorID() {
        return this.instructorID;
    }

    public String getTitle() {
        return this.title;
    }

    public String getStatus() {
        return this.status;
    }

    public String getInformation() {
        return this.information;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public void updateFields(EditText className, EditText classInfo, Spinner classStatus, Spinner selectInstructor,
                             EditText startDate, EditText endDate) {
        this.setTitle(className.getText().toString());
        this.setInformation(classInfo.getText().toString());
        this.setStatus(classStatus.getSelectedItem().toString());
        this.setInstructorID(((CourseInstructor) selectInstructor.getSelectedItem()).getInstructorID());
        this.setStartDate(startDate.getText().toString());
        this.setEndDate(endDate.getText().toString());
    }

}
