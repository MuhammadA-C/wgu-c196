package com.muhammadchambersc196.controller.detailed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.muhammadchambersc196.R;
import com.muhammadchambersc196.controller.HomeScreenActivity;
import com.muhammadchambersc196.controller.create.CreateCourseActivity;
import com.muhammadchambersc196.helper.SwitchScreen;

public class DetailedTermActivity extends AppCompatActivity {
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

        addBtn = findViewById(R.id.detailed_term_add_class_btn);
        viewBtn = findViewById(R.id.detailed_term_view_class_btn);
        deleteBtn = findViewById(R.id.detailed_term_delete_class_btn);
        classesList = findViewById(R.id.detailed_term_classes_list);
        termName = findViewById(R.id.detailed_term_term_name);
        startDate = findViewById(R.id.detailed_term_start_date);
        endDate = findViewById(R.id.detailed_term_end_date);

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

    void goToNewScreen(Class className, String keyName, String value) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, className);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(keyName, value);
        //Note: Need to always start the activity that you're going to
        startActivity(intent);
    }
}