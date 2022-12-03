package com.bugay.ui;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CreateTask extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private EditText crttsk;
    private TextView count;
    private TextView ddate, mtime;
    private Button savem;
    private Button button, btt;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        count = findViewById(R.id.number);
        crttsk = findViewById(R.id.Etask);
        mtime = findViewById(R.id.pkatime);
        ddate = findViewById(R.id.date);
        savem = findViewById(R.id.btnSaveTask);
        button = (Button) findViewById(R.id.settme);
        btt = findViewById(R.id.btnBack);

        //count characters
        crttsk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String a = crttsk.getText().toString();
                int b = a.length();
                count.setText(""+(int)b);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Intent incomingIntent = getIntent();
        String date = incomingIntent.getStringExtra("date");
        ddate.setText(date);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        btt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateTask.this, calendarActivtiy.class);
                Intent incomingIntent = getIntent();
                String date = incomingIntent.getStringExtra("date");
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });

        savem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskm = crttsk.getText().toString();
                String timem = mtime.getText().toString();
                String datem = ddate.getText().toString();

                String id = UUID.randomUUID().toString();
                saveToFireStore(taskm, timem, datem, id);
            }
        });

    }

    private void saveToFireStore(String taskm, String timem, String datem, String id) {
        if (!taskm.isEmpty()) {
            Map<String, Object> task = new HashMap<>();
            task.put("TASK_TITLE", taskm);
            task.put("TASK_TIME", timem);
            task.put("TASK_DATE", datem);
            task.put("TASK_ID", id);

            db.collection("Task").document(id).set(task).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> t) {
                    if (t.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Task Created", Toast.LENGTH_SHORT).show();
                        StartActivity();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Failed to Create", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(getApplicationContext(), "Empty Fields are not allowed", Toast.LENGTH_SHORT).show();
        }
    }
        private void StartActivity () {
            Intent intent = new Intent(CreateTask.this, calendarActivtiy.class);
            Intent incomingIntent = getIntent();
            String date = incomingIntent.getStringExtra("date");
            intent.putExtra("date", date);
            startActivity(intent);
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String hourOfDays = String.valueOf(hourOfDay);
        String minutes = String.valueOf(minute);
        String hour = hourOfDays+ " : " +minutes;
        TextView timer = findViewById(R.id.pkatime);
        timer.setText(hour);
    }

}