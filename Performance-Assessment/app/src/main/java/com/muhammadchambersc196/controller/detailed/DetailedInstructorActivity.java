package com.muhammadchambersc196.controller.detailed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.muhammadchambersc196.R;
import com.muhammadchambersc196.database.Repository;
import com.muhammadchambersc196.entities.CourseInstructor;
import com.muhammadchambersc196.helper.CourseInstructorHelper;
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

        editBtn = findViewById(R.id.detailed_view_edit_btn);
        name = findViewById(R.id.detailed_view_ci_name);
        email = findViewById(R.id.detailed_view_ci_email);
        phoneNumber = findViewById(R.id.detailed_view_ci_number);

        setScreenInfo(instructorId);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
    }

    void setScreenInfo(int instructorId) {
        CourseInstructor instructor;

        try {
            instructor = CourseInstructorHelper.retrieveCourseFromDatabaseByTermID((ArrayList<CourseInstructor>) repository.getmAllCourseInstructors(), instructorId);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (instructor == null) {
            return;
        }

        name.setText(instructor.getName());
        email.setText(instructor.getEmail());
        phoneNumber.setText(instructor.getPhoneNumber());
    }

    void switchScreen(Class goToScreen, String cameFromScreenKey, String cameFromScreenValue, String addOrUpdateScreenKey, String addOrUpdateScreenValue, String idKey, String idValue) {
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