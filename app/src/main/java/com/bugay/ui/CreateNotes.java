package com.bugay.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class CreateNotes extends AppCompatActivity {

    private EditText txtTitle, txtDesc;
    private Button btnsave, btnback;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String uid, utitle, udesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notes);

        txtTitle=findViewById(R.id.txtTitle);
        txtDesc=findViewById(R.id.txtDesc);
        btnsave=findViewById(R.id.btnsnotes);
        btnback=findViewById(R.id.btt);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            btnsave.setText("Update");
            utitle = bundle.getString("utitle");
            uid = bundle.getString("uid");
            udesc = bundle.getString("udesc");

            txtTitle.setText(utitle);
            txtDesc.setText(udesc);
        }else{
            btnsave.setText("Save");
        }
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = txtTitle.getText().toString();
                String desc = txtDesc.getText().toString();
                Bundle bundle1 = getIntent().getExtras();
                if(bundle1 !=null){
                    String id = uid;
                    updateToFirestore(title, desc, id);
                }else{
                    String id = UUID.randomUUID().toString();
                    saveToFirestore(title, desc, id);
                }



            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateNotes.this, NotesActivity.class);
                startActivity(intent);
            }
        });
    }
    private void updateToFirestore(String title, String desc, String id){
        db.collection("Notes").document(id).update("title", title, "desc", desc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(CreateNotes.this, "Data Updated!", Toast.LENGTH_SHORT).show();
                            StartActivity();
                        }else{
                            Toast.makeText(CreateNotes.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateNotes.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void saveToFirestore(String title, String desc, String id){
        //check if there are inputted values on title and desc
        if(!title.isEmpty() && !desc.isEmpty()){
            //create db
            HashMap<String, Object> map = new HashMap<>();
            map.put("id",id);
            map.put("title", title);
            map.put("desc", desc);


            db.collection("Notes").document(id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    //if save
                    if(task.isSuccessful()){
                        Toast.makeText(CreateNotes.this, "Note Saved!!", Toast.LENGTH_SHORT).show();
                        StartActivity();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //if failed save
                    Toast.makeText(CreateNotes.this, "Failed to Save!!", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(this, "Empty Fields is not allowed", Toast.LENGTH_SHORT).show();
        }
    }
    private void StartActivity () {
        Intent intent = new Intent(CreateNotes.this, NotesActivity.class);
        startActivity(intent);
    }
}