package com.muhammadchambersc196.helper;

import com.muhammadchambersc196.entities.Assessment;

import java.util.ArrayList;

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
}
