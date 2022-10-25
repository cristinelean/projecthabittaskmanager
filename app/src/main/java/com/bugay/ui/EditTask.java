package com.bugay.ui;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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


public class EditTask extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private EditText crttsk;
    private TextView ddate, mtime;
    private Button savem;
    private Button button, btt;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String uTask, uTime, uDate, uId;
    private Bundle bundle, bundle1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        crttsk = findViewById(R.id.task);
        mtime = findViewById(R.id.textView);
        ddate = findViewById(R.id.crntdate);
        savem = findViewById(R.id.stask);
        button = (Button) findViewById(R.id.button);
        btt = findViewById(R.id.btt);

        bundle = getIntent().getExtras();
        if (bundle != null){
            savem.setText("update");//button lets change it later nalang depende sa prototype
            uId = bundle.getString("uId");
            uTask = bundle.getString("uTask");
            uTime = bundle.getString("uTime");
            uDate = bundle.getString("uDate");
            ddate.setText(uDate);
            crttsk.setText(uTask);
            mtime.setText(uTime);
        }else{
            savem.setText("save");
        }


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
                Intent intent = new Intent(EditTask.this, calendarActivtiy.class);
                intent.putExtra("date", uDate);
                startActivity(intent);
            }
        });

        savem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskm = crttsk.getText().toString();
                String timem = mtime.getText().toString();
                String datem = ddate.getText().toString();

                Bundle bundle1 = getIntent().getExtras();

                    String id = uId;
                    updateToFireStore(id, taskm, timem, datem);
                /*else{
                    String id = UUID.randomUUID().toString();
                    saveToFireStore(taskm, timem, datem, id);
                }*/
            }
        });

    }

    private void updateToFireStore(String id, String taskm, String timem, String datem){
        db.collection("Task").document(id).update("TASK_TITLE", taskm, "TASK_TIME", timem, "TASK_DATE", datem)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(EditTask.this, "Data Update!", Toast.LENGTH_SHORT).show();
                            StartActivity ();
                        }else{
                            Toast.makeText(EditTask.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditTask.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /*private void saveToFireStore(String taskm, String timem, String datem, String id) {
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
    }*/
    private void StartActivity () {
        Intent intent = new Intent(EditTask.this, calendarActivtiy.class);
        intent.putExtra("date", uDate);
        startActivity(intent);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText( hourOfDay + " : " + minute);
    }

}
