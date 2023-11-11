package com.muhammadchambersc196.controller.list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.muhammadchambersc196.R;
import com.muhammadchambersc196.controller.create.CreateTermActivity;
import com.muhammadchambersc196.controller.detailed.DetailedTermActivity;
import com.muhammadchambersc196.helper.SwitchScreen;

public class ListOfTermsActivity extends AppCompatActivity {
    Button addBtn;
    Button viewBtn;
    Button deleteBtn;
    RecyclerView termsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_terms);

        addBtn = findViewById(R.id.list_of_terms_add_term_btn);
        viewBtn = findViewById(R.id.list_of_terms_view_term_btn);
        deleteBtn = findViewById(R.id.list_of_terms_delete_term_btn);
        termsList = findViewById(R.id.list_of_terms_list);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNewScreen(CreateTermActivity.class, SwitchScreen.CAME_FROM, SwitchScreen.LIST_OF_TERMS_ACTIVITY);
            }
        });

        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNewScreen(DetailedTermActivity.class, SwitchScreen.CAME_FROM, SwitchScreen.LIST_OF_TERMS_ACTIVITY);
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
}