package com.muhammadchambersc196.entities;

import androidx.room.Entity;

@Entity(tableName = "courses")
public class Course {
    private int courseID;
    private String title;
    private String status;
    private String information;
    private String startDate;
    private String endDate;

    //Foreign Keys
    private int termID;
    private int courseInstructorID;


}
