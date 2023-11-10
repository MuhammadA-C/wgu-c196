package com.muhammadchambersc196.controller.update;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.muhammadchambersc196.R;

public class UpdateTermActivity extends AppCompatActivity {
    EditText termTitle;
    EditText startDate;
    EditText endDate;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_term);

        termTitle = findViewById(R.id.update_term_name);
        startDate = findViewById(R.id.update_term_start_date);
        endDate = findViewById(R.id.update_term_end_date);
        saveBtn = findViewById(R.id.update_term_save_btn);


    }
}