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
import com.muhammadchambersc196.entities.Assessment;
import com.muhammadchambersc196.helper.AssessmentHelper;
import com.muhammadchambersc196.helper.DateValidation;
import com.muhammadchambersc196.helper.InputValidation;
import com.muhammadchambersc196.helper.SwitchScreen;

import java.util.ArrayList;

public class CreateOrUpdateAssessmentActivity extends AppCompatActivity {
    Repository repository;
    int courseId;
    int assessmentId;
    EditText assessmentName;
    EditText assessmentInfo;
    EditText startDate;
    EditText endDate;
    Spinner assessmentType;
    Button saveBtn;
    Button cancelBtn;
    String addOrUpdate;
    ArrayList<Assessment> dbAssessmentList;
    Assessment assessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_update_assessment);

        repository = new Repository(getApplication());

        try {
            setDatabaseLists();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //Retrieves the intent that was passed to this activity/screen
        Intent intent = getIntent();
        //Retrieves the data value/string name that was passed to this intent
        String activityCameFrom = intent.getStringExtra(SwitchScreen.CAME_FROM_KEY);

        addOrUpdate = intent.getStringExtra(SwitchScreen.ADD_OR_UPDATE_SCREEN_KEY);

        //Gets references to the activities input fields
        assessmentName = findViewById(R.id.create_assessment_name);
        assessmentInfo = findViewById(R.id.create_assessment_info);
        startDate = findViewById(R.id.create_assessment_start_date);
        endDate = findViewById(R.id.create_assessment_end_date);
        assessmentType = findViewById(R.id.create_assessment_type);
        saveBtn = findViewById(R.id.create_assessment_save_btn);
        cancelBtn = findViewById(R.id.create_assessment_cancel_btn);

        setAssessmentId(intent);
        setAssessment();
        setCourseId(intent);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Checks to ensure that the input fields are NOT empty
                if (InputValidation.isInputFieldEmpty(assessmentName) || InputValidation.isInputFieldEmpty(assessmentInfo) ||
                InputValidation.isInputFieldEmpty(startDate) || InputValidation.isInputFieldEmpty(endDate) ||
                InputValidation.isInputFieldEmpty(assessmentType)) {
                    return;
                    //Need to add a check for the spinner/class status input field
                }

                //Checks to ensure that the start and end dates are formatted correctly
                if (!DateValidation.isDateFormattedCorrect(startDate.getText().toString()) ||
                        !DateValidation.isDateFormattedCorrect(endDate.getText().toString())) {
                    return;
                }

                //Checks to ensure start date is before the end date
                if (!DateValidation.isStartDateBeforeEndDate(startDate.getText().toString(), endDate.getText().toString())) {
                    return;
                }

                /*
                    NOTE: Need to add a check to ensure assessment start date is the same or after course start date, but before course end date.
                    And assessment end date the same or before course end date.
                 */


                if (addOrUpdate.equals(SwitchScreen.ADD_ASSESSMENT_VALUE)) {
                    Assessment addAssessment = new Assessment(assessmentName.getText().toString(), assessmentType.getSelectedItem().toString(),
                            assessmentInfo.getText().toString(), startDate.getText().toString(), endDate.getText().toString(), courseId);

                    try {
                        //Adds new assessment to the database
                        repository.insert(addAssessment);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    switchScreen(SwitchScreen.getActivityClass(activityCameFrom));

                } else {
                   //Assessment updateAssessment = AssessmentHelper.retrieveAssessmentFromDatabaseByAssessmentID()

                }
            }
        });
    }

    void setDatabaseLists() throws InterruptedException {
        dbAssessmentList = (ArrayList<Assessment>) repository.getmAllAssessments();
    }

    void setCourseId(Intent intent) {
        if (addOrUpdate.equals(SwitchScreen.ADD_ASSESSMENT_VALUE)) {
            courseId = Integer.valueOf(intent.getStringExtra(SwitchScreen.COURSE_ID_KEY));
        } else {
            courseId = assessment.getCourseID();
        }
    }

    void setAssessmentId(Intent intent) {
        if (addOrUpdate.equals(SwitchScreen.ADD_ASSESSMENT_VALUE)) {
            return;
        }

        assessmentId = Integer.valueOf(intent.getStringExtra(SwitchScreen.ASSESSMENT_ID_KEY));
    }

    void setAssessment() {
        assessment = AssessmentHelper.retrieveAssessmentFromDatabaseByAssessmentID(dbAssessmentList, assessmentId);
    }

    void switchScreen(Class className) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, className);
        //Need to always start the activity that you're going to
        startActivity(intent);
    }


}