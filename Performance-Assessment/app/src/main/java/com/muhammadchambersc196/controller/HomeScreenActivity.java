package com.muhammadchambersc196.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.muhammadchambersc196.R;
import com.muhammadchambersc196.database.Repository;
import com.muhammadchambersc196.entities.CourseInstructor;

public class HomeScreenActivity extends AppCompatActivity {
    //Used to add items to the database
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        //Used to add items to the database
        repository = new Repository(getApplication());



    }
}