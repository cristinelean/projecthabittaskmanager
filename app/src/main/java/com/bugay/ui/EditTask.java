package com.bugay.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class EditTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        //only accessible when editing a task
    }
}