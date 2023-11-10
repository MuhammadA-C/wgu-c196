package com.muhammadchambersc196.controller.detailed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.muhammadchambersc196.R;
import com.muhammadchambersc196.controller.create.CreateAssessmentActivity;
import com.muhammadchambersc196.controller.create.CreateNoteActivity;
import com.muhammadchambersc196.controller.update.UpdateCourseActivity;
import com.muhammadchambersc196.helper.SwitchScreen;

public class DetailedCourseActivity extends AppCompatActivity {
    Button viewAssignmentBtn;
    Button deleteAssessmentBtn;
    Button viewNoteBtn;
    Button deleteNoteBtn;
    TextView startDate;
    TextView endDate;
    TextView instructorName;
    RecyclerView assessmentsList;
    RecyclerView notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_course);

       viewAssignmentBtn = findViewById(R.id.detailed_class_view_assignment_btn);
        startDate = findViewById(R.id.detailed_class_start_date);
        endDate = findViewById(R.id.detailed_class_end_date);
        instructorName = findViewById(R.id.detailed_ci_name);
        assessmentsList = findViewById(R.id.detailed_class_assessments_list);
        deleteAssessmentBtn = findViewById(R.id.detailed_class_delete_assignment_btn);
        viewNoteBtn = findViewById(R.id.detailed_class_view_note_btn);
        deleteNoteBtn = findViewById(R.id.detailed_class_delete_note_btn);
        notesList = findViewById(R.id.detailed_class_notes_list);

        viewAssignmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNewScreen(DetailedAssessmentActivity.class, SwitchScreen.CAME_FROM, SwitchScreen.DETAILED_COURSE_ACTIVITY);
            }
        });

        viewNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNewScreen(DetailedNoteActivity.class, SwitchScreen.CAME_FROM, SwitchScreen.DETAILED_COURSE_ACTIVITY);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detailed_course, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
            Note: Need this check because .getTitle() can return null and this can cause the
            .equals() check to crash the app
         */
        if (item.getTitle() == null) {
            return false;
        }

        if (item.getTitle().equals("Update Course")) {
            goToNewScreen(UpdateCourseActivity.class, SwitchScreen.CAME_FROM, SwitchScreen.DETAILED_COURSE_ACTIVITY);
            return true;
        } else if (item.getTitle().equals("Add Assessment")) {
            goToNewScreen(CreateAssessmentActivity.class, SwitchScreen.CAME_FROM, SwitchScreen.DETAILED_COURSE_ACTIVITY);
            return true;
        } else if (item.getTitle().equals("Add Note")) {
            goToNewScreen(CreateNoteActivity.class, SwitchScreen.CAME_FROM, SwitchScreen.DETAILED_COURSE_ACTIVITY);
            return true;
        }
        return false;
    }

    void goToNewScreen(Class className, String keyName, String value) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, className);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(keyName, value);
        //Note: Need to always start the activity that you're going to
        startActivity(intent);
    }

    //Will be used to pass the course id to the create assessment view
    void goToNewScreen(Class className, String keyName1, String value1, String keyName2, String value2) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, className);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(keyName1, value1);
        intent.putExtra(keyName2, value2);
        //Note: Need to always start the activity that you're going to
        startActivity(intent);
    }
}