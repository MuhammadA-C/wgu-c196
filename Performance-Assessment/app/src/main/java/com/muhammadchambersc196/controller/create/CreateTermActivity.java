package com.muhammadchambersc196.controller.create;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.muhammadchambersc196.R;
import com.muhammadchambersc196.controller.HomeScreenActivity;
import com.muhammadchambersc196.helper.Helper;

public class CreateTermActivity extends AppCompatActivity {
    Button createTermBtn;
    EditText createTermName;
    EditText createTermStartDate;
    EditText createTermEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_term);

        createTermBtn = findViewById(R.id.create_term_btn);

        //Retrieves the intent that was passed to this activity/screen
        Intent intent = getIntent();
        //Retrieves the data value/string name that was passed to this intent
        String activityCameFrom = intent.getStringExtra("came_from");

        createTermBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTermName = findViewById(R.id.create_term_name);
                createTermStartDate = findViewById(R.id.create_term_start_date);
                createTermEndDate = findViewById(R.id.create_term_end_date);

                /*
                if(Helper.isInputFieldEmpty(createTermName) || Helper.isInputFieldEmpty(createTermStartDate) || Helper.isInputFieldEmpty(createTermEndDate)) {
                    return;
                }

                 */

                try {
                    System.out.println("Create Term Screen: " + activityCameFrom);
                    goToNewScreen(Class.forName(activityCameFrom), "came_from", CreateTermActivity.class.toString());
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
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