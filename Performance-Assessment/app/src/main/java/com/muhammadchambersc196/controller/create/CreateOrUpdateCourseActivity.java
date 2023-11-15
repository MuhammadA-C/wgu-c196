package com.muhammadchambersc196.controller.create;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.muhammadchambersc196.helper.StatusSpinnerHelper;
import com.muhammadchambersc196.helper.SwitchScreen;

import java.util.ArrayList;

public class CreateOrUpdateCourseActivity extends AppCompatActivity {
    Repository repository;
    String activityCameFrom;
    String activityCameFrom2;
    String addOrUpdate;
    int termId;
    int courseId;
    EditText className;
    EditText classInfo;
    Spinner classStatus;
    Spinner selectInstructor;
    EditText startDate;
    EditText endDate;
    Button saveBtn;
    Button cancelBtn;
    Button addCIBtn;
    ArrayList<Course> dbCourseList;
    ArrayList<CourseInstructor> dbInstructorList;
    ArrayList<Term> dbTermList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_update_course);
        repository = new Repository(getApplication());

        //Storing a reference to the database lists that are used to reduce calling them due to crashing app
        try {
            setDatabaseListVariables();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //Retrieves the intent that was passed to this activity/screen
        Intent intent = getIntent();
        //Retrieves the data value/string name that was passed to this intent
        activityCameFrom = intent.getStringExtra(SwitchScreen.CAME_FROM_KEY);

        setActivityCameFrom2(intent);
        setAddOrUpdate(intent);
        /*
            Bug = course id is null sometimes, need to check why
         */
        setCourseId(intent);
        setTermID(intent);
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

       setSpinnerSelectedInstructor();
       setScreenInfo();


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InputValidation.isInputFieldEmpty(className) || InputValidation.isInputFieldEmpty(classInfo) ||
                        InputValidation.isInputFieldEmpty(classStatus) || InputValidation.isInputFieldEmpty(selectInstructor) ||
                        InputValidation.isInputFieldEmpty(startDate) || InputValidation.isInputFieldEmpty(endDate)) {
                    //Checks to ensure that the input fields are NOT empty
                    return;
                } else if (!DateValidation.isDateFormattedCorrect(startDate.getText().toString()) ||
                        !DateValidation.isDateFormattedCorrect(endDate.getText().toString())) {
                    //Checks to ensure that the start and end dates are formatted correctly
                    return;
                } else if (!DateValidation.isStartDateTheSameOrBeforeEndDate(startDate.getText().toString(), endDate.getText().toString())) {
                    //Checks if the classes start date is the same or before the classes end date
                    return;
                }

                Course saveCourse = getCourseForAddOrUpdate();

                if (!CourseHelper.areCourseDatesWithinRangeOfTermDates(saveCourse, termId, dbTermList)) {
                    //Course start and end dates must be within range of the terms start and end dates
                    return;
                } else if (CourseHelper.doesCourseExistForTerm(CourseHelper.getAllCoursesForTerm (dbCourseList, termId), termId, saveCourse)) {
                   /*
                        Checks to see if the course already exists for the term by comparing the course names.
                        The assumption here is that there shouldn't be duplicate courses for the same term.
                    */
                    return;
                }

                //Check is needed to set the correct reference to the screen that called the create or update course activity
                if (activityCameFrom.equals(SwitchScreen.CREATE_OR_UPDATE_INSTRUCTOR_ACTIVITY)) {
                    activityCameFrom = activityCameFrom2;
                }

                if (addOrUpdate.equals(SwitchScreen.ADD_COURSE_VALUE)) {
                    try {
                        //Adds new course to the database
                        repository.insert(saveCourse);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    switchScreen(SwitchScreen.getActivityClass(activityCameFrom), SwitchScreen.TERM_ID_KEY, String.valueOf(termId));

                } else {
                    try {
                        //Saves the updated course values in the database
                        repository.update(saveCourse);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    switchScreen(SwitchScreen.getActivityClass(activityCameFrom), SwitchScreen.TERM_ID_KEY,
                            String.valueOf(termId), SwitchScreen.COURSE_ID_KEY, String.valueOf(courseId));
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

                switchScreen(CreateOrUpdateInstructorActivity.class, SwitchScreen.CAME_FROM_KEY, SwitchScreen.CREATE_OR_UPDATE_COURSE_ACTIVITY,
                        SwitchScreen.CAME_FROM_KEY2, activityCameFrom, SwitchScreen.ADD_OR_UPDATE_SCREEN_KEY, SwitchScreen.ADD_INSTRUCTOR_VALUE,
                        SwitchScreen.CAME_FROM_ADD_OR_UPDATE_SCREEN_KEY, addOrUpdate, SwitchScreen.TERM_ID_KEY, String.valueOf(termId),
                        SwitchScreen.COURSE_ID_KEY, String.valueOf(courseId));
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
                }

                if (activityCameFrom.equals(SwitchScreen.DETAILED_COURSE_ACTIVITY)) {
                    switchScreen(SwitchScreen.getActivityClass(activityCameFrom), SwitchScreen.TERM_ID_KEY,
                            String.valueOf(termId), SwitchScreen.COURSE_ID_KEY, String.valueOf(courseId));
                } else {
                    switchScreen(SwitchScreen.getActivityClass(activityCameFrom), SwitchScreen.TERM_ID_KEY, String.valueOf(termId));
                }
            }
        });
    }

    void setDatabaseListVariables() throws InterruptedException {
        /*
            Application was crashing when testing and I found the root cause to be due to all of the database calls for this
            screen which is slowing down the application, which causes it to freeze and stop responding.

            Fix#1: Created this method to store references to the database lists that I use for this screen to reduce
            the amount of database calls to retrieve the database lists

            Note: I could also decrease the Thread.sleep() time in the repository method
         */
        dbCourseList = (ArrayList<Course>) repository.getmAllCourses();
        dbInstructorList = (ArrayList<CourseInstructor>) repository.getmAllCourseInstructors();
        dbTermList = (ArrayList<Term>) repository.getmAllTerms();
    }

    ArrayAdapter<String> createStatusListAdapter() {
        ArrayList<String> statusOptionsList = new ArrayList<>();

        //Spinner stores the items in the position that they were added with the same index as the list
        statusOptionsList.add(StatusSpinnerHelper.PLAN_TO_TAKE);
        statusOptionsList.add(StatusSpinnerHelper.IN_PROGRESS);
        statusOptionsList.add(StatusSpinnerHelper.COMPLETED);
        statusOptionsList.add(StatusSpinnerHelper.DROPPED);

        return new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusOptionsList);
    }

    int getSpinnerStatusPosition(String status) {
        /*
            Note: Due to the current design for this, this method will need to be updated if
            the order of the items for the status spinner is changed; or more are added.
         */
        switch (status) {
            case StatusSpinnerHelper.PLAN_TO_TAKE:
                return 0;
            case StatusSpinnerHelper.IN_PROGRESS:
                return 1;
            case StatusSpinnerHelper.COMPLETED:
                return 2;
            case StatusSpinnerHelper.DROPPED:
                return 3;
        }
        return -1;
    }

    ArrayAdapter<CourseInstructor> createInstructorListAdapter() {
        return new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dbInstructorList);
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

    void switchScreen(Class className, String cameFromKey1, String cameFromValue1, String cameFromKey2,
                      String cameFromValue2, String addOrUpdateScreenKey, String addOrUpdateScreenValue,
                      String cameFromAddOrUpdateScreenKey, String cameFromAddOrUpdateScreenValue, String idKey1,
                      String idValue1, String idKey2, String idValue2) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, className);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(cameFromKey1, cameFromValue1);
        intent.putExtra(cameFromKey2, cameFromValue2);
        intent.putExtra(addOrUpdateScreenKey, addOrUpdateScreenValue);
        intent.putExtra(cameFromAddOrUpdateScreenKey, cameFromAddOrUpdateScreenValue);
        intent.putExtra(idKey1, idValue1);
        intent.putExtra(idKey2, idValue2);
        //Need to always start the activity that you're going to
        startActivity(intent);
    }

    void setAddOrUpdate(Intent intent) {
        if (activityCameFrom.equals(SwitchScreen.CREATE_OR_UPDATE_INSTRUCTOR_ACTIVITY)) {
            addOrUpdate = intent.getStringExtra(SwitchScreen.CAME_FROM_ADD_OR_UPDATE_SCREEN_KEY);
        } else {
            addOrUpdate = intent.getStringExtra(SwitchScreen.ADD_OR_UPDATE_SCREEN_KEY);
        }
    }

    void setCourseId(Intent intent) {
        if (!activityCameFrom.equals(SwitchScreen.DETAILED_TERM_ACTIVITY)) {
            courseId = Integer.valueOf(intent.getStringExtra(SwitchScreen.COURSE_ID_KEY));
        }
    }

    void setTermID(Intent intent) {
        if (activityCameFrom.equals(SwitchScreen.DETAILED_COURSE_ACTIVITY)) {
            termId =  (CourseHelper.retrieveCourseFromDatabaseByCourseID(dbCourseList, courseId).getTermID());
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

    void setSpinnerSelectedInstructor() {
        //Below check can be moved if I place this inside of another method which sets all of the input fields, and have the check in that bigger method
        if (!activityCameFrom.equals(SwitchScreen.DETAILED_COURSE_ACTIVITY)) {
            return;
        }
        int instructorIDForCourse = CourseHelper.retrieveCourseFromDatabaseByCourseID(dbCourseList, courseId).getInstructorID();

        selectInstructor.setSelection(getSpinnerSelectedInstructorPosition(instructorIDForCourse));
    }

    Course getCourseForAddOrUpdate() {
        if (addOrUpdate.equals(SwitchScreen.ADD_COURSE_VALUE)) {
            return new Course(className.getText().toString(), classStatus.getSelectedItem().toString(),
                    classInfo.getText().toString(), startDate.getText().toString(), endDate.getText().toString(),
                    termId, ((CourseInstructor) selectInstructor.getSelectedItem()).getInstructorID());
        }

        Course updateCourse = CourseHelper.retrieveCourseFromDatabaseByCourseID(dbCourseList, courseId);

        updateCourse.updateFields(className, classInfo, classStatus, selectInstructor, startDate, endDate);

        return updateCourse;
    }

    int getSpinnerSelectedInstructorPosition(int instructorIDForCourse) {
        if (dbInstructorList.size() == 0) {
            return -1;
        }

        int instructorIndex = 0;
        for (CourseInstructor dbInstructor : dbInstructorList) {
            if (dbInstructor.getInstructorID() == instructorIDForCourse) {
                return instructorIndex;
            }
            instructorIndex++;
        }
        return -1;
    }

    void setScreenInfo() {
        if (addOrUpdate.equals(SwitchScreen.ADD_COURSE_VALUE)) {
            return;
        }

        Course course = CourseHelper.retrieveCourseFromDatabaseByCourseID(dbCourseList, courseId);

        className.setText(course.getTitle());
        classInfo.setText(course.getInformation());
        classStatus.setSelection(getSpinnerStatusPosition(course.getStatus()));
        startDate.setText(course.getStartDate());
        endDate.setText(course.getEndDate());
    }
}