package com.bugay.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class calendarActivtiy extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView mclndr;
    private Button mbtn, btc;
    private RecyclerView RV;
    private Adapter adptr;
    private List<Model> list = new ArrayList<>();
    private ImageView flag;
    private TextView notask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_activtiy);
        flag = findViewById(R.id.flag);
        notask = findViewById(R.id.noTask);
        mclndr = (TextView) findViewById(R.id.date);
        mbtn = (Button) findViewById(R.id.btnAddTask);
        btc = (Button) findViewById(R.id.btnBack);
        RV = findViewById(R.id.recview);
        RV.setHasFixedSize(true);
        RV.setLayoutManager(new LinearLayoutManager(this));

        Intent incomingIntent = getIntent();
        String date = incomingIntent.getStringExtra("date");
        mclndr.setText(date);

        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(calendarActivtiy.this, CreateTask.class);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });

        btc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(calendarActivtiy.this, PlannerActivtiy.class);
                startActivity(i);
            }
        });

        adptr = new Adapter(this, list);
        RV.setAdapter(adptr);

        ItemTouchHelper touchHelper = new ItemTouchHelper(new TouchHelper(adptr));
        touchHelper.attachToRecyclerView(RV);
        showData();
    }
    public void showData(){
        Intent incomingIntent = getIntent();
        String date = incomingIntent.getStringExtra("date");
        db.collection("Task").whereEqualTo("TASK_DATE", date).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                list.clear();
                for (DocumentSnapshot snapshot : task.getResult()){
                    Model model = new Model(snapshot.getString("TASK_ID"), snapshot.getString("TASK_TITLE"),
                            snapshot.getString("TASK_TIME"), snapshot.getString("TASK_DATE"));
                    list.add(model);
                }
                adptr.notifyDataSetChanged();
                //To check if the list in empty state
                if (!(list.size() == 0)){
                    flag.setVisibility(View.GONE);
                    notask.setVisibility(View.GONE);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(calendarActivtiy.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

}


