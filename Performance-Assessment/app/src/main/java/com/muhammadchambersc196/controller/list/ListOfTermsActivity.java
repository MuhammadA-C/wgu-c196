package com.muhammadchambersc196.controller.list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.muhammadchambersc196.R;
import com.muhammadchambersc196.controller.create.CreateTermActivity;
import com.muhammadchambersc196.controller.detailed.DetailedTermActivity;
import com.muhammadchambersc196.helper.SwitchScreen;

public class ListOfTermsActivity extends AppCompatActivity {
    Button listOfTermsAddTermBtn;
    Button listOfTermsViewTermBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_terms);

        listOfTermsAddTermBtn = findViewById(R.id.list_of_terms_add_term_btn);
        listOfTermsViewTermBtn = findViewById(R.id.list_of_terms_view_term_btn);

        listOfTermsAddTermBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNewScreen(CreateTermActivity.class, SwitchScreen.CAME_FROM, SwitchScreen.LIST_OF_TERMS_ACTIVITY);
            }
        });

        listOfTermsViewTermBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNewScreen(DetailedTermActivity.class, SwitchScreen.CAME_FROM, SwitchScreen.LIST_OF_TERMS_ACTIVITY);
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