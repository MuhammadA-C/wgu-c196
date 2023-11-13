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
import com.muhammadchambersc196.controller.create.CreateOrUpdateCourseActivity;
import com.muhammadchambersc196.controller.create.CreateNoteActivity;
import com.muhammadchambersc196.database.Repository;
import com.muhammadchambersc196.entities.Course;
import com.muhammadchambersc196.helper.CourseHelper;
import com.muhammadchambersc196.helper.SwitchScreen;

import java.util.ArrayList;

public class DetailedCourseActivity extends AppCompatActivity {
    Repository repository;
    int courseId;
    Button viewAssignmentBtn;
    Button deleteAssessmentBtn;
    Button viewNoteBtn;
    Button deleteNoteBtn;
    TextView className;
    TextView startDate;
    TextView endDate;
    TextView instructorName;
    RecyclerView assessmentsList;
    RecyclerView notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_course);

        repository = new Repository(getApplication());
        Intent intent = getIntent();
        courseId = Integer.valueOf(intent.getStringExtra(SwitchScreen.COURSE_ID_KEY));

        className = findViewById(R.id.detailed_class_name);
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
                switchScreen(DetailedAssessmentActivity.class, SwitchScreen.CAME_FROM_KEY, SwitchScreen.DETAILED_COURSE_ACTIVITY);
            }
        });

        viewNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchScreen(DetailedNoteActivity.class, SwitchScreen.CAME_FROM_KEY, SwitchScreen.DETAILED_COURSE_ACTIVITY);
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

        if (item.getTitle().equals(SwitchScreen.UPDATE_COURSE_VALUE)) {
            switchScreen(CreateOrUpdateCourseActivity.class, SwitchScreen.CAME_FROM_KEY, SwitchScreen.DETAILED_COURSE_ACTIVITY, SwitchScreen.ADD_OR_UPDATE_SCREEN_KEY, SwitchScreen.UPDATE_COURSE_VALUE);
            return true;
        } else if (item.getTitle().equals("Add Assessment")) {
            switchScreen(CreateAssessmentActivity.class, SwitchScreen.CAME_FROM_KEY, SwitchScreen.DETAILED_COURSE_ACTIVITY);
            return true;
        } else if (item.getTitle().equals("Add Note")) {
            switchScreen(CreateNoteActivity.class, SwitchScreen.CAME_FROM_KEY, SwitchScreen.DETAILED_COURSE_ACTIVITY);
            return true;
        }
        return false;
    }

    /*
        Note:
           * For update course I need to pass in the course id
           * For add assessment I need to pass in the course id
           * For add note I need to pass in the course id
           * For view not I need to pass in the selected note id
           * For view assessment I need to pass in the selected assessment id
     */
    void switchScreen(Class className, String keyName, String value) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, className);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(keyName, value);
        //Note: Need to always start the activity that you're going to
        startActivity(intent);
    }

    void switchScreen(Class goToScreen, String cameFromScreenKey, String cameFromScreenValue, String addOrUpdateScreenKey, String addOrUpdateScreenValue) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, goToScreen);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(cameFromScreenKey, cameFromScreenValue);
        intent.putExtra(addOrUpdateScreenKey, addOrUpdateScreenValue);
        //Note: Need to always start the activity that you're going to
        startActivity(intent);
    }


    void switchScreen(Class goToScreen, String cameFromScreenKey, String cameFromScreenValue, String addOrUpdateScreenKey, String addOrUpdateScreenValue, String idKey, int idValue) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, goToScreen);

        //Specifies the data to pass to the new activity/screen
        intent.putExtra(cameFromScreenKey, cameFromScreenValue);
        intent.putExtra(addOrUpdateScreenKey, addOrUpdateScreenValue);
        intent.putExtra(idKey, idValue);

        //Need to always start the activity that you're going to
        startActivity(intent);
    }

    void setScreenInfo(int termId) {
        Course course;

        try {
            course = CourseHelper.retrieveCourseFromDatabaseByTermID((ArrayList<Course>) repository.getmAllCourses(), courseId);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (course == null) {
            return;
        }

        className.setText(course.getTitle());
        startDate.setText(course.getStartDate());
        endDate.setText(course.getEndDate());
        //instructorName.setText(course.getIn);
    }
}