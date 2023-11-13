package com.muhammadchambersc196.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.muhammadchambersc196.entities.CourseInstructor;

import java.util.List;

@Dao
public interface CourseInstructorDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CourseInstructor courseInstructor);

    @Update
    void update(CourseInstructor courseInstructor);

    @Delete
    void delete(CourseInstructor courseInstructor);

    @Query("SELECT * FROM COURSE_INSTRUCTORS ORDER BY instructorID ASC")
    List<CourseInstructor> getAllCourseInstructors();
}
