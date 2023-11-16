package com.muhammadchambersc196.controller.list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.muhammadchambersc196.R;
import com.muhammadchambersc196.controller.adapter.TermAdapter;
import com.muhammadchambersc196.controller.create.CreateOrUpdateTermActivity;
import com.muhammadchambersc196.controller.detailed.DetailedTermActivity;
import com.muhammadchambersc196.database.Repository;
import com.muhammadchambersc196.entities.Course;
import com.muhammadchambersc196.entities.Term;
import com.muhammadchambersc196.helper.SelectedListItem;
import com.muhammadchambersc196.helper.SwitchScreen;

import java.util.ArrayList;
import java.util.List;

public class ListOfTermsActivity extends AppCompatActivity {
    Repository repository;
    Button addBtn;
    Button viewBtn;
    Button deleteBtn;
    RecyclerView termsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_terms);

        repository = new Repository(getApplication());
        addBtn = findViewById(R.id.list_of_terms_add_term_btn);
        viewBtn = findViewById(R.id.list_of_terms_view_term_btn);
        deleteBtn = findViewById(R.id.list_of_terms_delete_term_btn);
        termsList = findViewById(R.id.list_of_terms_list);

        setTermRecyclerView();


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBtnSwitchScreen(CreateOrUpdateTermActivity.class, SwitchScreen.CAME_FROM_KEY,
                        SwitchScreen.LIST_OF_TERMS_ACTIVITY, SwitchScreen.ADD_OR_UPDATE_SCREEN_KEY, SwitchScreen.ADD_TERM_VALUE);
            }
        });


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SelectedListItem.getSelectedTerm() == null) {
                    return;
                }

                try {
                    //Checks to see if the term has any courses. Only terms with no courses can be deleted
                    if (doesTermHaveCourses((ArrayList<Course>) repository.getmAllCourses(), SelectedListItem.getSelectedTerm().getTermID())) {
                        return;
                    }

                    repository.delete(SelectedListItem.getSelectedTerm());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                SelectedListItem.setSelectedTerm(null);
                setTermRecyclerView();
            }
        });


        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SelectedListItem.getSelectedTerm() == null) {
                    return;
                }

                /*
                    Note: Obtains the termId then sets the helper class that holds holds the
                    reference to the selected term to null.

                    This is done to clean up things and not have the reference lingering
                 */
                int termId = SelectedListItem.getSelectedTerm().getTermID();
                SelectedListItem.setSelectedTerm(null);

                switchScreen(DetailedTermActivity.class, SwitchScreen.CAME_FROM_KEY, SwitchScreen.LIST_OF_TERMS_ACTIVITY,
                        SwitchScreen.TERM_ID_KEY, String.valueOf(termId));
            }
        });
    }

    boolean doesTermHaveCourses(ArrayList<Course> dbCourseList, int termId) {
        if (dbCourseList.size() == 0) {
            return false;
        }

        /*
            Loops through the list of courses to find if the term has any courses.
             * If the term has any courses, then true will be returned
             * If the term does NOT have any courses, then false will be returned
         */
        for (Course dbCourse : dbCourseList) {
            if (dbCourse.getTermID() == termId) {
                return true;
            }
        }
        return false;
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

    void setTermRecyclerView() {
        List<Term> allTerms;

        try {
            allTerms = repository.getmAllTerms();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        final TermAdapter termAdapter = new TermAdapter(this);

        termsList.setAdapter(termAdapter);
        termsList.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(allTerms);
    }
}