package com.muhammadchambersc196.controller.create;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Surface;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.muhammadchambersc196.R;
import com.muhammadchambersc196.database.Repository;
import com.muhammadchambersc196.entities.Course;
import com.muhammadchambersc196.entities.CourseInstructor;
import com.muhammadchambersc196.entities.Term;
import com.muhammadchambersc196.helper.CourseHelper;
import com.muhammadchambersc196.helper.DateValidation;
import com.muhammadchambersc196.helper.InputValidation;
import com.muhammadchambersc196.helper.SwitchScreen;

import java.util.ArrayList;

public class CreateOrUpdateCourseActivity extends AppCompatActivity {
    Repository repository;
    String activityCameFrom;
    String activityCameFrom2;
    String addOrUpdate;
    int termId;
    EditText className;
    EditText classInfo;
    Spinner classStatus;
    Spinner selectInstructor;
    EditText startDate;
    EditText endDate;
    Button saveBtn;
    Button cancelBtn;
    Button addCIBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_update_course);
        repository = new Repository(getApplication());

        //Retrieves the intent that was passed to this activity/screen
        Intent intent = getIntent();
        //Retrieves the data value/string name that was passed to this intent
        activityCameFrom = intent.getStringExtra(SwitchScreen.CAME_FROM_KEY);

        setActivityCameFrom2(intent);
        setTermID(intent);
        setAddOrUpdate(intent);

        //Sets the action bar title of the screen to say "Add" or "Update" based on if it's supposed to be for adding or updating
        setTitle(addOrUpdate);

        //Gets references to the activities input fields
        classStatus = findViewById(R.id.create_class_select_status);
        className = findViewById(R.id.create_class_name);
        classInfo = findViewById(R.id.create_class_info);
        startDate = findViewById(R.id.create_class_start_date);
        endDate = findViewById(R.id.create_class_end_date);
        saveBtn = findViewById(R.id.create_class_save_btn);
        cancelBtn = findViewById(R.id.create_class_cancel_btn);
        addCIBtn = findViewById(R.id.create_class_add_ci_btn);
        selectInstructor = findViewById(R.id.create_class_select_ci);

        //Sets the class status spinner
        classStatus.setAdapter(createStatusListAdapter());
        //Sets the course instructor spinner
        selectInstructor.setAdapter(createInstructorListAdapter());


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addOrUpdate.equals(SwitchScreen.ADD_COURSE_VALUE)) {
                    //Checks if there are any empty input fields
                    if (InputValidation.isInputFieldEmpty(className) ||InputValidation.isInputFieldEmpty(classInfo) || InputValidation.isInputFieldEmpty(classStatus) ||
                            InputValidation.isInputFieldEmpty(startDate) || InputValidation.isInputFieldEmpty(endDate))  {
                        return;
                    }

                    //Checks if the classes start and end dates are formatted correctly (yyyy-mm-dd)
                    if (!DateValidation.isDateFormattedCorrect(startDate.getText().toString()) || !DateValidation.isDateFormattedCorrect(endDate.getText().toString())) {
                        return;
                    }

                    //Checks if the classes start date is the same or before the classes end date
                    if (!DateValidation.isStartDateTheSameOrBeforeEndDate(startDate.getText().toString(), endDate.getText().toString())) {
                        return;
                    }

                    Course addCourse = new Course(className.getText().toString(), classStatus.getSelectedItem().toString(), classInfo.getText().toString(), startDate.getText().toString(), endDate.getText().toString(), termId, ((CourseInstructor) selectInstructor.getSelectedItem()).getInstructorID());

                    try {
                        //Course start and end dates must be within range of the terms start and end dates
                        if (!CourseHelper.areCourseDatesWithinRangeOfTermDates(addCourse, termId, (ArrayList<Term>) repository.getmAllTerms())) {
                            return;
                        }

                        /*
                            Checks to see if the course already exists for the term by comparing the course names.
                            The assumption here is that there shouldn't be duplicate courses for the same term.
                         */
                        if (CourseHelper.doesCourseExistForTerm(CourseHelper.getAllCoursesForTerm ((ArrayList<Course>) repository.getmAllCourses(), termId), termId, addCourse)) {
                            return;
                        }

                        //Adds new course to the database
                        repository.insert(addCourse);

                        switchScreen(SwitchScreen.getActivityClass(activityCameFrom2), SwitchScreen.TERM_ID_KEY, String.valueOf(termId));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    //This part will be for updating a course

                    switchScreen(SwitchScreen.getActivityClass(activityCameFrom), SwitchScreen.TERM_ID_KEY, String.valueOf(termId), SwitchScreen.COURSE_ID_KEY, intent.getStringExtra(SwitchScreen.COURSE_ID_KEY));
                }
            }
        });


        addCIBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                    Need this check because if the user bounces back and forth from the add instructor screen
                    to the add course screen, the variable "activityCameFrom" will hold a reference to the wrong screen.
                 */
                if (activityCameFrom.equals(SwitchScreen.CREATE_OR_UPDATE_INSTRUCTOR_ACTIVITY)) {
                    activityCameFrom = activityCameFrom2;
                }
                switchScreen(CreateOrUpdateInstructorActivity.class, SwitchScreen.CAME_FROM_KEY, SwitchScreen.CREATE_OR_UPDATE_COURSE_ACTIVITY, SwitchScreen.CAME_FROM_KEY2, activityCameFrom, SwitchScreen.ADD_OR_UPDATE_SCREEN_KEY, SwitchScreen.ADD_INSTRUCTOR_VALUE, SwitchScreen.TERM_ID_KEY, String.valueOf(termId));
            }
        });


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                    Check is needed to set the "activityCameFrom" variable with a reference to the
                    activity that triggered the create or update course activity.
                 */
                if (activityCameFrom.equals(SwitchScreen.CREATE_OR_UPDATE_INSTRUCTOR_ACTIVITY)) {
                    activityCameFrom = activityCameFrom2;
                    System.out.println("Hit 1");
                }



                System.out.println("Create Course; Activity came from 2 " + activityCameFrom2);

                if (activityCameFrom.equals(SwitchScreen.DETAILED_COURSE_ACTIVITY)) {
                    System.out.println("Hit 2");
                    System.out.println("Create Course; Activity came from 1 " + activityCameFrom);
                    System.out.println("Create Course; Course  ID " + intent.getStringExtra(SwitchScreen.COURSE_ID_KEY));
                    /*
                        Course ID is null. I need to pass in course id to the add instructor page, and the add instructor page needs to pass the course id back
                     */

                    switchScreen(SwitchScreen.getActivityClass(activityCameFrom), SwitchScreen.TERM_ID_KEY, String.valueOf(termId), SwitchScreen.COURSE_ID_KEY, intent.getStringExtra(SwitchScreen.COURSE_ID_KEY));
                } else {
                    System.out.println("Hit 3");
                    switchScreen(SwitchScreen.getActivityClass(activityCameFrom), SwitchScreen.TERM_ID_KEY, String.valueOf(termId));
                }
            }
        });
    }

    ArrayAdapter<String> createStatusListAdapter() {
        ArrayList<String> statusOptionsList = new ArrayList<>();

        statusOptionsList.add("Not Started");
        statusOptionsList.add("In-Progress");
        statusOptionsList.add("Completed");
        statusOptionsList.add("Failed");

        return new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusOptionsList);
    }

    ArrayAdapter<CourseInstructor> createInstructorListAdapter() {
        ArrayList<CourseInstructor> instructorOptionsList = null;

        try {
            instructorOptionsList = (ArrayList<CourseInstructor>) repository.getmAllCourseInstructors();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, instructorOptionsList);
    }

    void switchScreen(Class className, String idKey, String idValue) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, className);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(idKey, idValue);
        //Need to always start the activity that you're going to
        startActivity(intent);
    }

    void switchScreen(Class className, String idKey1, String idValue1, String idKey2, String idValue2) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, className);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(idKey1, idValue1);
        intent.putExtra(idKey2, idValue2);
        //Need to always start the activity that you're going to
        startActivity(intent);
    }

    void switchScreen(Class className, String cameFromKey1, String cameFromValue1, String cameFromKey2, String cameFromValue2, String addOrUpdateScreenKey, String addOrUpdateScreenValue, String idKey, String idValue) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, className);

        //Specifies the data to pass to the new activity/screen
        intent.putExtra(cameFromKey1, cameFromValue1);
        intent.putExtra(cameFromKey2, cameFromValue2);
        intent.putExtra(addOrUpdateScreenKey, addOrUpdateScreenValue);
        intent.putExtra(idKey, idValue);
        //Need to always start the activity that you're going to
        startActivity(intent);
    }

    void setScreenInfo() throws InterruptedException {
        if (addOrUpdate.equals(SwitchScreen.ADD_COURSE_VALUE)) {
            return;
        }

        Course course = CourseHelper.retrieveCourseFromDatabaseByCourseID((ArrayList<Course>) repository.getmAllCourses(), termId);

        if (course == null) {
            return;
        }

        className.setText(course.getTitle());
        classInfo.setText(course.getInformation());
        startDate.setText(course.getStartDate());
        endDate.setText(course.getEndDate());
    }

    void setAddOrUpdate(Intent intent) {
        if (activityCameFrom.equals(SwitchScreen.CREATE_OR_UPDATE_INSTRUCTOR_ACTIVITY)) {
            addOrUpdate = SwitchScreen.ADD_COURSE_VALUE;
        } else {
            addOrUpdate = intent.getStringExtra(SwitchScreen.ADD_OR_UPDATE_SCREEN_KEY);
        }
    }

    void setTermID(Intent intent) {
        if (activityCameFrom.equals(SwitchScreen.DETAILED_COURSE_ACTIVITY)) {
            try {
                termId =  (CourseHelper.retrieveCourseFromDatabaseByCourseID((ArrayList<Course>) repository.getmAllCourses(), Integer.valueOf(intent.getStringExtra(SwitchScreen.COURSE_ID_KEY))).getTermID());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            termId = Integer.valueOf(intent.getStringExtra(SwitchScreen.TERM_ID_KEY));
        }
    }

    void setActivityCameFrom2(Intent intent) {
        if (activityCameFrom.equals(SwitchScreen.DETAILED_COURSE_ACTIVITY)) {
            activityCameFrom2 = activityCameFrom;
        } else {
            activityCameFrom2 = intent.getStringExtra(SwitchScreen.CAME_FROM_KEY2);
        }
    }
}