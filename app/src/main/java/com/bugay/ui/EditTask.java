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


public class EditTask extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private EditText crttsk;
    private TextView count;
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

        bundle = getIntent().getExtras();
        if (bundle != null){
            savem.setText("Save");//button lets change it later nalang depende sa prototype
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
        int hour = hourOfDay;
        int min = minute;
        String day = "";
        String Thour = "";
        String minutes = "";
        // AM or PM
        if (0<=hour && hour<12){
            day = "AM";
        }else{
            day = "PM";
        }

        if (hour == 0){
            Thour = "12";
        }else if (hour == 1){
            Thour = "01";
        }else if (hour == 2){
            Thour = "02";
        }else if (hour == 3){
            Thour = "03";
        }else if (hour == 4){
            Thour = "04";
        }else if (hour == 5){
            Thour = "05";
        }else if (hour == 6){
            Thour = "06";
        }else if (hour == 7){
            Thour = "07";
        }else if (hour == 8){
            Thour = "08";
        }else if (hour == 9){
            Thour = "09";
        }else if (hour == 13){
            Thour = "01";
        }else if (hour == 14){
            Thour = "02";
        }else if (hour == 15){
            Thour = "03";
        }else if (hour == 16){
            Thour = "04";
        }else if (hour == 17){
            Thour = "05";
        }else if (hour == 18){
            Thour = "06";
        }else if (hour == 19){
            Thour = "07";
        }else if (hour == 20){
            Thour = "08";
        }else if (hour == 21){
            Thour = "09";
        }else if (hour == 22) {
            Thour = "10";
        }else if (hour == 23) {
            Thour = "11";
        }else{
            Thour = String.valueOf(hour);
        }

        if (min == 0){
            minutes = "00";
        }else if (min == 1){
            minutes = "01";
        }else if (min == 2){
            minutes = "02";
        }else if (min == 3){
            minutes = "03";
        }else if (min == 4){
            minutes = "04";
        }else if (min == 5){
            minutes = "05";
        }else if (min == 6){
            minutes = "06";
        }else if (min == 7){
            minutes = "07";
        }else if (min == 8){
            minutes = "08";
        }else if (min == 9){
            minutes = "09";
        }else{
            minutes = String.valueOf(min);
        }

        String time = Thour+ " : " +minutes+ " " + day;
        TextView timer = findViewById(R.id.pkatime);
        timer.setText(time);
    }

}
