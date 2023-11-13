package com.muhammadchambersc196.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.muhammadchambersc196.R;
import com.muhammadchambersc196.controller.list.ListOfInstructorsActivity;
import com.muhammadchambersc196.controller.list.ListOfTermsActivity;
import com.muhammadchambersc196.helper.SwitchScreen;

public class HomeScreenActivity extends AppCompatActivity {
    //Used to add items to the database
    //Repository repository;
    Button viewAllTermsBtn;
    Button viewAllInstructorsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        //Used to add items to the database
        //repository = new Repository(getApplication());

        viewAllTermsBtn = findViewById(R.id.home_view_all_terms_btn);
        viewAllInstructorsBtn = findViewById(R.id.home_view_all_instructors_btn);

        viewAllTermsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchScreen(ListOfTermsActivity.class, SwitchScreen.CAME_FROM_KEY, SwitchScreen.HOME_SCREEN_ACTIVITY);
            }
        });

        viewAllInstructorsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchScreen(ListOfInstructorsActivity.class, SwitchScreen.CAME_FROM_KEY, SwitchScreen.HOME_SCREEN_ACTIVITY);
            }
        });
    }

    void switchScreen(Class className, String keyName, String value) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, className);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(keyName, value);
        //Note: Need to always start the activity that you're going to
        startActivity(intent);
    }
}