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
import com.muhammadchambersc196.entities.Term;
import com.muhammadchambersc196.helper.DateValidation;
import com.muhammadchambersc196.helper.DialogMessages;
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
    Term term;
    String addOrUpdate;
    int termId;
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_update_term);

        repository = new Repository(getApplication());
        builder = new AlertDialog.Builder(this);

        try {
            dbTermList = (ArrayList<Term>) repository.getmAllTerms();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //Retrieves the intent that was passed to this activity/screen
        Intent intent = getIntent();
        //Retrieves the data value/string name that was passed to this intent
        String activityCameFrom = intent.getStringExtra(SwitchScreen.CAME_FROM_KEY);
        addOrUpdate = intent.getStringExtra(SwitchScreen.ADD_OR_UPDATE_SCREEN_KEY);

        setTermId(intent);
        setTerm();
        //Sets the action bar title of the screen to say "Add" or "Update" based on if it's supposed to be for adding or updating
        setTitle(addOrUpdate);

        //Gets references to the activities input fields
        saveBtn = findViewById(R.id.create_term_btn);
        termName = findViewById(R.id.create_term_name);
        startDate = findViewById(R.id.create_term_start_date);
        endDate = findViewById(R.id.create_term_end_date);
        cancelBtn = findViewById(R.id.create_term_cancel_btn);

        setScreenInfo();


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InputValidation.isInputFieldEmpty(termName) || InputValidation.isInputFieldEmpty(startDate) ||
                        InputValidation.isInputFieldEmpty(endDate)) {
                    //Checks to ensure that the input fields are NOT empty
                    Toast.makeText(CreateOrUpdateTermActivity.this, DialogMessages.EMPTY_INPUT_FIELDS, Toast.LENGTH_SHORT).show();
                    return;
                } else if (!DateValidation.isDateANumber(startDate.getText().toString()) || !DateValidation.isDateANumber(endDate.getText().toString())) {
                    Toast.makeText(CreateOrUpdateTermActivity.this, DialogMessages.INVALID_INPUT_FOR_DATE, Toast.LENGTH_SHORT).show();
                    return;
                } else if (!DateValidation.isDateFormattedCorrect(startDate.getText().toString()) ||
                        !DateValidation.isDateFormattedCorrect(endDate.getText().toString())) {
                    //Checks to ensure that the start and end dates are formatted correctly
                    Toast.makeText(CreateOrUpdateTermActivity.this, DialogMessages.DATE_IS_FORMATTED_INCORRECTLY, Toast.LENGTH_SHORT).show();
                    return;
                } else if (!DateValidation.isStartDateBeforeEndDate(startDate.getText().toString(), endDate.getText().toString())) {
                    //Checks to ensure start date is before the end date
                    Toast.makeText(CreateOrUpdateTermActivity.this, DialogMessages.START_DATE_IS_AFTER_END_DATE, Toast.LENGTH_SHORT).show();
                    return;
                }

                Term saveTerm = getTermForAddOrUpdate();

                //Doesn't allow term to be added if the term name already exists in the database
                if (TermHelper.doesTermNameExistInDatabase(dbTermList, saveTerm)) {
                    Toast.makeText(CreateOrUpdateTermActivity.this, DialogMessages.TERM_ALREADY_EXISTS, Toast.LENGTH_SHORT).show();
                    return;
                } else if (TermHelper.doesTermDateOverlapWithTermInDatabase(dbTermList, saveTerm)) {
                    //Doesn't allow the term to be added if the start and end dates overlaps with a term in the database
                    Toast.makeText(CreateOrUpdateTermActivity.this, DialogMessages.TERM_DATES_OVERLAP_WITH_ANOTHER_TERMS_DATES, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (addOrUpdate.equals(SwitchScreen.ADD_TERM_VALUE)) {
                    try {
                        //Adds new term to the database
                        repository.insert(saveTerm);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    Toast.makeText(CreateOrUpdateTermActivity.this, "Created " + saveTerm.getTitle(), Toast.LENGTH_SHORT).show();
                    switchScreen(SwitchScreen.getActivityClass(activityCameFrom));
                } else {
                    try {
                        //Saves the updated term values in the database
                        repository.update(saveTerm);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    Toast.makeText(CreateOrUpdateTermActivity.this, "Updated " + saveTerm.getTitle(), Toast.LENGTH_SHORT).show();
                    switchScreen(SwitchScreen.getActivityClass(activityCameFrom), SwitchScreen.TERM_ID_KEY, String.valueOf(termId));
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
                                    Need this check because if the screen is for updating the term then we need to pass
                                    in the term id when switching back because the detailed term activity uses it
                                 */
                                if (addOrUpdate.equals(SwitchScreen.ADD_TERM_VALUE)) {
                                    switchScreen(SwitchScreen.getActivityClass(activityCameFrom));
                                } else {
                                    switchScreen(SwitchScreen.getActivityClass(activityCameFrom), SwitchScreen.TERM_ID_KEY, String.valueOf(termId));
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

    void switchScreen(Class className, String termIdKey, String termIdValue) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, className);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(termIdKey, termIdValue);
        //Need to always start the activity that you're going to
        startActivity(intent);
    }

    void setScreenInfo() {
        if (addOrUpdate.equals(SwitchScreen.ADD_TERM_VALUE)) {
            return;
        }
        termName.setText(term.getTitle());
        startDate.setText(term.getStartDate());
        endDate.setText(term.getEndDate());
    }

    void setTermId(Intent intent) {
        if (addOrUpdate.equals(SwitchScreen.ADD_TERM_VALUE)) {
            return;
        }
        termId = Integer.valueOf(intent.getStringExtra(SwitchScreen.TERM_ID_KEY));
    }

    void setTerm() {
        term = TermHelper.retrieveTermFromDatabaseByTermID(dbTermList, termId);
    }

    Term getTermForAddOrUpdate() {
        if (addOrUpdate.equals(SwitchScreen.ADD_TERM_VALUE)) {
            return new Term(termName.getText().toString().trim(), startDate.getText().toString(), endDate.getText().toString());
        }
        Term updateTerm = term;

        updateTerm.updateFields(termName, startDate, endDate);
        return updateTerm;
    }
}