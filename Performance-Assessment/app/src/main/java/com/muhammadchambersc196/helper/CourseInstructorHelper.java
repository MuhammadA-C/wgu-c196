package com.muhammadchambersc196.helper;

import androidx.annotation.NonNull;

import com.muhammadchambersc196.entities.Course;
import com.muhammadchambersc196.entities.CourseInstructor;

import java.util.ArrayList;
import java.util.List;

public class CourseInstructorHelper {
    public static CourseInstructor retrieveCourseFromDatabaseByTermID(@NonNull ArrayList<CourseInstructor> dbListOfInstructors, int instructorId) {
        for (CourseInstructor dbInstructor : dbListOfInstructors) {
            if (dbInstructor.getCourseInstructorID() == instructorId) {
                return dbInstructor;
            }
        }
        return null;
    }

    public static boolean doesCourseInstructorInDatabase(CourseInstructor addCourseInstructor, List<CourseInstructor> databaseCourseInstructorList) {
        if (databaseCourseInstructorList.size() == 0) {
            return false;
        }

        for(CourseInstructor dbCourseInstructor : databaseCourseInstructorList) {
            if (dbCourseInstructor.getCourseInstructorID() == addCourseInstructor.getCourseInstructorID()) {
                continue;
            }

            if(addCourseInstructor.getName().toLowerCase().equals(dbCourseInstructor.getName().toLowerCase()) &&
                    addCourseInstructor.getPhoneNumber().equals(dbCourseInstructor.getPhoneNumber()) &&
                    addCourseInstructor.getEmail().equals(dbCourseInstructor.getEmail())) {
                return true;
            }
        }
        return false;
    }
}
