package com.muhammadchambersc196.entities;

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
    private String courseInstructorName;
    private String courseInstructorEmail;


    public Course(int courseID, String title, String status, String information, String startDate, String endDate, int termID, String courseInstructorName, String courseInstructorEmail) {
        this.setCourseID(courseID);
        this.setTitle(title);
        this.setStatus(status);
        this.setInformation(information);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
        this.setTermID(termID);
        this.setCourseInstructorName(courseInstructorName);
        this.setCourseInstructorEmail(courseInstructorEmail);
    }

    @Ignore
    public Course(String title, String status, String information, String startDate, String endDate, int termID) {
        this.setTitle(title);
        this.setStatus(status);
        this.setInformation(information);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
        this.setTermID(termID);
        this.setCourseInstructorName(courseInstructorName);
        this.setCourseInstructorEmail(courseInstructorEmail);
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

    public void setCourseInstructorName(String courseInstructorName) {
        this.courseInstructorName = courseInstructorName;
    }

    public void setCourseInstructorEmail(String courseInstructorEmail) {
        this.courseInstructorName = courseInstructorEmail;
    }

    public int getCourseID() {
        return this.courseID;
    }

    public int getTermID() {
        return this.termID;
    }

    public String getCourseInstructorName() {
        return this.courseInstructorName;
    }

    public String getCourseInstructorEmail() {
        return this.courseInstructorEmail;
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

}
