package com.muhammadchambersc196.database;

import androidx.room.Database;

import com.muhammadchambersc196.entities.Assessment;
import com.muhammadchambersc196.entities.Course;
import com.muhammadchambersc196.entities.CourseInstructor;
import com.muhammadchambersc196.entities.CourseNote;
import com.muhammadchambersc196.entities.Term;

//Note: Need to change the database version number when making changes to the database
@Database(entities = {Assessment.class, Course.class, CourseInstructor.class, CourseNote.class, Term.class}, version = 0, exportSchema = false)
public class DatabaseBuilder {
}
