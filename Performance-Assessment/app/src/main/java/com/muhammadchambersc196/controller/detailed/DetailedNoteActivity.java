package com.muhammadchambersc196.controller.detailed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.muhammadchambersc196.R;
import com.muhammadchambersc196.controller.create.CreateNoteActivity;
import com.muhammadchambersc196.helper.SwitchScreen;

public class DetailedNoteActivity extends AppCompatActivity {
    Button editBtn;
    Button shareBtn;
    TextView note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_note);

        //Gets references to the activities input fields
        editBtn = findViewById(R.id.detailed_note_edit_note_btn);
        note = findViewById(R.id.detailed_note_note);
        shareBtn = findViewById(R.id.detailed_note_share_note_btn);


        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNewScreen(CreateNoteActivity.class, SwitchScreen.CAME_FROM_KEY, SwitchScreen.DETAILED_NOTE_ACTIVITY, SwitchScreen.ADD_OR_UPDATE_SCREEN_KEY, "Update Note");
            }
        });
    }

    /*
        Note:
           * For edit note I need to pass in the note id
     */
    void goToNewScreen(Class goToScreen, String cameFromScreenKey, String cameFromScreenValue, String addOrUpdateScreenKey, String addOrUpdateScreenValue) {
        //Specifies the new activity/screen to go to
        Intent intent = new Intent(this, goToScreen);
        //Specifies the data to pass to the new activity/screen
        intent.putExtra(cameFromScreenKey, cameFromScreenValue);
        intent.putExtra(addOrUpdateScreenKey, addOrUpdateScreenValue);
        //Note: Need to always start the activity that you're going to
        startActivity(intent);
    }
}