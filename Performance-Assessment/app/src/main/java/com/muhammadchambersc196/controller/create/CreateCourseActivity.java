package com.muhammadchambersc196.controller.create;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.muhammadchambersc196.R;
import com.muhammadchambersc196.helper.DateValidation;
import com.muhammadchambersc196.helper.InputValidation;
import com.muhammadchambersc196.helper.SwitchScreen;

public class CreateCourseActivity extends AppCompatActivity {
    //Note: Need to correctly set the course id by taking the value passed from the course page
    int termId;
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
        setContentView(R.layout.activity_create_course);

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

                goToNewScreen(SwitchScreen.getActivityClass(activityCameFrom));

            }
        });
    }

    void goToNewScreen(Class className) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, className);
        //Need to always start the activity that you're going to
        startActivity(intent);
    }
}