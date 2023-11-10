package com.muhammadchambersc196.controller.update;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.muhammadchambersc196.R;

public class UpdateNoteActivity extends AppCompatActivity {
    EditText noteDetails;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        noteDetails = findViewById(R.id.update_note_details);
        saveBtn = findViewById(R.id.update_note_save_btn);
    }
}