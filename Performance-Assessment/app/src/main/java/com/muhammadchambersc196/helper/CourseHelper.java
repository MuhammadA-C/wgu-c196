package com.muhammadchambersc196.helper;

import com.muhammadchambersc196.entities.Course;
import com.muhammadchambersc196.entities.Term;

import java.time.LocalDate;
import java.util.ArrayList;

public class CourseHelper {
    public static boolean areCourseDatesWithinRangeOfTermDates(Course course, int termId, ArrayList<Term> terms) {
        Term term = TermHelper.retrieveTermFromDatabaseByTermID(terms, termId);
        LocalDate courseStartDate = LocalDate.parse(course.getStartDate());
        LocalDate courseEndDate = LocalDate.parse(course.getEndDate());
        LocalDate termStartDate = LocalDate.parse(term.getStartDate());
        LocalDate termEndDate = LocalDate.parse(term.getEndDate());

        if (courseStartDate.isEqual(termStartDate) && courseEndDate.isEqual(termEndDate)) {
            return true;
        } else if (courseStartDate.isAfter(termStartDate) && courseEndDate.isEqual(termEndDate)) {
            return true;
        } else if (courseStartDate.isAfter(termStartDate) && courseEndDate.isBefore(termEndDate)) {
            return true;
        } else if (courseStartDate.isEqual(termStartDate) && courseEndDate.isEqual(termStartDate)) {
            return true;
        } else if (courseStartDate.isEqual(termEndDate) && courseEndDate.isEqual(termEndDate)) {
            return true;
        }
        return false;
    }
}
