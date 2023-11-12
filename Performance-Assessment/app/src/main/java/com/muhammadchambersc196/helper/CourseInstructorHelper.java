package com.muhammadchambersc196.helper;

import com.muhammadchambersc196.entities.CourseInstructor;

import java.util.List;

public class CourseInstructorHelper {
    public static boolean doesCourseInstructorInDatabase(CourseInstructor addCourseInstructor, List<CourseInstructor> databaseCourseInstructorList) {
        if (databaseCourseInstructorList.size() == 0) {
            return false;
        }

        for(CourseInstructor dbCourseInstructor : databaseCourseInstructorList) {
            if(addCourseInstructor.getName().toLowerCase().equals(dbCourseInstructor.getName().toLowerCase()) &&
                    addCourseInstructor.getPhoneNumber().equals(dbCourseInstructor.getPhoneNumber()) &&
                    addCourseInstructor.getEmail().equals(dbCourseInstructor.getEmail())) {
                return true;
            }
        }
        return false;
    }
}
