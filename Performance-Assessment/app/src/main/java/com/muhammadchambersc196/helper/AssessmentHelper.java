package com.muhammadchambersc196.helper;

import com.muhammadchambersc196.entities.Assessment;

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
}
