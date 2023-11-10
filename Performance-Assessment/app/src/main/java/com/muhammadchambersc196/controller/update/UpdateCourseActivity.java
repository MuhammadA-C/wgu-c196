package com.muhammadchambersc196.controller.update;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.muhammadchambersc196.R;

public class UpdateCourseActivity extends AppCompatActivity {
    EditText classTitle;
    EditText classInfo;
    Spinner classStatus;
    EditText startDate;
    EditText endDate;
    EditText instructorName;
    EditText instructorEmail;
    EditText instructorPhoneNumber;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_course);

        classTitle = findViewById(R.id.update_class_name);
        classInfo = findViewById(R.id.update_class_info);
        classStatus = findViewById(R.id.update_class_status);
        startDate = findViewById(R.id.update_class_start_date);
        endDate = findViewById(R.id.update_class_end_date);
        instructorName = findViewById(R.id.update_class_ci_name);
        instructorEmail = findViewById(R.id.update_class_ci_email);
        instructorPhoneNumber = findViewById(R.id.update_class_ci_phone_number);
        saveBtn = findViewById(R.id.update_class_save_btn);






    }
}