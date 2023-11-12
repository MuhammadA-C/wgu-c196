package com.muhammadchambersc196.controller.create;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.muhammadchambersc196.R;
import com.muhammadchambersc196.database.Repository;
import com.muhammadchambersc196.entities.Term;
import com.muhammadchambersc196.helper.DateValidation;
import com.muhammadchambersc196.helper.Helper;
import com.muhammadchambersc196.helper.InputValidation;
import com.muhammadchambersc196.helper.SwitchScreen;

import java.util.ArrayList;

public class CreateTermActivity extends AppCompatActivity {
    Repository repository;
    Button saveBtn;
    Button cancelBtn;
    EditText termName;
    EditText startDate;
    EditText endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_term);

        saveBtn = findViewById(R.id.create_term_btn);
        termName = findViewById(R.id.create_term_name);
        startDate = findViewById(R.id.create_term_start_date);
        endDate = findViewById(R.id.create_term_end_date);
        cancelBtn = findViewById(R.id.create_term_cancel_btn);

        //Retrieves the intent that was passed to this activity/screen
        Intent intent = getIntent();
        //Retrieves the data value/string name that was passed to this intent
        String activityCameFrom = intent.getStringExtra(SwitchScreen.CAME_FROM);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Checks to ensure that the input fields are NOT empty
                if(InputValidation.isInputFieldEmpty(termName) || InputValidation.isInputFieldEmpty(startDate) || InputValidation.isInputFieldEmpty(endDate)) {
                    return;
                }

                //Checks to ensure that the start and end dates are formatted correctly
                if(!DateValidation.isDateFormattedCorrect(startDate.getText().toString()) || !DateValidation.isDateFormattedCorrect(endDate.getText().toString())) {
                    return;
                }

                //Checks to ensure start date is the same or before the end date
                if(!DateValidation.isStartDateTheSameOrBeforeEndDate(startDate.getText().toString(), endDate.getText().toString())) {
                    return;
                }

                repository = new Repository(getApplication());
                Term term = new Term(termName.getText().toString(), startDate.getText().toString(), endDate.getText().toString());

                try {
                    //Doesn't allow term to be added if the term name already exists in the database
                    if (Helper.doesTermExistInDatabase((ArrayList<Term>) repository.getmAllTerms(), term)) {
                        return;
                    }

                    //Doesn't allow the term to be added if the start and end dates overlaps with a term in the database
                    if (Helper.doesTermDateOverlapWithTermInDatabase((ArrayList<Term>) repository.getmAllTerms(), term)) {
                        return;
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                //Adds term to the database
                try {
                    repository.insert(term);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                goToNewScreen(SwitchScreen.getActivityClass(activityCameFrom));
            }
        });
        
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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