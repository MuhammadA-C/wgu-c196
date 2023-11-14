package com.muhammadchambersc196.controller.create;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.muhammadchambersc196.R;
import com.muhammadchambersc196.helper.InputValidation;
import com.muhammadchambersc196.helper.SwitchScreen;

public class CreateOrUpdateNoteActivity extends AppCompatActivity {
    //Note: Need to correctly set the course id by taking the value passed from the course page
    int courseId;
    EditText createNoteDetails;
    Button saveBtn;
    Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_update_note);

        //Retrieves the intent that was passed to this activity/screen
        Intent intent = getIntent();
        //Retrieves the data value/string name that was passed to this intent
        String activityCameFrom = intent.getStringExtra(SwitchScreen.CAME_FROM_KEY);

        //Gets references to the activities input fields
        createNoteDetails = findViewById(R.id.create_note_details);
        saveBtn = findViewById(R.id.create_note_btn);
        cancelBtn = findViewById(R.id.create_cancel_btn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(InputValidation.isInputFieldEmpty(createNoteDetails)) {
                    return;
                }

                /*
                    1. Need to create course note object
                    2. Need to add the course note to the database
                 */
                goToNewScreen(SwitchScreen.getActivityClass(activityCameFrom));
            }
        });


    }

    void goToNewScreen(Class className) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, className);
        //Need to always start the activity that you're going to
        startActivity(intent);
    }
}