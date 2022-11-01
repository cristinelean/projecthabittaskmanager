package com.bugay.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;

    private Button bNotes,bPlanner,bTimer,bTips,bAboutUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbar);
        bNotes=findViewById(R.id.btnNotes);
        bPlanner=findViewById(R.id.btnPlanner);
        bTimer=findViewById(R.id.btnTimer);
        bTips=findViewById(R.id.btnTips);
        bAboutUs=findViewById(R.id.btnAboutUs);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        bNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NotesActivity.class);
                startActivity(intent);
            }
        });
        bPlanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PlannerActivtiy.class);
                startActivity(intent);
            }
        });
    }
}