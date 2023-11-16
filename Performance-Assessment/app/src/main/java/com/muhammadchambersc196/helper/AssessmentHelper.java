package com.muhammadchambersc196.helper;

import com.muhammadchambersc196.entities.Assessment;
import com.muhammadchambersc196.entities.Course;
import com.muhammadchambersc196.entities.Term;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AssessmentHelper {
    public static Assessment retrieveAssessmentFromDatabaseByAssessmentID(ArrayList<Assessment> dbAssessmentList, int assessmentId) {
        if (dbAssessmentList.size() == 0) {
            return null;
        }

        for (Assessment dbAssessment : dbAssessmentList) {
            if (dbAssessment.getAssessmentID() == assessmentId) {
                return dbAssessment;
            }
        }
        return null;
    }

    public static ArrayList<Assessment> getAllAssessmentsForCourse(ArrayList<Assessment> dbAssessmentList, int courseID) {
        if (dbAssessmentList.size() == 0) {
            return new ArrayList<>();
        }

        ArrayList<Assessment> assessmentsForCourse = new ArrayList<>();

        for(Assessment dbAssessment : dbAssessmentList) {
            if(dbAssessment.getCourseID() == courseID) {
                assessmentsForCourse.add(dbAssessment);
            }
        }
        return assessmentsForCourse;
    }

    public static boolean areAssessmentDatesWithinRangeOfCourseDates(Assessment assessment, int courseId, ArrayList<Course> dbCourseList) {
        Course course = CourseHelper.retrieveCourseFromDatabaseByCourseID(dbCourseList, courseId);

        //This is needed because term could be null which would causes issues with the code below
        if (course == null) {
            return false;
        }

        LocalDate assessmentStartDate = LocalDate.parse(assessment.getStartDate());
        LocalDate assessmentEndDate = LocalDate.parse(assessment.getEndDate());
        LocalDate courseStartDate = LocalDate.parse(course.getStartDate());
        LocalDate courseEndDate = LocalDate.parse(course.getEndDate());

        if (assessmentStartDate.isEqual(courseStartDate) && assessmentEndDate.isEqual(courseEndDate)) {
            return true;
        } else if (assessmentStartDate.isAfter(courseStartDate) && assessmentEndDate.isEqual(courseEndDate)) {
            return true;
        } else if (assessmentStartDate.isAfter(courseStartDate) && assessmentEndDate.isBefore(courseEndDate)) {
            return true;
        } else if (assessmentStartDate.isEqual(courseStartDate) && assessmentEndDate.isBefore(courseEndDate)) {
            return true;
        } else if (assessmentEndDate.isEqual(courseEndDate) && assessmentStartDate.isAfter(courseStartDate)) {
            return true;
        } else if (assessmentStartDate.isEqual(courseStartDate) && assessmentEndDate.isEqual(courseStartDate)) {
            return true;
        } else if (assessmentStartDate.isEqual(courseEndDate) && assessmentEndDate.isEqual(courseEndDate)) {
            return true;
        }
        return false;
    }
}
