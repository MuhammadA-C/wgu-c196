package com.muhammadchambersc196.controller.detailed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.muhammadchambersc196.R;
import com.muhammadchambersc196.controller.create.CreateTermActivity;
import com.muhammadchambersc196.controller.update.UpdateAssessmentActivity;
import com.muhammadchambersc196.helper.SwitchScreen;

public class DetailedAssessmentActivity extends AppCompatActivity {
    Button editBtn;
    TextView assessmentName;
    TextView assessmentType;
    TextView startDate;
    TextView endDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_assessment);

        editBtn = findViewById(R.id.detailed_assessment_edit_btn);
        assessmentName = findViewById(R.id.detailed_assessment_name);
        assessmentType = findViewById(R.id.detailed_assessment_type);
        startDate = findViewById(R.id.detailed_assessment_start_date);
        endDate = findViewById(R.id.detailed_assessment_end_date);

        /*
            Need to set the text views
         */
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNewScreen(UpdateAssessmentActivity.class, SwitchScreen.CAME_FROM, SwitchScreen.DETAILED_ASSESSMENT_ACTIVITY);
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