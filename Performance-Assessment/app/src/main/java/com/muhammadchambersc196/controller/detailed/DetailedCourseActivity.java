package com.muhammadchambersc196.controller.detailed;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.muhammadchambersc196.R;
import com.muhammadchambersc196.controller.HomeScreenActivity;
import com.muhammadchambersc196.controller.MyReceiver;
import com.muhammadchambersc196.controller.adapter.AssessmentAdapter;
import com.muhammadchambersc196.controller.adapter.NoteAdapter;
import com.muhammadchambersc196.controller.create.CreateOrUpdateAssessmentActivity;
import com.muhammadchambersc196.controller.create.CreateOrUpdateCourseActivity;
import com.muhammadchambersc196.controller.create.CreateOrUpdateNoteActivity;
import com.muhammadchambersc196.controller.create.CreateOrUpdateTermActivity;
import com.muhammadchambersc196.database.Repository;
import com.muhammadchambersc196.entities.Assessment;
import com.muhammadchambersc196.entities.Course;
import com.muhammadchambersc196.entities.CourseInstructor;
import com.muhammadchambersc196.entities.CourseNote;
import com.muhammadchambersc196.helper.AssessmentHelper;
import com.muhammadchambersc196.helper.CourseHelper;
import com.muhammadchambersc196.helper.DateFormat;
import com.muhammadchambersc196.helper.DateValidation;
import com.muhammadchambersc196.helper.DialogMessages;
import com.muhammadchambersc196.helper.InstructorHelper;
import com.muhammadchambersc196.helper.SelectedListItem;
import com.muhammadchambersc196.helper.SwitchScreen;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

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
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_course);

        repository = new Repository(getApplication());
        builder = new AlertDialog.Builder(this);

        try {
            dbCourseList = (ArrayList<Course>) repository.getmAllCourses();
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

        try {
            setScreenInfo();
            setAssessmentRecyclerView();
            setCourseNoteRecyclerView();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        viewAssignmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SelectedListItem.getSelectedAssessment() == null) {
                    Toast.makeText(DetailedCourseActivity.this, DialogMessages.NEED_TO_SELECT_AN_ASSESSMENT + " view", Toast.LENGTH_SHORT).show();
                    return;
                }

                Assessment assessment = SelectedListItem.getSelectedAssessment();

                SelectedListItem.setSelectedAssessment(null);

                switchScreen(DetailedAssessmentActivity.class, SwitchScreen.CAME_FROM_KEY, SwitchScreen.DETAILED_COURSE_ACTIVITY,
                        SwitchScreen.ASSESSMENT_ID_KEY, String.valueOf(assessment.getAssessmentID()));
            }
        });


        viewNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SelectedListItem.getSelectedNote() == null) {
                    Toast.makeText(DetailedCourseActivity.this, DialogMessages.NEED_TO_SELECT_A_NOTE + " view", Toast.LENGTH_SHORT).show();
                    return;
                }

                CourseNote note = SelectedListItem.getSelectedNote();

                SelectedListItem.setSelectedNote(null);

                switchScreen(DetailedNoteActivity.class, SwitchScreen.CAME_FROM_KEY, SwitchScreen.DETAILED_COURSE_ACTIVITY,
                        SwitchScreen.COURSE_NOTE_ID_KEY, String.valueOf(note.getCourseNoteID()));
            }
        });

        
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Need to pass in term id because it is used on the detailed term screen to populate it
                int termId = CourseHelper.retrieveCourseFromDatabaseByCourseID(dbCourseList, courseId).getTermID();

                SelectedListItem.setSelectedNote(null);
                SelectedListItem.setSelectedAssessment(null);

                switchScreen(DetailedTermActivity.class, SwitchScreen.TERM_ID_KEY, String.valueOf(termId));
            }
        });


        deleteNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SelectedListItem.getSelectedNote() == null) {
                    Toast.makeText(DetailedCourseActivity.this, DialogMessages.NEED_TO_SELECT_A_NOTE + " delete", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Displays a confirmation box for the user to confirm if they want to cancel
                builder.setTitle(DialogMessages.CONFIRMATION)
                        .setMessage(DialogMessages.DELETE_CONFIRMATION_MESSAGE + "the note?")
                        .setCancelable(true)
                        .setPositiveButton(DialogMessages.YES, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try {
                                    Toast.makeText(DetailedCourseActivity.this, "Deleted note", Toast.LENGTH_SHORT).show();
                                    repository.delete(SelectedListItem.getSelectedNote());
                                    SelectedListItem.setSelectedNote(null);
                                    setCourseNoteRecyclerView();
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
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

        deleteAssessmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SelectedListItem.getSelectedAssessment() == null) {
                    Toast.makeText(DetailedCourseActivity.this, DialogMessages.NEED_TO_SELECT_AN_ASSESSMENT + " delete", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Displays a confirmation box for the user to confirm if they want to cancel
                builder.setTitle(DialogMessages.CONFIRMATION)
                        .setMessage(DialogMessages.DELETE_CONFIRMATION_MESSAGE + SelectedListItem.getSelectedAssessment().getTitle() + "?")
                        .setCancelable(true)
                        .setPositiveButton(DialogMessages.YES, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try {
                                    Toast.makeText(DetailedCourseActivity.this, "Deleted " + SelectedListItem.getSelectedAssessment().getTitle(), Toast.LENGTH_SHORT).show();
                                    repository.delete(SelectedListItem.getSelectedAssessment());
                                    SelectedListItem.setSelectedAssessment(null);
                                    setAssessmentRecyclerView();
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
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

        if (item.getTitle().equals(getString(R.string.menu_update_course))) {
            switchScreen(CreateOrUpdateCourseActivity.class, SwitchScreen.CAME_FROM_KEY, SwitchScreen.DETAILED_COURSE_ACTIVITY,
                    SwitchScreen.ADD_OR_UPDATE_SCREEN_KEY, SwitchScreen.UPDATE_COURSE_VALUE, SwitchScreen.COURSE_ID_KEY, String.valueOf(courseId));
            return true;
        } else if (item.getTitle().equals(getString(R.string.menu_add_assessment))) {
            switchScreen(CreateOrUpdateAssessmentActivity.class, SwitchScreen.CAME_FROM_KEY, SwitchScreen.DETAILED_COURSE_ACTIVITY,
                    SwitchScreen.ADD_OR_UPDATE_SCREEN_KEY, SwitchScreen.ADD_ASSESSMENT_VALUE, SwitchScreen.COURSE_ID_KEY, String.valueOf(courseId));
            return true;
        } else if (item.getTitle().equals(getString(R.string.menu_add_note))) {
            switchScreen(CreateOrUpdateNoteActivity.class, SwitchScreen.CAME_FROM_KEY, SwitchScreen.DETAILED_COURSE_ACTIVITY,
                    SwitchScreen.ADD_OR_UPDATE_SCREEN_KEY, SwitchScreen.ADD_NOTE_VALUE, SwitchScreen.COURSE_ID_KEY, String.valueOf(courseId));
            return true;
        } else if (item.getTitle().equals(getString(R.string.menu_notify_for_start_date))) {
            Course course = CourseHelper.retrieveCourseFromDatabaseByCourseID(dbCourseList, courseId);
            SimpleDateFormat originalFormatter = new SimpleDateFormat(DateFormat.longDateFormat, Locale.US);
            SimpleDateFormat targetFormatter = new SimpleDateFormat(DateFormat.shortDateFormat, Locale.US);

            Date formattedStartDate = null;

            try {
                String formattedStartDateStr = targetFormatter.format(originalFormatter.parse(course.getStartDate()));
                formattedStartDate = targetFormatter.parse(formattedStartDateStr);

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            Long trigger = formattedStartDate.getTime();
            Intent intent = new Intent(DetailedCourseActivity.this, MyReceiver.class);
            intent.putExtra("key", "Class, " + course.getTitle() + ", starts today!");

            PendingIntent sender = PendingIntent.getBroadcast(DetailedCourseActivity.this, ++HomeScreenActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            return true;
        } else if (item.getTitle().equals(getString(R.string.menu_notify_for_end_date))) {
            Course course = CourseHelper.retrieveCourseFromDatabaseByCourseID(dbCourseList, courseId);
            SimpleDateFormat originalFormatter = new SimpleDateFormat(DateFormat.longDateFormat, Locale.US);
            SimpleDateFormat targetFormatter = new SimpleDateFormat(DateFormat.shortDateFormat, Locale.US);

            Date formattedEndDate = null;

            try {
                String formattedEndDateStr = targetFormatter.format(originalFormatter.parse(course.getEndDate()));
                formattedEndDate = targetFormatter.parse(formattedEndDateStr);

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            Long trigger = formattedEndDate.getTime();
            Intent intent = new Intent(DetailedCourseActivity.this, MyReceiver.class);
            intent.putExtra("key", "Class, " + course.getTitle() + ", ends today!");

            PendingIntent sender = PendingIntent.getBroadcast(DetailedCourseActivity.this, ++HomeScreenActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            return true;
        }
        return false;
    }
    
    void switchScreen(Class className, String keyName, String value) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, className);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(keyName, value);
        //Need to always start the activity that you're going to
        startActivity(intent);
    }

    void switchScreen(Class className, String keyName, String value, String idKey, String idValue) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, className);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(keyName, value);
        intent.putExtra(idKey, idValue);
        //Need to always start the activity that you're going to
        startActivity(intent);
    }

    void switchScreen(Class goToScreen, String cameFromScreenKey, String cameFromScreenValue,
                      String addOrUpdateScreenKey, String addOrUpdateScreenValue, String idKey, String idValue) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, goToScreen);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(cameFromScreenKey, cameFromScreenValue);
        intent.putExtra(addOrUpdateScreenKey, addOrUpdateScreenValue);
        intent.putExtra(idKey, idValue);
        //Need to always start the activity that you're going to
        startActivity(intent);
    }

    void setScreenInfo() throws InterruptedException {
        Course course = CourseHelper.retrieveCourseFromDatabaseByCourseID(dbCourseList, courseId);

        startDate.setText(course.getStartDate());
        endDate.setText(course.getEndDate());
        instructorName.setText(InstructorHelper.retrieveCourseFromDatabaseByInstructorID((ArrayList<CourseInstructor>) repository.getmAllCourseInstructors(), course.getInstructorID()).getName());
    }

    void setAssessmentRecyclerView() throws InterruptedException {
        final AssessmentAdapter termAdapter = new AssessmentAdapter(this);

        List<Assessment> assessmentsForCourse = AssessmentHelper.getAllAssessmentsForCourse((ArrayList<Assessment>)repository.getmAllAssessments(), courseId);

        assessmentsList.setAdapter(termAdapter);
        assessmentsList.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setAssessments(assessmentsForCourse);
    }

    void setCourseNoteRecyclerView() throws InterruptedException {
        final NoteAdapter termAdapter = new NoteAdapter(this);

        List<CourseNote> notesForCourse = CourseHelper.getAllNotesForCourse( (ArrayList<CourseNote>) repository.getmAllCourseNotes(), courseId);

        notesList.setAdapter(termAdapter);
        notesList.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setNotes(notesForCourse);
    }
}