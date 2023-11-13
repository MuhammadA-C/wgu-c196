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

        //Gets references to the activities input fields
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
                /*
                    NOTE: NEED TO PASS IN THE COURSE ID, TERM ID, AND WHETHER THE SCREEN WAS ADD OR UPDATE WHEN SAVING
                 */

                //Checks to see if the input fields are empty
                if (InputValidation.isInputFieldEmpty(name) || InputValidation.isInputFieldEmpty(email) ||
                        InputValidation.isInputFieldEmpty(phoneNumber)) {
                    return;
                }

                if (addOrUpdate.equals(SwitchScreen.ADD_INSTRUCTOR_VALUE)) {

                    /*
                        SECTION BELOW: Is for adding creating a new Course Instructor Object
                     */

                    CourseInstructor addInstructor = new CourseInstructor(name.getText().toString(),
                            phoneNumber.getText().toString(), email.getText().toString());

                    try {
                        //Doesn't allow instructor to be added if it already exists in the database
                        if (InstructorHelper.doesCourseInstructorExistInDatabase(addInstructor, repository.getmAllCourseInstructors())) {
                            return;
                        }

                        //Adds instructor to the database
                        repository.insert(addInstructor);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    if (activityCameFrom.equals(SwitchScreen.CREATE_OR_UPDATE_COURSE_ACTIVITY)) {
                        switchScreen(SwitchScreen.getActivityClass(activityCameFrom), SwitchScreen.CAME_FROM_KEY,
                                SwitchScreen.CREATE_OR_UPDATE_INSTRUCTOR_ACTIVITY, SwitchScreen.CAME_FROM_KEY2,
                                intent.getStringExtra(SwitchScreen.CAME_FROM_KEY2), SwitchScreen.TERM_ID_KEY,
                                intent.getStringExtra(SwitchScreen.TERM_ID_KEY));
                    } else {
                        switchScreen(SwitchScreen.getActivityClass(activityCameFrom));
                    }

                } else {

                    /*
                        SECTION BELOW: Is for updating a Course Instructor object
                     */

                    CourseInstructor updateInstructor;

                    try {
                        updateInstructor = InstructorHelper.retrieveCourseFromDatabaseByInstructorID((ArrayList<CourseInstructor>) repository.getmAllCourseInstructors(),
                                Integer.valueOf(intent.getStringExtra(SwitchScreen.INSTRUCTOR_ID_KEY)));

                        if (updateInstructor == null) {
                            return;
                        }

                        //Updates the values for the course instructor object
                        updateInstructor.setName(name.getText().toString());
                        updateInstructor.setEmail(email.getText().toString());
                        updateInstructor.setPhoneNumber(phoneNumber.getText().toString());

                        //Doesn't allow course instructor to be added if it already exists in the database
                        if (InstructorHelper.doesCourseInstructorExistInDatabase(updateInstructor,
                                repository.getmAllCourseInstructors())) {
                            return;
                        }

                        //Saves the updated course instructor values in the database
                        repository.update(updateInstructor);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    //Need to pass in the course instructor id because it is used for the detailed course instructor screen to display the objects info to the screen
                    switchScreen(SwitchScreen.getActivityClass(activityCameFrom), SwitchScreen.INSTRUCTOR_ID_KEY,
                            intent.getStringExtra(SwitchScreen.INSTRUCTOR_ID_KEY));
                }
            }
        });


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                    Need to check to see if this screen was opened by the detailed course instructor screen
                    because then we will need to pass in the course instructor id.

                    Note: The course instructor id is used by the detailed course instructor screen to
                    display the objects information.
                 */
                if (activityCameFrom.equals(SwitchScreen.DETAILED_INSTRUCTOR_ACTIVITY)) {
                    switchScreen(SwitchScreen.getActivityClass(activityCameFrom), SwitchScreen.INSTRUCTOR_ID_KEY,
                            intent.getStringExtra(SwitchScreen.INSTRUCTOR_ID_KEY));
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
                      String cameFromValue2, String idKey, String idValue) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, className);

        //Specifies the data to pass to the new activity/screen
        intent.putExtra(cameFromKey1, cameFromValue1);
        intent.putExtra(cameFromKey2, cameFromValue2);
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

    void setScreenInfo(String addOrUpdate, Intent intent) throws InterruptedException {
        if (addOrUpdate.equals(SwitchScreen.ADD_INSTRUCTOR_VALUE)) {
            return;
        }

        CourseInstructor instructor = InstructorHelper.retrieveCourseFromDatabaseByInstructorID((ArrayList<CourseInstructor>) repository.getmAllCourseInstructors(),
                Integer.valueOf(intent.getStringExtra(SwitchScreen.INSTRUCTOR_ID_KEY)));

        if (instructor == null) {
            return;
        }

        name.setText(instructor.getName());
        email.setText(instructor.getEmail());
        phoneNumber.setText(instructor.getPhoneNumber());
    }
}