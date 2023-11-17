package com.muhammadchambersc196.controller.list;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.muhammadchambersc196.R;
import com.muhammadchambersc196.controller.adapter.InstructorAdapter;
import com.muhammadchambersc196.controller.create.CreateOrUpdateInstructorActivity;
import com.muhammadchambersc196.controller.detailed.DetailedInstructorActivity;
import com.muhammadchambersc196.database.Repository;
import com.muhammadchambersc196.entities.Course;
import com.muhammadchambersc196.entities.CourseInstructor;
import com.muhammadchambersc196.helper.DialogMessages;
import com.muhammadchambersc196.helper.InstructorHelper;
import com.muhammadchambersc196.helper.SelectedListItem;
import com.muhammadchambersc196.helper.SwitchScreen;

import java.util.ArrayList;
import java.util.List;

public class ListOfInstructorsActivity extends AppCompatActivity {
    Repository repository;
    RecyclerView instructorsList;
    Button deleteCI;
    Button viewCI;
    Button addCI;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_instructors);

        repository = new Repository(getApplication());
        builder = new AlertDialog.Builder(this);

        instructorsList = findViewById(R.id.list_of_instructors_ci_list);
        deleteCI = findViewById(R.id.list_of_instructors_delete_ci_btn);
        viewCI = findViewById(R.id.list_of_instructors_view_ci_btn);
        addCI = findViewById(R.id.list_of_instructors_add_ci_btn);

        setCourseRecyclerView();


        addCI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBtnSwitchScreen(CreateOrUpdateInstructorActivity.class, SwitchScreen.CAME_FROM_KEY,
                        SwitchScreen.LIST_OF_INSTRUCTORS_ACTIVITY, SwitchScreen.ADD_OR_UPDATE_SCREEN_KEY, SwitchScreen.ADD_INSTRUCTOR_VALUE);
            }
        });


        deleteCI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SelectedListItem.getSelectedInstructor() == null) {
                    Toast.makeText(ListOfInstructorsActivity.this, DialogMessages.NEED_TO_SELECT_AN_INSTRUCTOR + " delete", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    //Checks if the course instructor to delete has courses. Only course instructors with no courses can be deleted
                    if (InstructorHelper.doesInstructorHaveCourses(SelectedListItem.getSelectedInstructor().getInstructorID(),
                            (ArrayList<Course>) repository.getmAllCourses())) {
                        Toast.makeText(ListOfInstructorsActivity.this, "You cannot delete " + SelectedListItem.getSelectedInstructor() + " because they are assigned to courses.", Toast.LENGTH_LONG).show();
                        return;
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                //Displays a confirmation box for the user to confirm if they want to cancel
                builder.setTitle(DialogMessages.CONFIRMATION)
                        .setMessage(DialogMessages.DELETE_CONFIRMATION_MESSAGE + SelectedListItem.getSelectedInstructor().getName() + "?")
                        .setCancelable(true)
                        .setPositiveButton(DialogMessages.YES, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try {
                                    Toast.makeText(ListOfInstructorsActivity.this, "Deleted " + SelectedListItem.getSelectedInstructor(), Toast.LENGTH_LONG).show();
                                    repository.delete(SelectedListItem.getSelectedInstructor());
                                    SelectedListItem.setSelectedInstructor(null);
                                    setCourseRecyclerView();
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


        viewCI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SelectedListItem.getSelectedInstructor() == null) {
                    Toast.makeText(ListOfInstructorsActivity.this, DialogMessages.NEED_TO_SELECT_AN_INSTRUCTOR + " view", Toast.LENGTH_SHORT).show();
                    return;
                }

                /*
                    Note: Obtains the instructorId then sets the helper class that holds holds the
                    reference to the selected instructor to null.

                    This is done to clean up things and not have the reference lingering
                 */
                int instructorId = SelectedListItem.getSelectedInstructor().getInstructorID();
                SelectedListItem.setSelectedInstructor(null);

                switchScreen(DetailedInstructorActivity.class, SwitchScreen.CAME_FROM_KEY, SwitchScreen.LIST_OF_INSTRUCTORS_ACTIVITY,
                        SwitchScreen.INSTRUCTOR_ID_KEY, String.valueOf(instructorId));
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

    void switchScreen(Class className, String classNameKey , String classNameValue, String idKey, String idValue) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, className);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(classNameKey, classNameValue);
        intent.putExtra(idKey, idValue);
        //Need to always start the activity that you're going to
        startActivity(intent);
    }

    void setCourseRecyclerView() {
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