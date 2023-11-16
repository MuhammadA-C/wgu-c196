package com.muhammadchambersc196.controller.detailed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.muhammadchambersc196.R;
import com.muhammadchambersc196.controller.create.CreateOrUpdateNoteActivity;
import com.muhammadchambersc196.database.Repository;
import com.muhammadchambersc196.entities.Course;
import com.muhammadchambersc196.entities.CourseNote;
import com.muhammadchambersc196.helper.CourseHelper;
import com.muhammadchambersc196.helper.CourseNoteHelper;
import com.muhammadchambersc196.helper.SwitchScreen;

import java.util.ArrayList;

public class DetailedNoteActivity extends AppCompatActivity {
    Repository repository;
    Button editBtn;
    Button shareBtn;
    Button backBtn;
    TextView notedDetails;
    int noteId;
    ArrayList<CourseNote> dbNoteList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_note);

        repository = new Repository(getApplication());
        Intent intent = getIntent();
        noteId = Integer.valueOf(intent.getStringExtra(SwitchScreen.COURSE_NOTE_ID_KEY));

        try {
            dbNoteList = (ArrayList<CourseNote>) repository.getmAllCourseNotes();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        CourseNote note = CourseNoteHelper.retrieveNoteFromDatabaseByNoteID(dbNoteList, noteId);

        //Gets references to the activities input fields
        editBtn = findViewById(R.id.detailed_note_edit_note_btn);
        notedDetails = findViewById(R.id.detailed_note_note);
        shareBtn = findViewById(R.id.detailed_note_share_note_btn);
        backBtn = findViewById(R.id.detailed_note_back_btn);

        notedDetails.setText(note.getNote());


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchScreen(DetailedCourseActivity.class, SwitchScreen.COURSE_ID_KEY, String.valueOf(note.getCourseID()));
            }
        });


        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchScreen(CreateOrUpdateNoteActivity.class, SwitchScreen.CAME_FROM_KEY, SwitchScreen.DETAILED_NOTE_ACTIVITY,
                        SwitchScreen.ADD_OR_UPDATE_SCREEN_KEY, SwitchScreen.UPDATE_NOTE_VALUE, SwitchScreen.COURSE_NOTE_ID_KEY, String.valueOf(noteId));
            }
        });


        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courseTitle;

                try {
                    courseTitle = CourseHelper.retrieveCourseFromDatabaseByCourseID((ArrayList<Course>) repository.getmAllCourses(), note.getCourseID()).getTitle();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                shareData(courseTitle, note.getNote());
            }
        });
    }

    void shareData(String courseName, String noteBody) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        //Needs to specify the type of data being shared
        intent.setType("text/plain");
        //Sets the subject for an email or other application that uses subject line
        intent.putExtra(Intent.EXTRA_SUBJECT, "Note for course: " + courseName);
        //Sets the text/body for an email or other application that uses text/body
        intent.putExtra(Intent.EXTRA_TEXT, noteBody);
        //Displays a list of applications to pick from
        startActivity(Intent.createChooser(intent, "Choose a Platform"));
    }

    void switchScreen(Class goToScreen, String idKey, String idValue) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, goToScreen);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(idKey, idValue);
        //Need to always start the activity that you're going to
        startActivity(intent);
    }

    void switchScreen(Class goToScreen, String cameFromScreenKey, String cameFromScreenValue, String addOrUpdateScreenKey,
                      String addOrUpdateScreenValue, String idKey, String idValue) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, goToScreen);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(cameFromScreenKey, cameFromScreenValue);
        intent.putExtra(addOrUpdateScreenKey, addOrUpdateScreenValue);
        intent.putExtra(idKey, idValue);
        //Need to always start the activity that you're going to
        startActivity(intent);
    }
}