package com.muhammadchambersc196.controller.detailed;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
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
import com.muhammadchambersc196.controller.create.CreateOrUpdateAssessmentActivity;
import com.muhammadchambersc196.database.Repository;
import com.muhammadchambersc196.entities.Assessment;
import com.muhammadchambersc196.helper.AssessmentHelper;
import com.muhammadchambersc196.helper.DateFormat;
import com.muhammadchambersc196.helper.SwitchScreen;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DetailedAssessmentActivity extends AppCompatActivity {
    Repository repository;
    Button editBtn;
    Button backBtn;
    TextView assessmentName;
    TextView assessmentType;
    TextView startDate;
    TextView endDate;
    TextView assessmentInfo;
    int assessmentId;
    ArrayList<Assessment> dbAssessmentList;
    Assessment assessment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_assessment);

        repository = new Repository(getApplication());

        Intent intent = getIntent();

        try {
            dbAssessmentList = (ArrayList<Assessment>) repository.getmAllAssessments();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assessmentId = Integer.valueOf(intent.getStringExtra(SwitchScreen.ASSESSMENT_ID_KEY));
        assessment = AssessmentHelper.retrieveAssessmentFromDatabaseByAssessmentID(dbAssessmentList, assessmentId);

        //Gets references to the activities input fields
        editBtn = findViewById(R.id.detailed_assessment_edit_btn);
        assessmentName = findViewById(R.id.detailed_assessment_name);
        assessmentType = findViewById(R.id.detailed_assessment_type);
        startDate = findViewById(R.id.detailed_assessment_start_date);
        endDate = findViewById(R.id.detailed_assessment_end_date);
        backBtn = findViewById(R.id.detailed_assessment_back_btn);
        assessmentInfo = findViewById(R.id.detailed_assessment_Info);

        setScreenInfo();


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int courseId = assessment.getCourseID();

                switchScreen(DetailedCourseActivity.class, SwitchScreen.COURSE_ID_KEY, String.valueOf(courseId));
            }
        });


        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchScreen(CreateOrUpdateAssessmentActivity.class, SwitchScreen.CAME_FROM_KEY,
                        SwitchScreen.DETAILED_ASSESSMENT_ACTIVITY, SwitchScreen.ADD_OR_UPDATE_SCREEN_KEY,
                        SwitchScreen.UPDATE_ASSESSMENT_VALUE, SwitchScreen.ASSESSMENT_ID_KEY, String.valueOf(assessmentId));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detailed_assessment, menu);
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

        if (item.getTitle().equals(getString(R.string.menu_notify_for_start_date))) {
            SimpleDateFormat originalFormatter = new SimpleDateFormat(DateFormat.longDateFormat, Locale.US);
            SimpleDateFormat targetFormatter = new SimpleDateFormat(DateFormat.shortDateFormat, Locale.US);

            Date formattedStartDate = null;

            try {
                String formattedStartDateStr = targetFormatter.format(originalFormatter.parse(assessment.getStartDate()));
                formattedStartDate = targetFormatter.parse(formattedStartDateStr);

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            Long trigger = formattedStartDate.getTime();
            Intent intent = new Intent(DetailedAssessmentActivity.this, MyReceiver.class);
            intent.putExtra("key", "Assessment, " + assessment.getTitle() + ", starts today!");

            PendingIntent sender = PendingIntent.getBroadcast(DetailedAssessmentActivity.this, ++HomeScreenActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            Toast.makeText(DetailedAssessmentActivity.this, "Alert was set!", Toast.LENGTH_SHORT).show();
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, trigger, sender);
            return true;
        } else if (item.getTitle().equals(getString(R.string.menu_notify_for_end_date))) {
            SimpleDateFormat originalFormatter = new SimpleDateFormat(DateFormat.longDateFormat, Locale.US);
            SimpleDateFormat targetFormatter = new SimpleDateFormat(DateFormat.shortDateFormat, Locale.US);

            Date formattedEndDate = null;

            try {
                String formattedEndDateStr = targetFormatter.format(originalFormatter.parse(assessment.getEndDate()));
                formattedEndDate = targetFormatter.parse(formattedEndDateStr);

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            Long trigger = formattedEndDate.getTime();
            Intent intent = new Intent(DetailedAssessmentActivity.this, MyReceiver.class);
            intent.putExtra("key", "Assessment, " + assessment.getTitle() + ", ends today!");

            PendingIntent sender = PendingIntent.getBroadcast(DetailedAssessmentActivity.this, ++HomeScreenActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            Toast.makeText(DetailedAssessmentActivity.this, "Alert was set!", Toast.LENGTH_SHORT).show();
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, trigger, sender);
            return true;
        }
        return false;
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

    void switchScreen(Class goToScreen, String idKey, String idValue) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, goToScreen);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(idKey, idValue);
        //Need to always start the activity that you're going to
        startActivity(intent);
    }

    void setScreenInfo() {
        assessmentName.setText(assessment.getTitle());
        assessmentType.setText(assessment.getType());
        assessmentInfo.setText(assessment.getInformation());
        startDate.setText(assessment.getStartDate());
        endDate.setText(assessment.getEndDate());
    }
}