package com.muhammadchambersc196.controller.detailed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.muhammadchambersc196.R;
import com.muhammadchambersc196.controller.HomeScreenActivity;
import com.muhammadchambersc196.controller.update.UpdateNoteActivity;

public class DetailedCourseActivity extends AppCompatActivity {
    Button detailedClassViewAssignmentBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_course);

        detailedClassViewAssignmentBtn = findViewById(R.id.detailed_class_view_assignment_btn);

        detailedClassViewAssignmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNewScreen(DetailedAssessmentActivity.class, "came_from", HomeScreenActivity.class.toString());
            }
        });
    }

    void goToNewScreen(Class className, String keyName, String value) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(getApplicationContext(), className);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(keyName, value);
        //Note: Need to always start the activity that you're going to
        startActivity(intent);
    }
}