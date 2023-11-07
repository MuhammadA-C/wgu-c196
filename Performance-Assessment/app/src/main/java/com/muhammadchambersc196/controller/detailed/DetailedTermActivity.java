package com.muhammadchambersc196.controller.detailed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.muhammadchambersc196.R;
import com.muhammadchambersc196.controller.HomeScreenActivity;
import com.muhammadchambersc196.controller.create.CreateCourseActivity;
import com.muhammadchambersc196.helper.SwitchScreen;

public class DetailedTermActivity extends AppCompatActivity {
    Button detailedTermAddClassBtn;
    Button detailedTermViewClassBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_term);

        detailedTermAddClassBtn = findViewById(R.id.detailed_term_add_class_btn);
        detailedTermViewClassBtn = findViewById(R.id.detailed_term_view_class_btn);

        detailedTermAddClassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNewScreen(CreateCourseActivity.class, SwitchScreen.CAME_FROM, SwitchScreen.DETAILED_TERM_ACTIVITY);
            }
        });

        detailedTermViewClassBtn.setOnClickListener(new View.OnClickListener() {
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