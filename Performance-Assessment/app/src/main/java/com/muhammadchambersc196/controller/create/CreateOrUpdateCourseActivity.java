package com.muhammadchambersc196.controller.create;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.muhammadchambersc196.R;
import com.muhammadchambersc196.database.Repository;
import com.muhammadchambersc196.entities.Course;
import com.muhammadchambersc196.entities.Term;
import com.muhammadchambersc196.helper.CourseHelper;
import com.muhammadchambersc196.helper.DateValidation;
import com.muhammadchambersc196.helper.InputValidation;
import com.muhammadchambersc196.helper.SwitchScreen;

import java.util.ArrayList;

/*
    Need to create the spinner for the class status
 */

public class CreateOrUpdateCourseActivity extends AppCompatActivity {
    Repository repository;
    EditText className;
    EditText classInfo;
    Spinner classStatus;
    EditText startDate;
    EditText endDate;
    EditText instructorName;
    EditText instructorEmail;
    EditText instructorPhoneNumber;
    Button saveBtn;
    Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_update_course);
        repository = new Repository(getApplication());

        className = findViewById(R.id.create_class_name);
        classInfo = findViewById(R.id.create_class_info);
        classStatus = findViewById(R.id.create_class_status);
        startDate = findViewById(R.id.create_class_start_date);
        endDate = findViewById(R.id.create_class_end_date);
        instructorName = findViewById(R.id.create_class_ci_name);
        instructorEmail = findViewById(R.id.create_class_ci_email);
        instructorPhoneNumber = findViewById(R.id.create_class_ci_phone_number);
        saveBtn = findViewById(R.id.create_class_save_btn);
        cancelBtn = findViewById(R.id.create_class_cancel_btn);

        //Retrieves the intent that was passed to this activity/screen
        Intent intent = getIntent();
        //Retrieves the data value/string name that was passed to this intent
        String activityCameFrom = intent.getStringExtra(SwitchScreen.CAME_FROM_KEY);
        String addOrUpdate = intent.getStringExtra(SwitchScreen.ADD_OR_UPDATE_SCREEN_KEY);
        int termId = Integer.valueOf(intent.getStringExtra(SwitchScreen.TERM_ID_KEY));

        //Sets the action bar title of the screen to say "Add" or "Update" based on if it's supposed to be for adding or updating
        setTitle(addOrUpdate);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InputValidation.isInputFieldEmpty(className) ||InputValidation.isInputFieldEmpty(classInfo) || InputValidation.isInputFieldEmpty(classStatus) ||
                InputValidation.isInputFieldEmpty(startDate) || InputValidation.isInputFieldEmpty(endDate) || InputValidation.isInputFieldEmpty(instructorName) ||
                InputValidation.isInputFieldEmpty(instructorEmail) || InputValidation.isInputFieldEmpty(instructorPhoneNumber))  {
                    return;
                }

                if (!DateValidation.isDateFormattedCorrect(startDate.getText().toString()) || DateValidation.isDateFormattedCorrect(endDate.getText().toString())) {
                    return;
                }

                if (!DateValidation.isStartDateTheSameOrBeforeEndDate(startDate.getText().toString(), endDate.getText().toString())) {
                   return;
                }
                /*
                    NOTE: Need to add a check to ensure course start date is the same or after term start date, but before term end date.
                    And course end date the same or before term end date.
                 */

                /*
                    1. Need to create the course object
                    2. Need to add the course object to the database
                 */

                Course addCourse = new Course(className.getText().toString(), classStatus.getSelectedItem().toString(), classInfo.getText().toString(), startDate.getText().toString(), endDate.getText().toString(), termId);

                //Need to test code below
                try {
                    if (!CourseHelper.areCourseDatesWithinRangeOfTermDates(addCourse, termId, (ArrayList<Term>) repository.getmAllTerms())) {
                        System.out.println("Class start and end dates must be within Terms start and end dates");
                        return;
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                switchScreen(SwitchScreen.getActivityClass(activityCameFrom), SwitchScreen.TERM_ID_KEY, String.valueOf(termId));
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchScreen(SwitchScreen.getActivityClass(activityCameFrom), SwitchScreen.TERM_ID_KEY, String.valueOf(termId));
            }
        });
    }

    void switchScreen(Class className, String termIdKey, String termIdValue) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, className);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(termIdKey, termIdValue);
        //Need to always start the activity that you're going to
        startActivity(intent);
    }
}