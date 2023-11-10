package com.muhammadchambersc196.controller.update;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.muhammadchambersc196.R;

public class UpdateAssessmentActivity extends AppCompatActivity {
    EditText assessmentName;
    Spinner assessmentType;
    EditText assessmentInfo;
    EditText startDate;
    EditText endDate;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_assessment);

        assessmentName = findViewById(R.id.update_assessment_name);
        assessmentType = findViewById(R.id.update_assessment_type);
        assessmentInfo = findViewById(R.id.update_assessment_info);
        startDate = findViewById(R.id.update_assessment_start_date);
        endDate = findViewById(R.id.update_assessment_end_date);
        saveBtn = findViewById(R.id.update_assessment_save_btn);
    }
}