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
import com.muhammadchambersc196.controller.adapter.CourseAdapter;
import com.muhammadchambersc196.controller.create.CreateOrUpdateCourseActivity;
import com.muhammadchambersc196.controller.create.CreateOrUpdateTermActivity;
import com.muhammadchambersc196.database.Repository;
import com.muhammadchambersc196.entities.Course;
import com.muhammadchambersc196.entities.Term;
import com.muhammadchambersc196.helper.CourseHelper;
import com.muhammadchambersc196.helper.SelectedListItem;
import com.muhammadchambersc196.helper.SwitchScreen;
import com.muhammadchambersc196.helper.TermHelper;

import java.util.ArrayList;
import java.util.List;

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

        repository = new Repository(getApplication());
        Intent intent = getIntent();
        termId = Integer.valueOf(intent.getStringExtra(SwitchScreen.TERM_ID_KEY));

        //Gets references to the activities input fields
        addCourseBtn = findViewById(R.id.detailed_term_add_class_btn);
        viewCourseBtn = findViewById(R.id.detailed_term_view_class_btn);
        deleteCourseBtn = findViewById(R.id.detailed_term_delete_class_btn);
        classesList = findViewById(R.id.detailed_term_classes_list);
        termName = findViewById(R.id.detailed_term_term_name);
        startDate = findViewById(R.id.detailed_term_start_date);
        endDate = findViewById(R.id.detailed_term_end_date);

        setScreenInfo(termId);
        setList(termId);

        
        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchScreen(CreateOrUpdateCourseActivity.class, SwitchScreen.CAME_FROM_KEY, SwitchScreen.DETAILED_TERM_ACTIVITY, SwitchScreen.ADD_OR_UPDATE_SCREEN_KEY, SwitchScreen.ADD_COURSE_VALUE, SwitchScreen.TERM_ID_KEY, String.valueOf(termId));
            }
        });


        viewCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SelectedListItem.getSelectedCourse() == null) {
                    return;
                }

                /*
                    Note: Obtains the termId then sets the helper class that holds holds the
                    reference to the selected course to null.

                    This is done to clean up things and not have the reference lingering
                 */
                String courseId = String.valueOf(SelectedListItem.getSelectedCourse().getCourseID());
                SelectedListItem.setSelectedCourse(null);

                switchScreen(DetailedCourseActivity.class, SwitchScreen.CAME_FROM_KEY, SwitchScreen.DETAILED_TERM_ACTIVITY, SwitchScreen.ADD_OR_UPDATE_SCREEN_KEY, SwitchScreen.UPDATE_COURSE_VALUE, SwitchScreen.COURSE_ID_KEY, courseId);
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

    void setList(int termId) {
        List<Course> allCoursesForTerm;

        try {
            allCoursesForTerm = CourseHelper.getAllCoursesForTerm((ArrayList<Course>) repository.getmAllCourses(), termId);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        final CourseAdapter courseAdapter = new CourseAdapter(this);

        classesList.setAdapter(courseAdapter);
        classesList.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setCourses(allCoursesForTerm);
    }
}