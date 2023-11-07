package com.muhammadchambersc196.controller.detailed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.muhammadchambersc196.R;
import com.muhammadchambersc196.controller.create.CreateTermActivity;
import com.muhammadchambersc196.controller.update.UpdateAssessmentActivity;

public class DetailedAssessmentActivity extends AppCompatActivity {
    Button detailedAssessmentEditBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_assessment);

        detailedAssessmentEditBtn = findViewById(R.id.detailed_assessment_edit_btn);

        detailedAssessmentEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNewScreen(UpdateAssessmentActivity.class, "came_from", CreateTermActivity.class.toString());
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