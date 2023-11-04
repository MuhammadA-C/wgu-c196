package com.muhammadchambersc196.entities;

import androidx.room.Entity;

@Entity(tableName = "assessments")
public class Assessment {
    private int assessmentID;
    private String title;
    private String type;
    private String information;
    private String startDate;
    private String endDate;

    //Foreign Keys
    private int courseID;
}
