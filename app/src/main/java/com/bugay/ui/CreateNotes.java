package com.bugay.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CreateNotes extends AppCompatActivity {

    TextView Notes;
    EditText eTitle, eDesc;
    Button Bshared, Bapply, Bsaveis, Bsavees, Bsaveic, Bsaveec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notes);
        Notes = findViewById(R.id.myNotes);
        eTitle = findViewById(R.id.Etitle);
        eDesc = findViewById(R.id.dec);
        Bshared = findViewById(R.id.shButton);
        Bapply = findViewById(R.id.saButton);
        Bsaveis = findViewById(R.id.SaveIs);
        Bsavees = findViewById(R.id.SaveEs);
        Bsaveic = findViewById(R.id.SaveIC);
        Bsaveec = findViewById(R.id.SaveEC);

        Bapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = eTitle.getText().toString();
                String b = eDesc.getText().toString();
                String data = a+" "+b;
                Notes.setText(data);
            }
        });

        Bshared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = eTitle.getText().toString();
                String b = eDesc.getText().toString();
                String data = a+" "+b;

                SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("text", data);
                editor.apply();

                Toast.makeText(CreateNotes.this, "Data Saved", Toast.LENGTH_SHORT).show();
            }
        });
        Bsaveis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = eTitle.getText().toString();
                String b = eDesc.getText().toString();
                String data = a+" "+b;

                FileOutputStream fos = null;
                try {
                    fos = openFileOutput("Notes.txt", Context.MODE_PRIVATE);
                    fos.write(data.getBytes());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally{
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Toast.makeText(CreateNotes.this, "Data Saved to Internal Storage", Toast.LENGTH_SHORT).show();
            }
        });
        loadData();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        String tNotes = sharedPreferences.getString("text", "");
        Notes.setText(tNotes);
    }
}