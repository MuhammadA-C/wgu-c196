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
import com.muhammadchambersc196.entities.Course;
import com.muhammadchambersc196.helper.DateValidation;
import com.muhammadchambersc196.helper.InputValidation;
import com.muhammadchambersc196.helper.SwitchScreen;

public class CreateAssessmentActivity extends AppCompatActivity {
    //Note: Need to correctly set the course id by taking the value passed from the course page
    int courseId = 1;
    EditText createAssessmentName;
    EditText createAssessmentInfo;
    EditText createAssessmentStartDate;
    EditText createAssessmentEndDate;
    Spinner createAssessmentType;
    Button createAssessmentSaveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_assessment);

        createAssessmentName = findViewById(R.id.create_assessment_name);
        createAssessmentInfo = findViewById(R.id.create_assessment_info);
        createAssessmentStartDate = findViewById(R.id.create_assessment_start_date);
        createAssessmentEndDate = findViewById(R.id.create_assessment_end_date);
        createAssessmentType = findViewById(R.id.create_assessment_type);
        createAssessmentSaveBtn = findViewById(R.id.create_assessment_save_btn);


        createAssessmentSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Checks to ensure that the input fields are NOT empty
                if (InputValidation.isInputFieldEmpty(createAssessmentName) || InputValidation.isInputFieldEmpty(createAssessmentInfo) ||
                InputValidation.isInputFieldEmpty(createAssessmentStartDate) || InputValidation.isInputFieldEmpty(createAssessmentEndDate) ||
                InputValidation.isInputFieldEmpty(createAssessmentType)) {
                    return;
                    //Need to add a check for the spinner/class status input field
                }

                //Checks to ensure that the start and end dates are formatted correctly
                if (!DateValidation.isDateFormattedCorrect(createAssessmentStartDate.getText().toString()) || !DateValidation.isDateFormattedCorrect(createAssessmentEndDate.getText().toString())) {
                    return;
                }

                //Checks to ensure start date is the same or before the end date
                if (!DateValidation.isStartDateTheSameOrBeforeEndDate(createAssessmentStartDate.getText().toString(), createAssessmentEndDate.getText().toString())) {
                    return;
                }

                //Note: Need to correctly set the course id by taking the value passed from the course page
                Assessment assessment = new Assessment(createAssessmentName.getText().toString(), createAssessmentType.getSelectedItem().toString(), createAssessmentInfo.getText().toString(),
                        createAssessmentStartDate.getText().toString(), createAssessmentEndDate.getText().toString(), courseId);

                //Need to add assessment to the database

                goToNewScreen(SwitchScreen.getActivityClass(SwitchScreen.DETAILED_COURSE_ACTIVITY), SwitchScreen.CAME_FROM, SwitchScreen.CREATE_ASSESSMENT_ACTIVITY);
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