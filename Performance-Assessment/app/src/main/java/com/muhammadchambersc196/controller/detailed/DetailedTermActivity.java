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
import com.muhammadchambersc196.controller.create.CreateCourseActivity;
import com.muhammadchambersc196.controller.create.CreateTermActivity;
import com.muhammadchambersc196.database.Repository;
import com.muhammadchambersc196.entities.Term;
import com.muhammadchambersc196.helper.Helper;
import com.muhammadchambersc196.helper.SwitchScreen;

import java.util.ArrayList;

public class DetailedTermActivity extends AppCompatActivity {
    Repository repository;
    Button addBtn;
    Button viewBtn;
    Button deleteBtn;
    RecyclerView classesList;
    TextView termName;
    TextView startDate;
    TextView endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_term);

        repository = new Repository(getApplication());

        addBtn = findViewById(R.id.detailed_term_add_class_btn);
        viewBtn = findViewById(R.id.detailed_term_view_class_btn);
        deleteBtn = findViewById(R.id.detailed_term_delete_class_btn);
        classesList = findViewById(R.id.detailed_term_classes_list);
        termName = findViewById(R.id.detailed_term_term_name);
        startDate = findViewById(R.id.detailed_term_start_date);
        endDate = findViewById(R.id.detailed_term_end_date);

        setScreenInfo();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNewScreen(CreateCourseActivity.class, SwitchScreen.CAME_FROM, SwitchScreen.DETAILED_TERM_ACTIVITY);
            }
        });

        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNewScreen(DetailedCourseActivity.class, SwitchScreen.CAME_FROM, SwitchScreen.DETAILED_TERM_ACTIVITY);
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

        if (item.getTitle().equals("Update Term")) {
            goToNewScreen(CreateTermActivity.class, SwitchScreen.CAME_FROM, SwitchScreen.DETAILED_TERM_ACTIVITY, SwitchScreen.ADD_OR_UPDATE_SCREEN, "Update Term");
            return true;
        }
        return false;
    }

    /*
        Note:
           * For update term I need to pass in the term id
           * For add course I need to pass in the term id
           * For view course I need to pass in the course id
     */
    void goToNewScreen(Class className, String keyName, String value) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, className);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(keyName, value);
        //Note: Need to always start the activity that you're going to
        startActivity(intent);
    }

    void goToNewScreen(Class goToScreen, String cameFromScreenKey, String cameFromScreenValue, String addOrUpdateScreenKey, String addOrUpdateScreenValue) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, goToScreen);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(cameFromScreenKey, cameFromScreenValue);
        intent.putExtra(addOrUpdateScreenKey, addOrUpdateScreenValue);
        //Note: Need to always start the activity that you're going to
        startActivity(intent);
    }

    void goToNewScreen(Class goToScreen, String cameFromScreenKey, String cameFromScreenValue, String addOrUpdateScreenKey, String addOrUpdateScreenValue, String courseIDKey, int courseIDValue) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, goToScreen);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(cameFromScreenKey, cameFromScreenValue);
        intent.putExtra(addOrUpdateScreenKey, addOrUpdateScreenValue);
        intent.putExtra(courseIDKey, courseIDValue);

        //Note: Need to always start the activity that you're going to
        startActivity(intent);
    }

    void setScreenInfo() {
        Term term;
        Intent intent = getIntent();
        int termId = Integer.valueOf(intent.getStringExtra("term_id"));

        try {
            term = Helper.retrieveTermFromDatabaseByTermID((ArrayList<Term>) repository.getmAllTerms(), termId);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        termName.setText(term.getTitle());
        startDate.setText(term.getStartDate());
        endDate.setText(term.getEndDate());
    }
}