package com.muhammadchambersc196.helper;

import com.muhammadchambersc196.entities.Course;
import com.muhammadchambersc196.entities.Term;

public class SelectedListItem {
    private static Term selectedTerm = null;
    private static Course selectedCourse = null;

    public static void setSelectedTerm(Term term) {
        selectedTerm = term;
    }

    public static Term getSelectedTerm() {
        return selectedTerm;
    }

    public static void setSelectedCourse(Course course) {
        selectedCourse = course;
    }
    public static Course getSelectedCourse() {
        return selectedCourse;
    }
}
