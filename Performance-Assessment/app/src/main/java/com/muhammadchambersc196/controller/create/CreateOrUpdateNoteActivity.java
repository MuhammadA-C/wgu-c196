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
import com.muhammadchambersc196.entities.CourseNote;
import com.muhammadchambersc196.helper.CourseNoteHelper;
import com.muhammadchambersc196.helper.DialogMessages;
import com.muhammadchambersc196.helper.InputValidation;
import com.muhammadchambersc196.helper.SwitchScreen;

import java.util.ArrayList;

public class CreateOrUpdateNoteActivity extends AppCompatActivity {
    Repository repository;
    int courseId;
    int noteId;
    String addOrUpdate;
    EditText noteDetails;
    Button saveBtn;
    Button cancelBtn;
    ArrayList<CourseNote> dbNoteList;
    CourseNote note;
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_update_note);

        repository = new Repository(getApplication());
        builder = new AlertDialog.Builder(this);

        try {
            dbNoteList = (ArrayList<CourseNote>) repository.getmAllCourseNotes();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //Retrieves the intent that was passed to this activity/screen
        Intent intent = getIntent();
        //Retrieves the data value/string name that was passed to this intent
        String activityCameFrom = intent.getStringExtra(SwitchScreen.CAME_FROM_KEY);
        addOrUpdate = intent.getStringExtra(SwitchScreen.ADD_OR_UPDATE_SCREEN_KEY);

        //Gets references to the activities input fields
        noteDetails = findViewById(R.id.create_note_details);
        saveBtn = findViewById(R.id.create_note_btn);
        cancelBtn = findViewById(R.id.create_cancel_btn);

        setNoteId(intent);
        setNote();
        setCourseId(intent);
        setTitle(addOrUpdate);

        setScreenInfo();


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Checks to ensure that the input fields are NOT empty
                if(InputValidation.isInputFieldEmpty(noteDetails)) {
                    Toast.makeText(CreateOrUpdateNoteActivity.this, DialogMessages.EMPTY_INPUT_FIELDS, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (addOrUpdate.equals(SwitchScreen.ADD_NOTE_VALUE)) {
                    CourseNote addNote = new CourseNote(noteDetails.getText().toString(), courseId);

                    try {
                        //Adds new course note to the database
                        repository.insert(addNote);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    Toast.makeText(CreateOrUpdateNoteActivity.this, "Created note", Toast.LENGTH_SHORT).show();
                    switchScreen(SwitchScreen.getActivityClass(activityCameFrom), SwitchScreen.COURSE_ID_KEY,
                            String.valueOf(courseId));
                } else {
                    CourseNote updateNote = CourseNoteHelper.retrieveNoteFromDatabaseByNoteID(dbNoteList, Integer.valueOf(noteId));

                    updateNote.setNote(noteDetails.getText().toString());

                    try {
                        repository.update(updateNote);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    Toast.makeText(CreateOrUpdateNoteActivity.this, "Updated note", Toast.LENGTH_SHORT).show();
                    switchScreen(SwitchScreen.getActivityClass(activityCameFrom), SwitchScreen.COURSE_NOTE_ID_KEY, String.valueOf(noteId));
                }
            }
        });


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Displays a confirmation box for the user to confirm if they want to cancel
                builder.setTitle(DialogMessages.CANCEL_CONFIRMATION)
                        .setMessage(DialogMessages.CANCEL_CONFORMATION_MESSAGE)
                        .setCancelable(true)
                        .setPositiveButton(DialogMessages.YES, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                /*
                                    * If this screen was for adding a note, then we must pass in the course id
                                    * If this screen was not for updating a note, then we must pass in the course note id

                                    Note: Due to the only screen being able to call the update course note screen is
                                    from the detailed course note screen, the detailed course note screen will need the
                                    course note id since it uses it to display the course note information
                                 */
                                if (addOrUpdate.equals(SwitchScreen.ADD_NOTE_VALUE)) {
                                    switchScreen(SwitchScreen.getActivityClass(activityCameFrom), SwitchScreen.COURSE_ID_KEY, String.valueOf(courseId));
                                } else {
                                    switchScreen(SwitchScreen.getActivityClass(activityCameFrom), SwitchScreen.COURSE_NOTE_ID_KEY, String.valueOf(noteId));
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

    void switchScreen(Class className, String idKey, String idValue) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, className);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(idKey, idValue);
        //Need to always start the activity that you're going to
        startActivity(intent);
    }

    void setNoteId(Intent intent) {
        if (addOrUpdate.equals(SwitchScreen.ADD_NOTE_VALUE)) {
            return;
        }
        noteId = Integer.valueOf(intent.getStringExtra(SwitchScreen.COURSE_NOTE_ID_KEY));
    }

    void setNote() {
       note = CourseNoteHelper.retrieveNoteFromDatabaseByNoteID(dbNoteList, noteId);
    }

    void setCourseId(Intent intent) {
        /*
            If this is for adding a note, then the course id would have been passed in from the prior screen.
            Otherwise, we can obtain the course note from the course note object.
         */
        if (addOrUpdate.equals(SwitchScreen.ADD_NOTE_VALUE)) {
            courseId = Integer.valueOf(intent.getStringExtra(SwitchScreen.COURSE_ID_KEY));
        } else {
            courseId = CourseNoteHelper.retrieveNoteFromDatabaseByNoteID(dbNoteList, noteId).getCourseID();
        }
    }

    void setScreenInfo() {
        if (addOrUpdate.equals(SwitchScreen.ADD_NOTE_VALUE)) {
            return;
        }

        CourseNote note = CourseNoteHelper.retrieveNoteFromDatabaseByNoteID(dbNoteList, noteId);

        noteDetails.setText(note.getNote());
    }
}