package com.muhammadchambersc196.helper;

import com.muhammadchambersc196.entities.Assessment;
import com.muhammadchambersc196.entities.Course;
import com.muhammadchambersc196.entities.CourseInstructor;
import com.muhammadchambersc196.entities.CourseNote;

import java.util.ArrayList;
import java.util.List;

public class Helper {
    public static boolean doesCourseHaveCourseNotes(int courseId, List<CourseNote> courseNoteList) {
        int count = 0;

        for(CourseNote courseNote : courseNoteList) {
            if(courseNote.getCourseID() == courseId) {
                count++;
            }
        }

        if(count == 0) {
            return false;
        }
        return true;
    }

    public static ArrayList<CourseNote> getAllCourseNotesForCourse(int courseId, List<CourseNote> courseNoteList) {
        ArrayList<CourseNote> courseNotesForCourse = new ArrayList<>();

        for(CourseNote courseNote : courseNoteList) {
            if(courseNote.getCourseID() == courseId) {
                courseNotesForCourse.add(courseNote);
            }
        }
        return courseNotesForCourse;
    }

    public static boolean doesCourseHaveAssessments(int courseId, List<Assessment> assessmentList) {
        int count = 0;

        for(Assessment assessment : assessmentList) {
            if(assessment.getCourseID() == courseId) {
                count++;
            }
        }

        if(count == 0) {
            return false;
        }
        return true;
    }

    public static ArrayList<Assessment> getAllAssessmentsForCourse(int courseId, List<Assessment> assessmentList) {
        ArrayList<Assessment> assessmentsForCourse = new ArrayList<>();

        for(Assessment assessment : assessmentList) {
            if(assessment.getCourseID() == courseId) {
                assessmentsForCourse.add(assessment);
            }
        }
        return assessmentsForCourse;
    }

    //Method below will be used to prevent duplicate course instructors from being added to the database
    public static boolean isCourseInstructorInDatabase(CourseInstructor valueToAdd, List<CourseInstructor> courseInstructorList) {
        for(CourseInstructor courseInstructorInList : courseInstructorList) {
            if(valueToAdd.getName().toLowerCase().equals(courseInstructorInList.getName().toLowerCase()) &&
                valueToAdd.getPhoneNumber().equals(courseInstructorInList.getPhoneNumber()) &&
                valueToAdd.getEmail().equals(courseInstructorInList.getEmail())) {
                return true;
            }
        }
        return false;
    }
}
