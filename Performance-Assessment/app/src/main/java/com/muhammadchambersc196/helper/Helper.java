package com.muhammadchambersc196.helper;

import com.muhammadchambersc196.entities.Assessment;
import com.muhammadchambersc196.entities.CourseNote;

import java.util.ArrayList;
import java.util.List;

public class Helper {

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

}
