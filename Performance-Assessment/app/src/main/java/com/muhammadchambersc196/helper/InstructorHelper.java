package com.muhammadchambersc196.helper;

import androidx.annotation.NonNull;

import com.muhammadchambersc196.entities.Course;
import com.muhammadchambersc196.entities.CourseInstructor;

import java.util.ArrayList;
import java.util.List;

public class InstructorHelper {
    public static CourseInstructor retrieveCourseFromDatabaseByTermID(ArrayList<CourseInstructor> dbInstructorList, int instructorId) {
        if (dbInstructorList.size() == 0) {
            return null;
        }

        for (CourseInstructor dbInstructor : dbInstructorList) {
            if (dbInstructor.getInstructorID() == instructorId) {
                return dbInstructor;
            }
        }
        return null;
    }

    public static boolean doesCourseInstructorExistInDatabase(CourseInstructor addCourseInstructor, List<CourseInstructor> dbCourseInstructorList) {
        if (dbCourseInstructorList.size() == 0) {
            return false;
        }

        for(CourseInstructor dbCourseInstructor : dbCourseInstructorList) {
            if (dbCourseInstructor.getInstructorID() == addCourseInstructor.getInstructorID()) {
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

    public static boolean doesInstructorHaveCourses(int instructorId, ArrayList<Course> dbCourseList) {
        if (dbCourseList.size() == 0) {
            return false;
        }

        for (Course dbCourse : dbCourseList) {
            if (dbCourse.getInstructorID() == instructorId) {
                return true;
            }
        }
        return false;
    }
}
