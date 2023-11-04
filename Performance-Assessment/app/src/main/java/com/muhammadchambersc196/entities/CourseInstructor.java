package com.muhammadchambersc196.entities;

import androidx.room.Entity;

@Entity(tableName = "course_instructors")
public class CourseInstructor {
    private int courseInstructorID;
    private String name;
    private String phoneNumber;
    private String email;
}
