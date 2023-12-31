package com.muhammadchambersc196.controller.detailed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.muhammadchambersc196.R;
import com.muhammadchambersc196.controller.create.CreateOrUpdateInstructorActivity;
import com.muhammadchambersc196.database.Repository;
import com.muhammadchambersc196.entities.CourseInstructor;
import com.muhammadchambersc196.helper.InstructorHelper;
import com.muhammadchambersc196.helper.SwitchScreen;

import java.util.ArrayList;

public class DetailedInstructorActivity extends AppCompatActivity {
    Repository repository;
    int instructorId;
    Button editBtn;
    TextView name;
    TextView email;
    TextView phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_instructor);

        repository = new Repository(getApplication());
        Intent intent = getIntent();
        instructorId = Integer.valueOf(intent.getStringExtra(SwitchScreen.INSTRUCTOR_ID_KEY));

        //Gets references to the activities input fields
        editBtn = findViewById(R.id.detailed_view_edit_btn);
        name = findViewById(R.id.detailed_view_ci_name);
        email = findViewById(R.id.detailed_view_ci_email);
        phoneNumber = findViewById(R.id.detailed_view_ci_number);

        setScreenInfo(instructorId);


        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchScreen(CreateOrUpdateInstructorActivity.class, SwitchScreen.CAME_FROM_KEY,
                        SwitchScreen.DETAILED_INSTRUCTOR_ACTIVITY, SwitchScreen.ADD_OR_UPDATE_SCREEN_KEY,
                        SwitchScreen.UPDATE_INSTRUCTOR_VALUE, SwitchScreen.INSTRUCTOR_ID_KEY, String.valueOf(instructorId));
            }
        });
    }

    void setScreenInfo(int instructorId) {
        CourseInstructor instructor;

        try {
            instructor = InstructorHelper.retrieveCourseFromDatabaseByInstructorID((ArrayList<CourseInstructor>) repository.getmAllCourseInstructors(), instructorId);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        name.setText(instructor.getName());
        email.setText(instructor.getEmail());
        phoneNumber.setText(instructor.getPhoneNumber());
    }

    void switchScreen(Class goToScreen, String cameFromScreenKey, String cameFromScreenValue, String addOrUpdateScreenKey,
                      String addOrUpdateScreenValue, String idKey, String idValue) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, goToScreen);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(cameFromScreenKey, cameFromScreenValue);
        intent.putExtra(addOrUpdateScreenKey, addOrUpdateScreenValue);
        intent.putExtra(idKey, idValue);
        //Need to always start the activity that you're going to
        startActivity(intent);
    }
}