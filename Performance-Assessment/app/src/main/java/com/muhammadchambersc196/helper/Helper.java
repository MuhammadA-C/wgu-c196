package com.muhammadchambersc196.helper;

import androidx.annotation.NonNull;

import com.muhammadchambersc196.database.Repository;
import com.muhammadchambersc196.entities.Assessment;
import com.muhammadchambersc196.entities.Course;
import com.muhammadchambersc196.entities.CourseInstructor;
import com.muhammadchambersc196.entities.CourseNote;
import com.muhammadchambersc196.entities.Term;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Helper {
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

    //Method below will be used to prevent duplicate terms from being added to the database
    public static boolean isTermInDatabase(Term valueToAdd, List<Term> termList) {
        for(Term termInList : termList) {
            if(valueToAdd.getTitle().toLowerCase().equals(termInList.getTitle().toLowerCase())) {
                return true;
            }
        }
        return false;
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

    //Method below will be used to prevent duplicate courses for the same term
    //Note: The user most likely shouldn't be able to add a duplicate course for the same term
    public static boolean doesCourseExistForTerm(Course valueToAdd, ArrayList<Course> listOfCoursesForTerm) {
        for(Course course : listOfCoursesForTerm) {
            if(valueToAdd.getTitle().toLowerCase().equals(course.getTitle().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static boolean doesTermExistInDatabase(@NonNull ArrayList<Term> databaseListOfTerms, Term term) {
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

    public static boolean doesTermDateOverlapWithTermInDatabase(@NonNull ArrayList<Term> databaseListOfTerms, Term term) {
        if (databaseListOfTerms.size() == 0) {
            return false;
        }

        LocalDate startDate = LocalDate.parse(term.getStartDate());
        LocalDate endDate = LocalDate.parse(term.getEndDate());

        int numOfOverlappingDates = 0;

        for (Term termInDatabase : databaseListOfTerms) {
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

    public static Term retrieveTermFromDatabaseByTermID(@NonNull ArrayList<Term> databaseListOfTerms, int termId) {
        for (Term term : databaseListOfTerms) {
            if (term.getTermID() == termId) {
                return term;
            }
        }
        return null;
    }


    /*
        Note:
            - Need to add code to check if the end date is on the same day or after the start date

     */

}
