package com.muhammadchambersc196.helper;

import com.muhammadchambersc196.controller.HomeScreenActivity;
import com.muhammadchambersc196.controller.create.CreateAssessmentActivity;
import com.muhammadchambersc196.controller.create.CreateCourseActivity;
import com.muhammadchambersc196.controller.create.CreateNoteActivity;
import com.muhammadchambersc196.controller.create.CreateTermActivity;
import com.muhammadchambersc196.controller.detailed.DetailedAssessmentActivity;
import com.muhammadchambersc196.controller.detailed.DetailedCourseActivity;
import com.muhammadchambersc196.controller.detailed.DetailedNoteActivity;
import com.muhammadchambersc196.controller.detailed.DetailedTermActivity;
import com.muhammadchambersc196.controller.list.ListOfTermsActivity;
import com.muhammadchambersc196.controller.update.UpdateAssessmentActivity;
import com.muhammadchambersc196.controller.update.UpdateCourseActivity;
import com.muhammadchambersc196.controller.update.UpdateNoteActivity;
import com.muhammadchambersc196.controller.update.UpdateTermActivity;

public class SwitchScreen {
    public static final String CAME_FROM = "came_from";
    public static final String LIST_OF_TERMS_ACTIVITY = "ListOfTermsActivity";
    public static final String  HOME_SCREEN_ACTIVITY = "HomeScreenActivity";
    public static final String DETAILED_TERM_ACTIVITY = "DetailedTermActivity";
    public static final String DETAILED_NOTE_ACTIVITY = "DetailedNoteActivity";
    public static final String DETAILED_COURSE_ACTIVITY = "DetailedCourseActivity";
    public static final String DETAILED_ASSESSMENT_ACTIVITY = "DetailedAssessmentActivity";
    public static final String UPDATE_ASSESSMENT_ACTIVITY = "UpdateAssessmentActivity";
    public static final String UPDATE_COURSE_ACTIVITY = "UpdateCourseActivity";
    public static final String UPDATE_NOTE_ACTIVITY = "UpdateNoteActivity";
    public static final String UPDATE_TERM_ACTIVITY = "UpdateTermActivity";
    public static final String CREATE_ASSESSMENT_ACTIVITY = "CreateAssessmentActivity";
    public static final String CREATE_COURSE_ACTIVITY = "CreateCourseActivity";
    public static final String CREATE_NOTE_ACTIVITY = "CreateNoteActivity";
    public static final String  CREATE_TERM_ACTIVITY = "CreateTermActivity";






    public static Class getActvityClass(String activityName) {
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
            case UPDATE_COURSE_ACTIVITY:
                className = UpdateCourseActivity.class;
                break;
            case UPDATE_NOTE_ACTIVITY:
                className = UpdateNoteActivity.class;
                break;
            case UPDATE_TERM_ACTIVITY:
                className = UpdateTermActivity.class;
                break;
            case CREATE_TERM_ACTIVITY:
                className = CreateTermActivity.class;
                break;
            case CREATE_ASSESSMENT_ACTIVITY:
                className = CreateAssessmentActivity.class;
                break;
            case CREATE_COURSE_ACTIVITY:
                className = CreateCourseActivity.class;
                break;
            case CREATE_NOTE_ACTIVITY:
                className = CreateNoteActivity.class;
                break;
        }
        return className;
    }
}
