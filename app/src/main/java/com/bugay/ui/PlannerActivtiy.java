package com.bugay.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.annotation.NonNull;

public class PlannerActivtiy extends Activity {
    private static final String TAG = "CalendarActivity";
    private CalendarView clndr;
    private Button btnB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);

        btnB = findViewById(R.id.btnBack);
        clndr = (CalendarView) findViewById(R.id.calendarView);
        clndr.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = (i1 +1) +"-" + i2 +"-" + i;
                Log.d(TAG, "onSelectedDayChange: date: " +date);

                Intent intent = new Intent(PlannerActivtiy.this, calendarActivtiy.class);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });

        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlannerActivtiy.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
