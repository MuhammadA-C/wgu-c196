package com.muhammadchambersc196.helper;

import androidx.annotation.NonNull;

import com.muhammadchambersc196.entities.Course;
import com.muhammadchambersc196.entities.Term;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TermHelper {
    public static boolean doesTermNameExistInDatabase(@NonNull ArrayList<Term> databaseListOfTerms, Term term) {
        if (databaseListOfTerms.size() == 0) {
            return false;
        }

        for (Term termInDatabase : databaseListOfTerms) {
            if (termInDatabase.getTermID() != term.getTermID() && term.getTitle().equals(termInDatabase.getTitle())) {
                return true;

            }
        }
        return false;
    }

    public static Term retrieveTermFromDatabaseByTermID(ArrayList<Term> dbTermList, int termId) {
        if (dbTermList.size() == 0) {
            return null;
        }

        for (Term term : dbTermList) {
            if (term.getTermID() == termId) {
                return term;
            }
        }
        return null;
    }

    public static boolean doesTermDateOverlapWithTermInDatabase(@NonNull ArrayList<Term> dbTermList, Term term) {
        if (dbTermList.size() == 0) {
            return false;
        }

        LocalDate startDate = LocalDate.parse(term.getStartDate());
        LocalDate endDate = LocalDate.parse(term.getEndDate());

        int numOfOverlappingDates = 0;

        for (Term termInDatabase : dbTermList) {
            if (termInDatabase.getTermID() == term.getTermID()) {
                numOfOverlappingDates--;
                continue;
            }

            LocalDate dbStartDate = LocalDate.parse(termInDatabase.getStartDate());
            LocalDate dbEndDate = LocalDate.parse(termInDatabase.getEndDate());

            if (endDate.isEqual(startDate)) {
                return false;
            } else if (endDate.isBefore(dbStartDate)) {
                return false;
            } else if(startDate.isEqual(dbEndDate)) {
                return false;
            } else if (startDate.isAfter(dbEndDate)) {
                return false;
            }
            numOfOverlappingDates++;
        }

        if (numOfOverlappingDates <= 0 ) {
            return false;
        }
        return true;
    }

    public static boolean doesTermHaveCourses(int termId, List<Course> courseList) {
        int count = 0;

        for(Course course : courseList) {
            if(course.getTermID() == termId) {
                count++;
            }
        }

        if(count == 0) {
            return false;
        }
        return true;
    }

    public static ArrayList<Course> getAllCoursesForTerm(int termId, List<Course> courseList) {
        ArrayList<Course> coursesForTerm = new ArrayList<>();

        for(Course course : courseList) {
            if(course.getTermID() == termId) {
                coursesForTerm.add(course);
            }
        }
        return coursesForTerm;
    }
}
