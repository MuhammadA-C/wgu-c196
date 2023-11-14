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
import com.muhammadchambersc196.helper.InputValidation;
import com.muhammadchambersc196.helper.SwitchScreen;
import com.muhammadchambersc196.helper.TermHelper;

import java.util.ArrayList;

public class CreateOrUpdateTermActivity extends AppCompatActivity {
    Repository repository;
    Button saveBtn;
    Button cancelBtn;
    EditText termName;
    EditText startDate;
    EditText endDate;
    ArrayList<Term> dbTermList;

    /*
        NOTE: Only need to add error messages for this class
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_update_term);
        repository = new Repository(getApplication());

        try {
            dbTermList = (ArrayList<Term>) repository.getmAllTerms();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //Retrieves the intent that was passed to this activity/screen
        Intent intent = getIntent();
        //Retrieves the data value/string name that was passed to this intent
        String activityCameFrom = intent.getStringExtra(SwitchScreen.CAME_FROM_KEY);
        String addOrUpdate = intent.getStringExtra(SwitchScreen.ADD_OR_UPDATE_SCREEN_KEY);

        //Sets the action bar title of the screen to say "Add" or "Update" based on if it's supposed to be for adding or updating
        setTitle(addOrUpdate);

        //Gets references to the activities input fields
        saveBtn = findViewById(R.id.create_term_btn);
        termName = findViewById(R.id.create_term_name);
        startDate = findViewById(R.id.create_term_start_date);
        endDate = findViewById(R.id.create_term_end_date);
        cancelBtn = findViewById(R.id.create_term_cancel_btn);

        try {
            setScreenInfo(addOrUpdate, intent);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Checks to ensure that the input fields are NOT empty
                if(InputValidation.isInputFieldEmpty(termName) || InputValidation.isInputFieldEmpty(startDate) ||
                        InputValidation.isInputFieldEmpty(endDate)) {
                    return;
                }

                //Checks to ensure that the start and end dates are formatted correctly
                if(!DateValidation.isDateFormattedCorrect(startDate.getText().toString()) ||
                        !DateValidation.isDateFormattedCorrect(endDate.getText().toString())) {
                    return;
                }

                //Checks to ensure start date is before the end date
                if(!DateValidation.isStartDateBeforeEndDate(startDate.getText().toString(), endDate.getText().toString())) {
                    return;
                }

                /*
                    Determines if a new term object needs to be created if the user is on the "Add Term"
                    screen, or if the current term object just needs to have its fields updated
                 */
                Term term = null;
                if (addOrUpdate.equals(SwitchScreen.ADD_TERM_VALUE)) {
                    term = new Term(termName.getText().toString(), startDate.getText().toString(), endDate.getText().toString());
                } else {
                    term = TermHelper.retrieveTermFromDatabaseByTermID(dbTermList, Integer.valueOf(intent.getStringExtra(SwitchScreen.TERM_ID_KEY)));
                    term.updateFields(termName, startDate, endDate);
                }

                //Doesn't allow term to be added if the term name already exists in the database
                if (TermHelper.doesTermNameExistInDatabase(dbTermList, term)) {
                    return;
                }

                //Doesn't allow the term to be added if the start and end dates overlaps with a term in the database
                if (TermHelper.doesTermDateOverlapWithTermInDatabase(dbTermList, term)) {
                    return;
                }

                if (addOrUpdate.equals(SwitchScreen.ADD_TERM_VALUE)) {
                    try {
                        //Adds new term to the database
                        repository.insert(term);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    switchScreen(SwitchScreen.getActivityClass(activityCameFrom));
                } else {

                    try {
                        //Saves the updated term values in the database
                        repository.update(term);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    switchScreen(SwitchScreen.getActivityClass(activityCameFrom), SwitchScreen.TERM_ID_KEY, String.valueOf(term.getTermID()));
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                    Need this check because if the screen is for updating the term then we need to pass
                    in the term id when switching back because the detailed term activity uses it
                 */
                if (addOrUpdate.equals(SwitchScreen.ADD_TERM_VALUE)) {
                    switchScreen(SwitchScreen.getActivityClass(activityCameFrom));
                } else {
                    switchScreen(SwitchScreen.getActivityClass(activityCameFrom), SwitchScreen.TERM_ID_KEY, intent.getStringExtra(SwitchScreen.TERM_ID_KEY));
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

    void switchScreen(Class className, String termIdKey, String termIdValue) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, className);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(termIdKey, termIdValue);
        //Need to always start the activity that you're going to
        startActivity(intent);
    }

    void setScreenInfo(String addOrUpdate, Intent intent) throws InterruptedException {
        if (addOrUpdate.equals(SwitchScreen.ADD_TERM_VALUE)) {
            return;
        }

        //Term database list
        Term term = TermHelper.retrieveTermFromDatabaseByTermID(dbTermList, Integer.valueOf(intent.getStringExtra(SwitchScreen.TERM_ID_KEY)));

        if (term == null) {
            return;
        }

        termName.setText(term.getTitle());
        startDate.setText(term.getStartDate());
        endDate.setText(term.getEndDate());
    }
}