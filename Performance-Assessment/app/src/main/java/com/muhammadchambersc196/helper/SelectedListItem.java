package com.muhammadchambersc196.helper;

import com.muhammadchambersc196.entities.Course;
import com.muhammadchambersc196.entities.CourseInstructor;
import com.muhammadchambersc196.entities.Term;

public class SelectedListItem {
    private static Term selectedTerm = null;
    private static Course selectedCourse = null;
    private static CourseInstructor selectedInstructor = null;

    public static void setSelectedTerm(Term term) {
        selectedTerm = term;
    }

    public static void setSelectedCourse(Course course) {
        selectedCourse = course;
    }

    public static void setSelectedInstructor(CourseInstructor instructor) {
        selectedInstructor = instructor;
    }

    public static Term getSelectedTerm() {
        return selectedTerm;
    }

    public static CourseInstructor getSelectedInstructor() {
        return selectedInstructor;
    }

    public static Course getSelectedCourse() {
        return selectedCourse;
    }
}
