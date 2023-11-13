package com.muhammadchambersc196.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.muhammadchambersc196.dao.AssessmentDAO;
import com.muhammadchambersc196.dao.CourseDAO;
import com.muhammadchambersc196.dao.CourseInstructorDAO;
import com.muhammadchambersc196.dao.CourseNoteDAO;
import com.muhammadchambersc196.dao.TermDAO;
import com.muhammadchambersc196.entities.Assessment;
import com.muhammadchambersc196.entities.Course;
import com.muhammadchambersc196.entities.CourseInstructor;
import com.muhammadchambersc196.entities.CourseNote;
import com.muhammadchambersc196.entities.Term;

//Note: Need to change the database version number when making changes to the database
@Database(entities = {Assessment.class, Course.class, CourseInstructor.class, CourseNote.class, Term.class}, version = 10, exportSchema = false)
public abstract class DatabaseBuilder extends RoomDatabase {
    public abstract AssessmentDAO assessmentDAO();
    public abstract CourseDAO courseDAO();
    public abstract CourseInstructorDAO courseInstructorDAO();
    public abstract CourseNoteDAO courseNoteDAO();
    public abstract TermDAO termDAO();
    private static volatile DatabaseBuilder INSTANCE;

    static DatabaseBuilder getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (DatabaseBuilder.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseBuilder.class, "MyDatabaseBuilder.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
