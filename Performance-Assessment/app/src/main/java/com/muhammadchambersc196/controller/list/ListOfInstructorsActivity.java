package com.muhammadchambersc196.controller.list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.muhammadchambersc196.R;

public class ListOfInstructorsActivity extends AppCompatActivity {
    RecyclerView listOfCIs;
    Button deleteCI;
    Button viewCI;
    Button addCI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_instructors);

        listOfCIs = findViewById(R.id.list_of_instructors_ci_list);
        deleteCI = findViewById(R.id.list_of_instructors_delete_ci_btn);
        viewCI = findViewById(R.id.list_of_instructors_view_ci_btn);
        addCI = findViewById(R.id.list_of_instructors_add_ci_btn);

        addCI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        deleteCI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        viewCI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}