package com.muhammadchambersc196.controller.create;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.muhammadchambersc196.R;
import com.muhammadchambersc196.database.Repository;
import com.muhammadchambersc196.entities.CourseInstructor;
import com.muhammadchambersc196.helper.CourseInstructorHelper;
import com.muhammadchambersc196.helper.InputValidation;
import com.muhammadchambersc196.helper.SwitchScreen;

import java.util.ArrayList;

public class CreateOrUpdateInstructorActivity extends AppCompatActivity {
    Repository repository;
    EditText name;
    EditText email;
    EditText phoneNumber;
    Button saveBtn;
    Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_update_instructor);
        repository = new Repository(getApplication());

        //Retrieves the intent that was passed to this activity/screen
        Intent intent = getIntent();
        //Retrieves the data value/string name that was passed to this intent
        String activityCameFrom = intent.getStringExtra(SwitchScreen.CAME_FROM_KEY);
        String addOrUpdate = intent.getStringExtra(SwitchScreen.ADD_OR_UPDATE_SCREEN_KEY);

        //Sets the action bar title of the screen to say "Add" or "Update" based on if it's supposed to be for adding or updating
        setTitle(addOrUpdate);

        name = findViewById(R.id.create_ci_name);
        email = findViewById(R.id.create_ci_email);
        phoneNumber = findViewById(R.id.create_ci_phone_number);
        saveBtn = findViewById(R.id.create_ci_save_btn);
        cancelBtn = findViewById(R.id.create_ci_cancel_btn);

        try {
            setScreenInfo(addOrUpdate, intent);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InputValidation.isInputFieldEmpty(name) || InputValidation.isInputFieldEmpty(email) || InputValidation.isInputFieldEmpty(phoneNumber)) {
                    return;
                }

                if (addOrUpdate.equals(SwitchScreen.ADD_INSTRUCTOR_VALUE)) {
                    CourseInstructor addInstructor = new CourseInstructor(name.getText().toString(), phoneNumber.getText().toString(), email.getText().toString());

                    try {
                        //Doesn't allow instructor to be added if it already exists in the database
                        if (CourseInstructorHelper.doesCourseInstructorInDatabase(addInstructor, repository.getmAllCourseInstructors())) {
                            return;
                        }

                        //Adds instructor to the database
                        repository.insert(addInstructor);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    switchScreen(SwitchScreen.getActivityClass(activityCameFrom));
                } else {
                    //Section for updating instructor

                }
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

    void setScreenInfo(String addOrUpdate, Intent intent) throws InterruptedException {
        if (addOrUpdate.equals(SwitchScreen.ADD_TERM_VALUE)) {
            return;
        }

        CourseInstructor instructor = CourseInstructorHelper.retrieveCourseFromDatabaseByTermID((ArrayList<CourseInstructor>) repository.getmAllCourseInstructors(), Integer.valueOf(intent.getStringExtra(SwitchScreen.INSTRUCTOR_ID_KEY)));

        if (instructor == null) {
            return;
        }

        name.setText(instructor.getName());
        email.setText(instructor.getEmail());
        phoneNumber.setText(instructor.getPhoneNumber());
    }
}