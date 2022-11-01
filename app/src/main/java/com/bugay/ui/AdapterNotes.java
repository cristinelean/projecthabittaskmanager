package com.bugay.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AdapterNotes extends RecyclerView.Adapter<AdapterNotes.myViewHolder> {

    private NotesActivity activity;
    private List<ModelNote> listnote;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public AdapterNotes(NotesActivity activity, List<ModelNote> listnote){
        this.activity=activity;
        this.listnote=listnote;
    }

    public void updateData(int position){
        ModelNote item = listnote.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("uid", item.getId());
        bundle.putString("utitle", item.getTitle());
        bundle.putString("udesc", item.getDesc());
        Intent intent = new Intent(activity, CreateNotes.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public void deleteData(int position){
        ModelNote item = listnote.get(position);
        db.collection("Notes").document(item.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(activity, "Data Deleted!", Toast.LENGTH_SHORT).show();
                    notifyRemoved(position);
                }else{
                    Toast.makeText(activity, "Error: "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void notifyRemoved(int position){
        listnote.remove(position);
        notifyItemRemoved(position);
        activity.showData();
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.itemnotes, parent, false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.title.setText(listnote.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return listnote.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.note_text);
        }
    }
}
