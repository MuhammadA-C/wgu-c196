package com.muhammadchambersc196.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "course_notes")
public class CourseNote {
    @PrimaryKey(autoGenerate = true)
    private int courseNoteID;
    private String note;

    //Foreign Keys
    private int courseID;


    public CourseNote(int courseNoteID, String note, int courseID) {
        this.setCourseNoteID(courseNoteID);
        this.setCourseID(courseID);
        this.setNote(note);
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setCourseNoteID(int courseNoteID) {
        this.courseNoteID = courseNoteID;
    }

    public int getCourseNoteID() {
        return this.courseNoteID;
    }

    public int getCourseID() {
        return this.courseID;
    }

    public String getNote() {
        return this.note;
    }
}
