package com.muhammadchambersc196.helper;

import com.muhammadchambersc196.controller.HomeScreenActivity;
import com.muhammadchambersc196.controller.detailed.DetailedAssessmentActivity;
import com.muhammadchambersc196.controller.detailed.DetailedCourseActivity;
import com.muhammadchambersc196.controller.detailed.DetailedNoteActivity;
import com.muhammadchambersc196.controller.detailed.DetailedTermActivity;
import com.muhammadchambersc196.controller.list.ListOfTermsActivity;
import com.muhammadchambersc196.controller.update.UpdateAssessmentActivity;
import com.muhammadchambersc196.controller.update.UpdateNoteActivity;

public class SwitchScreen {
    public static final String CAME_FROM_KEY = "came_from";
    public static final String TERM_ID_KEY = "term_id";
    public static final String ADD_OR_UPDATE_SCREEN_KEY = "add_or_update_screen";
    public static final String COURSE_ID_KEY = "course_id";
    public static final String UPDATE_TERM_VALUE = "Update Term";
    public static final String UPDATE_COURSE_VALUE = "Update Course";
    public static final String ADD_TERM_VALUE = "Add Term";
    public static final String ADD_COURSE_VALUE = "Add Course";
    public static final String LIST_OF_TERMS_ACTIVITY = "ListOfTermsActivity";
    public static final String HOME_SCREEN_ACTIVITY = "HomeScreenActivity";
    public static final String DETAILED_TERM_ACTIVITY = "DetailedTermActivity";
    public static final String DETAILED_NOTE_ACTIVITY = "DetailedNoteActivity";
    public static final String DETAILED_COURSE_ACTIVITY = "DetailedCourseActivity";
    public static final String DETAILED_ASSESSMENT_ACTIVITY = "DetailedAssessmentActivity";
    public static final String UPDATE_ASSESSMENT_ACTIVITY = "UpdateAssessmentActivity";
    public static final String UPDATE_NOTE_ACTIVITY = "UpdateNoteActivity";


    public static Class getActivityClass(String activityName) {
        Class className = null;

        switch (activityName) {
            case HOME_SCREEN_ACTIVITY:
                className = HomeScreenActivity.class;
                break;
            case LIST_OF_TERMS_ACTIVITY:
                className = ListOfTermsActivity.class;
                break;
            case DETAILED_TERM_ACTIVITY:
                className = DetailedTermActivity.class;
                break;
            case DETAILED_NOTE_ACTIVITY:
                className = DetailedNoteActivity.class;
                break;
            case DETAILED_COURSE_ACTIVITY:
                className = DetailedCourseActivity.class;
                break;
            case DETAILED_ASSESSMENT_ACTIVITY:
                className = DetailedAssessmentActivity.class;
                break;
            case UPDATE_ASSESSMENT_ACTIVITY:
                className = UpdateAssessmentActivity.class;
                break;
            case UPDATE_NOTE_ACTIVITY:
                className = UpdateNoteActivity.class;
                break;
        }
        return className;
    }
}
