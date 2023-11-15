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

        assessmentType.setAdapter(createAssessmentTypeListAdapter());

        setTitle(addOrUpdate);
        setScreenInfo();


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addOrUpdate.equals(SwitchScreen.ADD_ASSESSMENT_VALUE)) {
                    switchScreen(SwitchScreen.getActivityClass(activityCameFrom), SwitchScreen.COURSE_ID_KEY, String.valueOf(courseId));
                } else {
                    switchScreen(SwitchScreen.getActivityClass(activityCameFrom), SwitchScreen.ASSESSMENT_ID_KEY, String.valueOf(assessmentId));
                }
            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InputValidation.isInputFieldEmpty(assessmentName) || InputValidation.isInputFieldEmpty(assessmentInfo) ||
                InputValidation.isInputFieldEmpty(startDate) || InputValidation.isInputFieldEmpty(endDate) ||
                InputValidation.isInputFieldEmpty(assessmentType)) {
                    //Checks to ensure that the input fields are NOT empty
                    return;
                } else if (!DateValidation.isDateANumber(startDate.getText().toString())) {
                    //Checks to ensure that the year, month, and date are numbers
                    return;
                } else if (!DateValidation.isDateANumber(endDate.getText().toString())) {
                    //Checks to ensure that the year, month, and date are numbers
                    return;

                } else if (!DateValidation.isDateFormattedCorrect(startDate.getText().toString()) ||
                        !DateValidation.isDateFormattedCorrect(endDate.getText().toString())) {
                    //Checks to ensure that the start and end dates are formatted correctly
                    return;
                }  else if (!DateValidation.isStartDateTheSameOrBeforeEndDate(startDate.getText().toString(), endDate.getText().toString())) {
                    //Checks to ensure start date is before the end date
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

                    switchScreen(SwitchScreen.getActivityClass(activityCameFrom), SwitchScreen.COURSE_ID_KEY, String.valueOf(courseId));
                } else {

                    assessment.updateInputFields(assessmentName, assessmentInfo, assessmentType, startDate, endDate);

                    try {
                        repository.update(assessment);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    switchScreen(SwitchScreen.getActivityClass(activityCameFrom), SwitchScreen.ASSESSMENT_ID_KEY, String.valueOf(assessmentId));
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

    void switchScreen(Class className, String idKey, String idValue) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, className);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(idKey, idValue);
        //Need to always start the activity that you're going to
        startActivity(intent);
    }

    ArrayAdapter<String> createAssessmentTypeListAdapter() {
        ArrayList<String> statusOptionsList = new ArrayList<>();

        //Spinner stores the items in the position that they were added with the same index as the list
        statusOptionsList.add("Performance");
        statusOptionsList.add("Objective");

        return new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusOptionsList);
    }

    int getSpinnerAssessmentTypePosition(String status) {
        /*
            Note: Due to the current design for this, this method will need to be updated if
            the order of the items for the assessment type spinner is changed; or more are added.
         */
        switch (status) {
            case "Performance":
                return 0;
            case "Objective":
                return 1;
        }
        return -1;
    }

    void setScreenInfo() {
        if (addOrUpdate.equals(SwitchScreen.ADD_ASSESSMENT_VALUE)) {
            return;
        }

        assessmentName.setText(assessment.getTitle());
        assessmentInfo.setText(assessment.getInformation());
        assessmentType.setSelection(getSpinnerAssessmentTypePosition(assessment.getType()));
        startDate.setText(assessment.getStartDate());
        endDate.setText(assessment.getEndDate());
    }
}