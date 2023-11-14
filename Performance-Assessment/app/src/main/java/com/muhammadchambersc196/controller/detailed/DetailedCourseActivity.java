package com.muhammadchambersc196.controller.detailed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.muhammadchambersc196.R;
import com.muhammadchambersc196.controller.adapter.AssessmentAdapter;
import com.muhammadchambersc196.controller.adapter.NoteAdapter;
import com.muhammadchambersc196.controller.adapter.TermAdapter;
import com.muhammadchambersc196.controller.create.CreateOrUpdateAssessmentActivity;
import com.muhammadchambersc196.controller.create.CreateOrUpdateCourseActivity;
import com.muhammadchambersc196.controller.create.CreateOrUpdateNoteActivity;
import com.muhammadchambersc196.database.Repository;
import com.muhammadchambersc196.entities.Assessment;
import com.muhammadchambersc196.entities.Course;
import com.muhammadchambersc196.entities.CourseInstructor;
import com.muhammadchambersc196.entities.CourseNote;
import com.muhammadchambersc196.entities.Term;
import com.muhammadchambersc196.helper.CourseHelper;
import com.muhammadchambersc196.helper.InstructorHelper;
import com.muhammadchambersc196.helper.SwitchScreen;

import java.util.ArrayList;
import java.util.List;

public class DetailedCourseActivity extends AppCompatActivity {
    Repository repository;
    int courseId;
    Button viewAssignmentBtn;
    Button deleteAssessmentBtn;
    Button viewNoteBtn;
    Button backBtn;
    Button deleteNoteBtn;
    TextView startDate;
    TextView endDate;
    TextView instructorName;
    RecyclerView assessmentsList;
    RecyclerView notesList;
    ArrayList<Course> dbCourseList;
    ArrayList<CourseInstructor> dbInstructorList;
    List<CourseNote> dbNoteList;
    List<Assessment> dbAssessmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_course);

        repository = new Repository(getApplication());

        try {
            dbCourseList = (ArrayList<Course>) repository.getmAllCourses();
            dbInstructorList = (ArrayList<CourseInstructor>) repository.getmAllCourseInstructors();
            dbNoteList = repository.getmAllCourseNotes();
            dbAssessmentList = repository.getmAllAssessments();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Intent intent = getIntent();
        courseId = Integer.valueOf(intent.getStringExtra(SwitchScreen.COURSE_ID_KEY));

        //Gets references to the activities input fields
        viewAssignmentBtn = findViewById(R.id.detailed_class_view_assignment_btn);
        startDate = findViewById(R.id.detailed_class_start_date);
        endDate = findViewById(R.id.detailed_class_end_date);
        instructorName = findViewById(R.id.detailed_ci_name);
        assessmentsList = findViewById(R.id.detailed_class_assessments_list);
        deleteAssessmentBtn = findViewById(R.id.detailed_class_delete_assignment_btn);
        viewNoteBtn = findViewById(R.id.detailed_class_view_note_btn);
        deleteNoteBtn = findViewById(R.id.detailed_class_delete_note_btn);
        notesList = findViewById(R.id.detailed_class_notes_list);
        backBtn = findViewById(R.id.detailed_course_back_btn);

        //Sets the course name at the top in the action bar
        setTitle(CourseHelper.retrieveCourseFromDatabaseByCourseID(dbCourseList, courseId).getTitle());
        setScreenInfo();
        setAssessmentRecyclerView();
        setCourseNoteRecyclerView();


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

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Need to pass in term id because it is used on the detailed term screen to populate it
                int termId = CourseHelper.retrieveCourseFromDatabaseByCourseID(dbCourseList, courseId).getTermID();

                switchScreen(DetailedTermActivity.class, SwitchScreen.TERM_ID_KEY, String.valueOf(termId));
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
            switchScreen(CreateOrUpdateCourseActivity.class, SwitchScreen.CAME_FROM_KEY, SwitchScreen.DETAILED_COURSE_ACTIVITY,
                    SwitchScreen.ADD_OR_UPDATE_SCREEN_KEY, SwitchScreen.UPDATE_COURSE_VALUE, SwitchScreen.COURSE_ID_KEY, String.valueOf(courseId));
            return true;
        } else if (item.getTitle().equals(SwitchScreen.ADD_ASSESSMENT_VALUE)) {
            //Need to pass in the term id
            switchScreen(CreateOrUpdateAssessmentActivity.class, SwitchScreen.CAME_FROM_KEY, SwitchScreen.DETAILED_COURSE_ACTIVITY);
            return true;
        } else if (item.getTitle().equals(SwitchScreen.ADD_NOTE_VALUE)) {
            //Need to pass in the term id
            switchScreen(CreateOrUpdateNoteActivity.class, SwitchScreen.CAME_FROM_KEY, SwitchScreen.DETAILED_COURSE_ACTIVITY);
            return true;
        }
        return false;
    }

    /*
        Note:
           * For update course I need to pass in the course id
           * For add assessment I need to pass in the course id
           * For add note I need to pass in the course id
           * For view note I need to pass in the selected note id
           * For view assessment I need to pass in the selected assessment id
     */
    void switchScreen(Class className, String keyName, String value) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, className);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(keyName, value);
        //Need to always start the activity that you're going to
        startActivity(intent);
    }

    void switchScreen(Class goToScreen, String cameFromScreenKey, String cameFromScreenValue, String addOrUpdateScreenKey, String addOrUpdateScreenValue) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, goToScreen);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(cameFromScreenKey, cameFromScreenValue);
        intent.putExtra(addOrUpdateScreenKey, addOrUpdateScreenValue);
        //Need to always start the activity that you're going to
        startActivity(intent);
    }

    void switchScreen(Class goToScreen, String cameFromScreenKey, String cameFromScreenValue, String addOrUpdateScreenKey, String addOrUpdateScreenValue, String idKey, String idValue) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, goToScreen);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(cameFromScreenKey, cameFromScreenValue);
        intent.putExtra(addOrUpdateScreenKey, addOrUpdateScreenValue);
        intent.putExtra(idKey, idValue);
        //Need to always start the activity that you're going to
        startActivity(intent);
    }

    void setScreenInfo() {
        Course course = CourseHelper.retrieveCourseFromDatabaseByCourseID(dbCourseList, courseId);

        startDate.setText(course.getStartDate());
        endDate.setText(course.getEndDate());
        instructorName.setText(InstructorHelper.retrieveCourseFromDatabaseByInstructorID(dbInstructorList, course.getInstructorID()).getName());
    }

    void setAssessmentRecyclerView() {
        final AssessmentAdapter termAdapter = new AssessmentAdapter(this);

        assessmentsList.setAdapter(termAdapter);
        assessmentsList.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setAssessments(dbAssessmentList);
    }

    void setCourseNoteRecyclerView() {
        final NoteAdapter termAdapter = new NoteAdapter(this);

        notesList.setAdapter(termAdapter);
        notesList.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setNotes(dbNoteList);
    }
}