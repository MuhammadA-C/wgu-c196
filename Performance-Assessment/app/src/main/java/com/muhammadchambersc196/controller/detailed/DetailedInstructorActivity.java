package com.muhammadchambersc196.controller.detailed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.muhammadchambersc196.R;

public class DetailedInstructorActivity extends AppCompatActivity {
    Button editBtn;
    TextView name;
    TextView email;
    TextView number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_instructor);

        editBtn = findViewById(R.id.detailed_view_edit_btn);
        name = findViewById(R.id.detailed_view_ci_name);
        email = findViewById(R.id.detailed_view_ci_email);
        number = findViewById(R.id.detailed_view_ci_number);


        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}