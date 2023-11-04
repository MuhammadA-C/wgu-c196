package com.muhammadchambersc196.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.muhammadchambersc196.entities.CourseNote;

import java.util.List;

@Dao
public interface CourseNoteDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CourseNote courseNote);

    @Update
    void update(CourseNote courseNote);

    @Delete
    void delete(CourseNote courseNote);

    @Query("SELECT * FROM COURSE_NOTES ORDER BY courseNoteID ASC")
    List<CourseNote> getAllCourseNotes();
}
