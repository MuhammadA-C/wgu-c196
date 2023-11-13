package com.muhammadchambersc196.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "course_instructors")
public class CourseInstructor {
    @PrimaryKey(autoGenerate = true)
    private int courseInstructorID;
    private String name;
    private String phoneNumber;
    private String email;


    public CourseInstructor(int courseInstructorID, String name, String phoneNumber, String email) {
        this.setCourseInstructorID(courseInstructorID);
        this.setName(name);
        this.setPhoneNumber(phoneNumber);
        this.setEmail(email);
    }

    @Ignore
    public CourseInstructor(String name, String phoneNumber, String email) {
        this.setName(name);
        this.setPhoneNumber(phoneNumber);
        this.setEmail(email);
    }


    public void setCourseInstructorID(int courseInstructorID) {
        this.courseInstructorID = courseInstructorID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCourseInstructorID() {
        return this.courseInstructorID;
    }

    public String getName() {
        return this.name;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
