package com.muhammadchambersc196.entities;

import androidx.room.Entity;

@Entity(tableName = "terms")
public class Term {
    private int termID;
    private String title;
    private String startDate;
    private String endDate;
}
