package com.muhammadchambersc196.controller.create;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.muhammadchambersc196.R;
import com.muhammadchambersc196.database.Repository;
import com.muhammadchambersc196.entities.Course;
import com.muhammadchambersc196.entities.Term;
import com.muhammadchambersc196.helper.CourseHelper;
import com.muhammadchambersc196.helper.DateValidation;
import com.muhammadchambersc196.helper.InputValidation;
import com.muhammadchambersc196.helper.SwitchScreen;

import java.util.ArrayList;

public class CreateOrUpdateCourseActivity extends AppCompatActivity {
    Repository repository;
    EditText className;
    EditText classInfo;
    Spinner classStatus;
    EditText startDate;
    EditText endDate;
    EditText instructorName;
    EditText instructorEmail;
    EditText instructorPhoneNumber;
    Button saveBtn;
    Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_update_course);
        repository = new Repository(getApplication());

        //Retrieves the intent that was passed to this activity/screen
        Intent intent = getIntent();
        //Retrieves the data value/string name that was passed to this intent
        String activityCameFrom = intent.getStringExtra(SwitchScreen.CAME_FROM_KEY);
        String addOrUpdate = intent.getStringExtra(SwitchScreen.ADD_OR_UPDATE_SCREEN_KEY);
        int termId = Integer.valueOf(intent.getStringExtra(SwitchScreen.TERM_ID_KEY));

        //Sets the action bar title of the screen to say "Add" or "Update" based on if it's supposed to be for adding or updating
        setTitle(addOrUpdate);

        //Gets references to the activities input fields
        classStatus = findViewById(R.id.create_class_status);
        className = findViewById(R.id.create_class_name);
        classInfo = findViewById(R.id.create_class_info);
        startDate = findViewById(R.id.create_class_start_date);
        endDate = findViewById(R.id.create_class_end_date);
        instructorName = findViewById(R.id.create_class_ci_name);
        instructorEmail = findViewById(R.id.create_class_ci_email);
        instructorPhoneNumber = findViewById(R.id.create_class_ci_phone_number);
        saveBtn = findViewById(R.id.create_class_save_btn);
        cancelBtn = findViewById(R.id.create_class_cancel_btn);

        //Sets the class status spinner
        classStatus.setAdapter(createStatusListAdapter());

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addOrUpdate.equals(SwitchScreen.ADD_COURSE_VALUE)) {
                    if (InputValidation.isInputFieldEmpty(className) ||InputValidation.isInputFieldEmpty(classInfo) || InputValidation.isInputFieldEmpty(classStatus) ||
                            InputValidation.isInputFieldEmpty(startDate) || InputValidation.isInputFieldEmpty(endDate) || InputValidation.isInputFieldEmpty(instructorName) ||
                            InputValidation.isInputFieldEmpty(instructorEmail) || InputValidation.isInputFieldEmpty(instructorPhoneNumber))  {
                        return;
                    }

                    if (!DateValidation.isDateFormattedCorrect(startDate.getText().toString()) || !DateValidation.isDateFormattedCorrect(endDate.getText().toString())) {
                        return;
                    }

                    if (!DateValidation.isStartDateTheSameOrBeforeEndDate(startDate.getText().toString(), endDate.getText().toString())) {
                        return;
                    }

                    Course addCourse = new Course(className.getText().toString(), classStatus.getSelectedItem().toString(), classInfo.getText().toString(), startDate.getText().toString(), endDate.getText().toString(), termId);

                    try {
                        //Course start and end dates must be within range of the terms start and end dates
                        if (!CourseHelper.areCourseDatesWithinRangeOfTermDates(addCourse, termId, (ArrayList<Term>) repository.getmAllTerms())) {
                            return;
                        }

                        /*
                            Checks to see if the course already exists for the term by comparing the course names.
                            The assumption here is that there shouldn't be duplicate courses for the same term.
                         */
                        if (CourseHelper.doesCourseExistForTerm(CourseHelper.getAllCoursesForTerm ((ArrayList<Course>) repository.getmAllCourses(), termId), termId, addCourse)) {
                            return;
                        }

                        repository.insert(addCourse);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                } else {

                    //This part will be for updating a course

                }

                switchScreen(SwitchScreen.getActivityClass(activityCameFrom), SwitchScreen.TERM_ID_KEY, String.valueOf(termId));
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchScreen(SwitchScreen.getActivityClass(activityCameFrom), SwitchScreen.TERM_ID_KEY, String.valueOf(termId));
            }
        });
    }

    ArrayAdapter<String> createStatusListAdapter() {
        ArrayList<String> statusOptionsList = new ArrayList<>();

        statusOptionsList.add("Not Started");
        statusOptionsList.add("In-Progress");
        statusOptionsList.add("Completed");
        statusOptionsList.add("Failed");

        return new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusOptionsList);
    }

    void switchScreen(Class className, String termIdKey, String termIdValue) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, className);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(termIdKey, termIdValue);
        //Need to always start the activity that you're going to
        startActivity(intent);
    }
}