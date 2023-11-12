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
import com.muhammadchambersc196.controller.create.CreateOrUpdateCourseActivity;
import com.muhammadchambersc196.controller.create.CreateOrUpdateTermActivity;
import com.muhammadchambersc196.database.Repository;
import com.muhammadchambersc196.entities.Term;
import com.muhammadchambersc196.helper.SwitchScreen;
import com.muhammadchambersc196.helper.TermHelper;

import java.util.ArrayList;

public class DetailedTermActivity extends AppCompatActivity {
    Repository repository;
    int termId;
    Button addCourseBtn;
    Button viewCourseBtn;
    Button deleteCourseBtn;
    RecyclerView classesList;
    TextView termName;
    TextView startDate;
    TextView endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_term);

        Intent intent = getIntent();
        //Need to pass the term id in
        termId = Integer.valueOf(intent.getStringExtra(SwitchScreen.TERM_ID_KEY));
        repository = new Repository(getApplication());

        addCourseBtn = findViewById(R.id.detailed_term_add_class_btn);
        viewCourseBtn = findViewById(R.id.detailed_term_view_class_btn);
        deleteCourseBtn = findViewById(R.id.detailed_term_delete_class_btn);
        classesList = findViewById(R.id.detailed_term_classes_list);
        termName = findViewById(R.id.detailed_term_term_name);
        startDate = findViewById(R.id.detailed_term_start_date);
        endDate = findViewById(R.id.detailed_term_end_date);

        setScreenInfo(termId);

        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchScreen(CreateOrUpdateCourseActivity.class, SwitchScreen.CAME_FROM_KEY, SwitchScreen.DETAILED_TERM_ACTIVITY, SwitchScreen.ADD_OR_UPDATE_SCREEN_KEY, SwitchScreen.ADD_COURSE_VALUE, SwitchScreen.TERM_ID_KEY, String.valueOf(termId));
            }
        });

        viewCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchScreen(DetailedCourseActivity.class, SwitchScreen.CAME_FROM_KEY, SwitchScreen.DETAILED_TERM_ACTIVITY);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detailed_term, menu);
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

        if (item.getTitle().equals(SwitchScreen.UPDATE_TERM_VALUE)) {
            switchScreen(CreateOrUpdateTermActivity.class, SwitchScreen.CAME_FROM_KEY, SwitchScreen.DETAILED_TERM_ACTIVITY, SwitchScreen.ADD_OR_UPDATE_SCREEN_KEY, SwitchScreen.UPDATE_TERM_VALUE, SwitchScreen.TERM_ID_KEY, String.valueOf(termId));
            return true;
        }
        return false;
    }

    /*
        Note:
           * For view course I need to pass in the course id
     */
    void switchScreen(Class className, String keyName, String value) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, className);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(keyName, value);
        //Note: Need to always start the activity that you're going to
        startActivity(intent);
    }

    void switchScreen(Class goToScreen, String cameFromScreenKey, String cameFromScreenValue, String termIdKey, String termIdValue) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, goToScreen);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(cameFromScreenKey, cameFromScreenValue);
        intent.putExtra(termIdKey, termIdValue);
        //Need to always start the activity that you're going to
        startActivity(intent);
    }

    void switchScreen(Class goToScreen, String cameFromScreenKey, String cameFromScreenValue, String addOrUpdateScreenKey, String addOrUpdateScreenValue, String termIdKey, String termIdValue) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, goToScreen);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(cameFromScreenKey, cameFromScreenValue);
        intent.putExtra(addOrUpdateScreenKey, addOrUpdateScreenValue);
        intent.putExtra(termIdKey, termIdValue);
        //Need to always start the activity that you're going to
        startActivity(intent);
    }

    void switchScreen(Class goToScreen, String cameFromScreenKey, String cameFromScreenValue, String addOrUpdateScreenKey, String addOrUpdateScreenValue, String courseIDKey, int courseIDValue) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, goToScreen);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(cameFromScreenKey, cameFromScreenValue);
        intent.putExtra(addOrUpdateScreenKey, addOrUpdateScreenValue);
        intent.putExtra(courseIDKey, courseIDValue);
        //Note: Need to always start the activity that you're going to
        startActivity(intent);
    }

    void setScreenInfo(int termId) {
        Term term;

        try {
            term = TermHelper.retrieveTermFromDatabaseByTermID((ArrayList<Term>) repository.getmAllTerms(), termId);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (term == null) {
            return;
        }

        termName.setText(term.getTitle());
        startDate.setText(term.getStartDate());
        endDate.setText(term.getEndDate());
    }
}