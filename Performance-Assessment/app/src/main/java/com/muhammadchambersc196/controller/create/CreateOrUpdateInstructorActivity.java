package com.muhammadchambersc196.controller.create;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.muhammadchambersc196.R;
import com.muhammadchambersc196.database.Repository;
import com.muhammadchambersc196.entities.CourseInstructor;
import com.muhammadchambersc196.helper.DialogMessages;
import com.muhammadchambersc196.helper.InstructorHelper;
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
    ArrayList<CourseInstructor> dbInstructorList;
    String addOrUpdate;
    int instructorId;
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_update_instructor);

        repository = new Repository(getApplication());
        builder = new AlertDialog.Builder(this);

        try {
            dbInstructorList = (ArrayList<CourseInstructor>) repository.getmAllCourseInstructors();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //Retrieves the intent that was passed to this activity/screen
        Intent intent = getIntent();
        //Retrieves the data value/string name that was passed to this intent
        String activityCameFrom = intent.getStringExtra(SwitchScreen.CAME_FROM_KEY);
        addOrUpdate = intent.getStringExtra(SwitchScreen.ADD_OR_UPDATE_SCREEN_KEY);

        setInstructorId(intent);

        //Sets the action bar title of the screen to say "Add" or "Update" based on if it's supposed to be for adding or updating
        setTitle(addOrUpdate);

        //Gets references to the activities input fields
        name = findViewById(R.id.create_ci_name);
        email = findViewById(R.id.create_ci_email);
        phoneNumber = findViewById(R.id.create_ci_phone_number);
        saveBtn = findViewById(R.id.create_ci_save_btn);
        cancelBtn = findViewById(R.id.create_ci_cancel_btn);

        setScreenInfo(addOrUpdate, intent);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Checks to see if the input fields are empty
                if (InputValidation.isInputFieldEmpty(name) || InputValidation.isInputFieldEmpty(email) ||
                        InputValidation.isInputFieldEmpty(phoneNumber)) {
                    Toast.makeText(CreateOrUpdateInstructorActivity.this, DialogMessages.EMPTY_INPUT_FIELDS, Toast.LENGTH_SHORT).show();
                    return;
                }

                CourseInstructor saveInstructor = getInstructorForAddOrUpdate();

                if (InstructorHelper.doesCourseInstructorExistInDatabase(saveInstructor, dbInstructorList)) {
                    //Doesn't allow instructor to be added if it already exists in the database
                    Toast.makeText(CreateOrUpdateInstructorActivity.this, DialogMessages.INSTRUCTOR_ALREADY_EXISTS, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (addOrUpdate.equals(SwitchScreen.ADD_INSTRUCTOR_VALUE)) {
                    try {
                        //Adds new instructor to the database
                        repository.insert(saveInstructor);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    if (activityCameFrom.equals(SwitchScreen.CREATE_OR_UPDATE_COURSE_ACTIVITY)) {
                        Toast.makeText(CreateOrUpdateInstructorActivity.this, "Created " + saveInstructor.getName(), Toast.LENGTH_SHORT).show();
                        switchScreen(SwitchScreen.getActivityClass(activityCameFrom), SwitchScreen.CAME_FROM_KEY,
                                SwitchScreen.CREATE_OR_UPDATE_INSTRUCTOR_ACTIVITY, SwitchScreen.CAME_FROM_KEY2,
                                intent.getStringExtra(SwitchScreen.CAME_FROM_KEY2), SwitchScreen.CAME_FROM_ADD_OR_UPDATE_SCREEN_KEY,
                                intent.getStringExtra(SwitchScreen.CAME_FROM_ADD_OR_UPDATE_SCREEN_KEY), SwitchScreen.TERM_ID_KEY,
                                intent.getStringExtra(SwitchScreen.TERM_ID_KEY), SwitchScreen.COURSE_ID_KEY,
                                intent.getStringExtra(SwitchScreen.COURSE_ID_KEY));
                    } else {
                        Toast.makeText(CreateOrUpdateInstructorActivity.this, "Created " + saveInstructor.getName(), Toast.LENGTH_SHORT).show();
                        switchScreen(SwitchScreen.getActivityClass(activityCameFrom));
                    }

                } else {
                    try {
                        //Saves the updated course instructor values in the database
                        repository.update(saveInstructor);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    Toast.makeText(CreateOrUpdateInstructorActivity.this, "Updated " + saveInstructor.getName(), Toast.LENGTH_SHORT).show();
                    switchScreen(SwitchScreen.getActivityClass(activityCameFrom), SwitchScreen.INSTRUCTOR_ID_KEY, String.valueOf(instructorId));
                }
            }
        });


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Displays a confirmation box for the user to confirm if they want to cancel
                builder.setTitle(DialogMessages.CONFIRMATION)
                        .setMessage(DialogMessages.CANCEL_CONFORMATION_MESSAGE)
                        .setCancelable(true)
                        .setPositiveButton(DialogMessages.YES, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                /*
                                    Need to check to see if this screen was opened by the detailed course instructor screen
                                    because then we will need to pass in the course instructor id.

                                    Note: The course instructor id is used by the detailed course instructor screen to
                                    display the objects information.
                                 */
                                if (activityCameFrom.equals(SwitchScreen.DETAILED_INSTRUCTOR_ACTIVITY)) {
                                    switchScreen(SwitchScreen.getActivityClass(activityCameFrom), SwitchScreen.INSTRUCTOR_ID_KEY, String.valueOf(instructorId));
                                } else if (activityCameFrom.equals(SwitchScreen.CREATE_OR_UPDATE_COURSE_ACTIVITY)) {
                                    switchScreen(SwitchScreen.getActivityClass(activityCameFrom), SwitchScreen.CAME_FROM_KEY,
                                            SwitchScreen.CREATE_OR_UPDATE_INSTRUCTOR_ACTIVITY, SwitchScreen.CAME_FROM_KEY2,
                                            intent.getStringExtra(SwitchScreen.CAME_FROM_KEY2), SwitchScreen.CAME_FROM_ADD_OR_UPDATE_SCREEN_KEY,
                                            intent.getStringExtra(SwitchScreen.CAME_FROM_ADD_OR_UPDATE_SCREEN_KEY), SwitchScreen.TERM_ID_KEY,
                                            intent.getStringExtra(SwitchScreen.TERM_ID_KEY), SwitchScreen.COURSE_ID_KEY,
                                            intent.getStringExtra(SwitchScreen.COURSE_ID_KEY));
                                } else {
                                    switchScreen(SwitchScreen.getActivityClass(activityCameFrom));
                                }
                            }
                        })
                        .setNegativeButton(DialogMessages.NO, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
            }
        });

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

    void switchScreen(Class className, String cameFromKey1, String cameFromValue1, String cameFromKey2,
                      String cameFromValue2, String cameFromAddOrUpdateKey, String cameFromAddOrUpdateValue,
                      String idKey1, String idValue1, String idKey2, String idValue2) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, className);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(cameFromKey1, cameFromValue1);
        intent.putExtra(cameFromKey2, cameFromValue2);
        intent.putExtra(cameFromAddOrUpdateKey, cameFromAddOrUpdateValue);
        intent.putExtra(idKey1, idValue1);
        intent.putExtra(idKey2, idValue2);
        //Need to always start the activity that you're going to
        startActivity(intent);
    }

    void setScreenInfo(String addOrUpdate, Intent intent) {
        if (addOrUpdate.equals(SwitchScreen.ADD_INSTRUCTOR_VALUE)) {
            return;
        }

        CourseInstructor instructor = InstructorHelper.retrieveCourseFromDatabaseByInstructorID(dbInstructorList, instructorId);

        name.setText(instructor.getName());
        email.setText(instructor.getEmail());
        phoneNumber.setText(instructor.getPhoneNumber());
    }

    void setInstructorId(Intent intent) {
        if (addOrUpdate.equals(SwitchScreen.ADD_INSTRUCTOR_VALUE)) {
            return;
        }
        instructorId = Integer.valueOf(intent.getStringExtra(SwitchScreen.INSTRUCTOR_ID_KEY));
    }

    CourseInstructor getInstructorForAddOrUpdate() {
        if (addOrUpdate.equals(SwitchScreen.ADD_INSTRUCTOR_VALUE)) {
            return new CourseInstructor(name.getText().toString().trim(),
                    phoneNumber.getText().toString(), email.getText().toString());
        }
        CourseInstructor instructor = InstructorHelper.retrieveCourseFromDatabaseByInstructorID(dbInstructorList, Integer.valueOf(instructorId));

        //Updates the values for the course instructor object
        instructor.setName(name.getText().toString().trim());
        instructor.setEmail(email.getText().toString());
        instructor.setPhoneNumber(phoneNumber.getText().toString());

        return instructor;
    }
}