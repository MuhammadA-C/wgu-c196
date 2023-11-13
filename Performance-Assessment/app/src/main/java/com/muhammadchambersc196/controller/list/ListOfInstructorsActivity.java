package com.muhammadchambersc196.controller.list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.muhammadchambersc196.R;
import com.muhammadchambersc196.controller.adapter.InstructorAdapter;
import com.muhammadchambersc196.controller.adapter.TermAdapter;
import com.muhammadchambersc196.controller.create.CreateOrUpdateInstructorActivity;
import com.muhammadchambersc196.controller.create.CreateOrUpdateTermActivity;
import com.muhammadchambersc196.database.Repository;
import com.muhammadchambersc196.entities.CourseInstructor;
import com.muhammadchambersc196.entities.Term;
import com.muhammadchambersc196.helper.SwitchScreen;

import java.util.List;

public class ListOfInstructorsActivity extends AppCompatActivity {
    Repository repository;
    RecyclerView instructorsList;
    Button deleteCI;
    Button viewCI;
    Button addCI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_instructors);

        repository = new Repository(getApplication());

        instructorsList = findViewById(R.id.list_of_instructors_ci_list);
        deleteCI = findViewById(R.id.list_of_instructors_delete_ci_btn);
        viewCI = findViewById(R.id.list_of_instructors_view_ci_btn);
        addCI = findViewById(R.id.list_of_instructors_add_ci_btn);

        setList();

        addCI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBtnSwitchScreen(CreateOrUpdateInstructorActivity.class, SwitchScreen.CAME_FROM_KEY, SwitchScreen.LIST_OF_INSTRUCTORS_ACTIVITY, SwitchScreen.ADD_OR_UPDATE_SCREEN_KEY, SwitchScreen.ADD_INSTRUCTOR_VALUE);
            }
        });

        deleteCI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Need to stop instructor from being deleted if they are assigned to any courses
            }
        });

        viewCI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    void addBtnSwitchScreen(Class className, String keyName, String value, String addOrUpdateScreenKey, String addOrUpdateScreenValue) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, className);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(keyName, value);
        intent.putExtra(addOrUpdateScreenKey, addOrUpdateScreenValue);
        //Need to always start the activity that you're going to
        startActivity(intent);
    }

    void setList() {
        List<CourseInstructor> allInstructors;

        try {
            allInstructors = repository.getmAllCourseInstructors();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        final InstructorAdapter instructorAdapter = new InstructorAdapter(this);

        instructorsList.setAdapter(instructorAdapter);
        instructorsList.setLayoutManager(new LinearLayoutManager(this));
        instructorAdapter.setInstructors(allInstructors);
    }
}