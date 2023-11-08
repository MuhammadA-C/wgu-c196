package com.muhammadchambersc196.controller.create;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.muhammadchambersc196.R;
import com.muhammadchambersc196.entities.Term;
import com.muhammadchambersc196.helper.DateValidation;
import com.muhammadchambersc196.helper.InputValidation;
import com.muhammadchambersc196.helper.SwitchScreen;

public class CreateTermActivity extends AppCompatActivity {
    Button createTermBtn;
    EditText createTermName;
    EditText createTermStartDate;
    EditText createTermEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_term);

        createTermBtn = findViewById(R.id.create_term_btn);
        createTermName = findViewById(R.id.create_term_name);
        createTermStartDate = findViewById(R.id.create_term_start_date);
        createTermEndDate = findViewById(R.id.create_term_end_date);

        //Retrieves the intent that was passed to this activity/screen
        Intent intent = getIntent();
        //Retrieves the data value/string name that was passed to this intent
        String activityCameFrom = intent.getStringExtra(SwitchScreen.CAME_FROM);


        createTermBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Checks to ensure that the input fields are NOT empty
                if(InputValidation.isInputFieldEmpty(createTermName) || InputValidation.isInputFieldEmpty(createTermStartDate) || InputValidation.isInputFieldEmpty(createTermEndDate)) {
                    return;
                }

                //Checks to ensure that the start and end dates are formatted correctly
                if(!DateValidation.isDateFormattedCorrect(createTermStartDate.getText().toString()) || !DateValidation.isDateFormattedCorrect(createTermEndDate.getText().toString())) {
                    return;
                }

                //Checks to ensure start date is the same or before the end date
                if(!DateValidation.isStartDateTheSameOrBeforeEndDate(createTermStartDate.getText().toString(), createTermEndDate.getText().toString())) {
                    return;
                }

                Term term = new Term(createTermName.getText().toString(), createTermStartDate.getText().toString(), createTermEndDate.getText().toString());

                /*
                    Note: Need to add term to the database prior to switching screens
                 */

                goToNewScreen(SwitchScreen.getActivityClass(activityCameFrom), SwitchScreen.CAME_FROM, SwitchScreen.CREATE_TERM_ACTIVITY);
            }
        });
    }

    void goToNewScreen(Class className, String keyName, String value) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, className);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(keyName, value);
        //Note: Need to always start the activity that you're going to
        startActivity(intent);
    }
}