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

public class NotesActivity extends AppCompatActivity {

    private ImageView flag;
    private TextView notask;
    private Button btnbk, btnCreateNotes;
    private RecyclerView recyclerView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private AdapterNotes adapterNotes;
    private List<ModelNote> noteList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        flag = findViewById(R.id.flag);
        notask = findViewById(R.id.noNotes);
        btnCreateNotes = findViewById(R.id.btncnotes);
        btnbk = findViewById(R.id.btt);
        recyclerView = findViewById(R.id.recviewNote);



        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnCreateNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(NotesActivity.this, CreateNotes.class);
                startActivity(a);
            }
        });

        btnbk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotesActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        noteList = new ArrayList<>();
        adapterNotes = new AdapterNotes(this, noteList);
        recyclerView.setAdapter(adapterNotes);
        ItemTouchHelper touchHelper = new ItemTouchHelper(new TouchHelperNotes(adapterNotes));
        touchHelper.attachToRecyclerView(recyclerView);
        showData();
    }
    public void showData(){
        db.collection("Notes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                noteList.clear();
                for(DocumentSnapshot snapshot : task.getResult()){
                    ModelNote modelNote = new ModelNote(snapshot.getString("id"), snapshot.getString("title"), snapshot.getString("desc") );
                    noteList.add(modelNote);
                }
                adapterNotes.notifyDataSetChanged();
                //To check if the list in empty state
                if (!(noteList.size() == 0)){
                    flag.setVisibility(View.GONE);
                    notask.setVisibility(View.GONE);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NotesActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
