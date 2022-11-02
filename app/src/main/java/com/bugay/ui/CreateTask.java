package com.bugay.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CreateTask extends AppCompatActivity {

    private Button back;
    private TextView mclndr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        back = findViewById(R.id.btnBack);
        mclndr = findViewById(R.id.date);

        Intent incomingIntent = getIntent();
        String date = incomingIntent.getStringExtra("date");
        mclndr.setText(date);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateTask.this, TaskActivity.class);
                startActivity(intent);
            }
        });

    }
}