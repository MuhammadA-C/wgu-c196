package com.muhammadchambersc196.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "terms")
public class Term {
    @PrimaryKey(autoGenerate = true)
    private int termID;
    private String title;
    private String startDate;
    private String endDate;


    public Term(int termID, String title, String startDate, String endDate) {
        this.setTermID(termID);
        this.setTitle(title);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
    }

    @Ignore
    public Term(String title, String startDate, String endDate) {
        this.setTitle(title);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
    }


    public void setTermID(int termID) {
        this.termID = termID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getTermID() {
        return this.termID;
    }

    public String getTitle() {
        return this.title;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }
}
