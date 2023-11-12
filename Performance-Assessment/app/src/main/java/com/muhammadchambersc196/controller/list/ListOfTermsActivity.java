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
import com.muhammadchambersc196.controller.create.CreateTermActivity;
import com.muhammadchambersc196.controller.detailed.DetailedTermActivity;
import com.muhammadchambersc196.database.Repository;
import com.muhammadchambersc196.entities.Term;
import com.muhammadchambersc196.helper.SelectedListItem;
import com.muhammadchambersc196.helper.SwitchScreen;

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

        List<Term> allTerms = null;

        try {
            allTerms = repository.getmAllTerms();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        final TermAdapter termAdapter = new TermAdapter(this);

        termsList.setAdapter(termAdapter);
        termsList.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(allTerms);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNewScreen(CreateTermActivity.class, SwitchScreen.CAME_FROM, SwitchScreen.LIST_OF_TERMS_ACTIVITY);
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

                goToNewScreen(DetailedTermActivity.class, SwitchScreen.CAME_FROM, SwitchScreen.LIST_OF_TERMS_ACTIVITY, "term_id", String.valueOf(termId));
            }
        });
    }

    /*
        Note:
            * For view term I need to pass in the term id
     */
    void goToNewScreen(Class className, String keyName, String value) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, className);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(keyName, value);
        //Need to always start the activity that you're going to
        startActivity(intent);
    }

    void goToNewScreen(Class className, String classNameKey , String classNameValue, String termIdKey, String termIdValue) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, className);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(classNameKey, classNameValue);
        intent.putExtra(termIdKey, termIdValue);
        //Need to always start the activity that you're going to
        startActivity(intent);
    }
}