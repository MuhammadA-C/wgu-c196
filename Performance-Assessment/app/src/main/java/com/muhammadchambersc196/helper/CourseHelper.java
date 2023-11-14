package com.muhammadchambersc196.helper;

import androidx.annotation.NonNull;

import com.muhammadchambersc196.entities.Course;
import com.muhammadchambersc196.entities.CourseNote;
import com.muhammadchambersc196.entities.Term;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CourseHelper {
    public static boolean areCourseDatesWithinRangeOfTermDates(Course course, int termId, ArrayList<Term> terms) {
        Term term = TermHelper.retrieveTermFromDatabaseByTermID(terms, termId);

        //This is needed because term could be null which would causes issues with the code below
        if (term == null) {
            return false;
        }

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
        } else if (courseStartDate.isEqual(termStartDate) && courseEndDate.isBefore(termEndDate)) {
            return true;
        } else if (courseEndDate.isEqual(termEndDate) && courseStartDate.isAfter(termStartDate)) {
            return true;
        } else if (courseStartDate.isEqual(termStartDate) && courseEndDate.isEqual(termStartDate)) {
            return true;
        } else if (courseStartDate.isEqual(termEndDate) && courseEndDate.isEqual(termEndDate)) {
            return true;
        }
        return false;
    }

    public static boolean doesCourseExistForTerm(ArrayList<Course> allCoursesForTerm, int termId, Course addCourse) {
        if (allCoursesForTerm == null) {
            return false;
        }

        /*
         Checks to see if the course already exists for the term by comparing the course names.
         The assumption here is that there shouldn't be duplicate courses for the same term.
        */
        for(Course course : allCoursesForTerm) {
            if(addCourse.getTitle().toLowerCase().equals(course.getTitle().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<Course> getAllCoursesForTerm(ArrayList<Course> courseListFromDatabase, int termId) {
        if (courseListFromDatabase.size() == 0) {
            return null;
        }

        ArrayList<Course> coursesForTerm = new ArrayList<>();

        for (Course course : courseListFromDatabase) {
            if (course.getTermID() == termId) {
                coursesForTerm.add(course);
            }
        }
        return coursesForTerm;
    }

    public static Course retrieveCourseFromDatabaseByCourseID(ArrayList<Course> dbCourseList, int courseId) {
        if (dbCourseList.size() == 0) {
            return null;
        }

        for (Course dbCourse : dbCourseList) {
            if (dbCourse.getCourseID() == courseId) {
                return dbCourse;
            }
        }
        return null;
    }

    private static boolean doesCourseHaveNotes(ArrayList<CourseNote> dbNoteList, int courseId) {
        int noteCount = 0;

        for(CourseNote dbNote : dbNoteList) {
            if(dbNote.getCourseID() == courseId) {
                noteCount++;
            }
        }

        if(noteCount == 0) {
            return false;
        }
        return true;
    }

    public static ArrayList<CourseNote> getAllNotesForCourse(ArrayList<CourseNote> dbNoteList, int courseId) {
        //If a course does NOT have any notes for it, then an empty array list will be returned
        if (!doesCourseHaveNotes(dbNoteList, courseId)) {
            return new ArrayList<>();
        }

        ArrayList<CourseNote> notesForCourse = new ArrayList<>();

        for(CourseNote dbNote : dbNoteList) {
            if(dbNote.getCourseID() == courseId) {
                notesForCourse.add(dbNote);
            }
        }
        return notesForCourse;
    }
}
