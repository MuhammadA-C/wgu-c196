package com.muhammadchambersc196.helper;

import com.muhammadchambersc196.entities.CourseNote;

import java.util.ArrayList;

public class CourseNoteHelper {
    public static CourseNote retrieveNoteFromDatabaseByNoteID(ArrayList<CourseNote> dbNoteList, int noteId) {
        if (dbNoteList.size() == 0) {
            return null;
        }

        for (CourseNote dbNote : dbNoteList) {
            if (dbNote.getCourseNoteID() == noteId) {
                return dbNote;
            }
        }
        return null;
    }

}
