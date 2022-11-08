package com.bugay.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TaskActivity extends AppCompatActivity {

    private Button back, addtask;
    private TextView mclndr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        mclndr = findViewById(R.id.date);
        back = findViewById(R.id.btnBack);
        addtask = findViewById(R.id.btnAddTask);

        Intent incomingIntent = getIntent();
        String date = incomingIntent.getStringExtra("date");
        mclndr.setText(date);

        addtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskActivity.this, CreateTask.class);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskActivity.this, PlannerActivity.class);
                startActivity(intent);
            }
        });
    }
}
