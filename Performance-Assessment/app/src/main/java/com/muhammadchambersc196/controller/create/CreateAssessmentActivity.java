package com.muhammadchambersc196.controller.create;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.muhammadchambersc196.R;
import com.muhammadchambersc196.entities.Assessment;
import com.muhammadchambersc196.helper.DateValidation;
import com.muhammadchambersc196.helper.InputValidation;
import com.muhammadchambersc196.helper.SwitchScreen;

public class CreateAssessmentActivity extends AppCompatActivity {
    //Note: Need to correctly set the course id by taking the value passed from the course page
    int courseId = 1;
    EditText assessmentName;
    EditText assessmentInfo;
    EditText startDate;
    EditText endDate;
    Spinner assessmentType;
    Button saveBtn;
    Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_assessment);

        assessmentName = findViewById(R.id.create_assessment_name);
        assessmentInfo = findViewById(R.id.create_assessment_info);
        startDate = findViewById(R.id.create_assessment_start_date);
        endDate = findViewById(R.id.create_assessment_end_date);
        assessmentType = findViewById(R.id.create_assessment_type);
        saveBtn = findViewById(R.id.create_assessment_save_btn);
        cancelBtn = findViewById(R.id.create_assessment_cancel_btn);

        //Retrieves the intent that was passed to this activity/screen
        Intent intent = getIntent();
        //Retrieves the data value/string name that was passed to this intent
        String activityCameFrom = intent.getStringExtra(SwitchScreen.CAME_FROM);

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
                if (!DateValidation.isDateFormattedCorrect(startDate.getText().toString()) || !DateValidation.isDateFormattedCorrect(endDate.getText().toString())) {
                    return;
                }

                //Checks to ensure start date is the same or before the end date
                if (!DateValidation.isStartDateTheSameOrBeforeEndDate(startDate.getText().toString(), endDate.getText().toString())) {
                    return;
                }

                /*
                    NOTE: Need to add a check to ensure assessment start date is the same or after course start date, but before course end date.
                    And assessment end date the same or before course end date.
                 */

                //Note: Need to correctly set the course id by taking the value passed from the course page
                Assessment assessment = new Assessment(assessmentName.getText().toString(), assessmentType.getSelectedItem().toString(), assessmentInfo.getText().toString(),
                        startDate.getText().toString(), endDate.getText().toString(), courseId);

                //Need to add assessment to the database

                goToNewScreen(SwitchScreen.getActivityClass(activityCameFrom), SwitchScreen.CAME_FROM, SwitchScreen.CREATE_ASSESSMENT_ACTIVITY);
            }
        });
    }

    void goToNewScreen(Class className, String keyName, String value) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, className);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(keyName, value);
        //Note: Need to always start the activity that you're going to
        startActivity(intent);
    }
}