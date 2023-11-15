package com.muhammadchambersc196.entities;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessments")
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessmentID;
    private String title;
    private String type;
    private String information;
    private String startDate;
    private String endDate;

    //Foreign Keys
    private int courseID;


    public Assessment(int assessmentID, String  title, String type, String information, String startDate, String endDate, int courseID) {
        this.setAssessmentID(assessmentID);
        this.setTitle(title);
        this.setInformation(information);
        this.setType(type);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
        this.setCourseID(courseID);
    }

    @Ignore
    public Assessment(String  title, String type, String information, String startDate, String endDate, int courseID) {
        this.setTitle(title);
        this.setInformation(information);
        this.setType(type);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
        this.setCourseID(courseID);
    }


    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getAssessmentID() {
        return this.assessmentID;
    }

    public String getTitle() {
        return this.title;
    }

    public String getInformation() {
        return this.information;
    }

    public String getType() {
        return this.type;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public int getCourseID() {
        return this.courseID;
    }

    public void updateInputFields(EditText assessmentName, EditText assessmentInfo, Spinner assessmentType, EditText startDate, EditText endDate) {
        setTitle(assessmentName.getText().toString());
        setInformation(assessmentInfo.getText().toString());
        setType(assessmentType.getSelectedItem().toString());
        setStartDate(startDate.getText().toString());
        setEndDate(endDate.getText().toString());
    }
}
