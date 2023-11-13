package com.muhammadchambersc196.controller.create;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.muhammadchambersc196.R;
import com.muhammadchambersc196.helper.SwitchScreen;

public class CreateOrUpdateInstructorActivity extends AppCompatActivity {
    EditText instructorName;
    EditText instructorEmail;
    EditText instructorPhoneNumber;
    Button saveBtn;
    Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_update_instructor);

        instructorName = findViewById(R.id.create_ci_name);
        instructorEmail = findViewById(R.id.create_ci_email);
        instructorPhoneNumber = findViewById(R.id.create_ci_phone_number);
        saveBtn = findViewById(R.id.create_ci_save_btn);
        cancelBtn = findViewById(R.id.create_ci_cancel_btn);

        //Retrieves the intent that was passed to this activity/screen
        Intent intent = getIntent();
        //Retrieves the data value/string name that was passed to this intent
        String activityCameFrom = intent.getStringExtra(SwitchScreen.CAME_FROM_KEY);
        String addOrUpdate = intent.getStringExtra(SwitchScreen.ADD_OR_UPDATE_SCREEN_KEY);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addOrUpdate.equals(SwitchScreen.ADD_INSTRUCTOR_VALUE)) {
                    switchScreen(SwitchScreen.getActivityClass(activityCameFrom));
                } else {

                }
            }
        });

    }

    void switchScreen(Class className) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, className);
        //Need to always start the activity that you're going to
        startActivity(intent);
    }
}