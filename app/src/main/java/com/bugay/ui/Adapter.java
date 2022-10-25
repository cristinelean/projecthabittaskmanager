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

public class Adapter extends RecyclerView.Adapter<Adapter.mViewHolder> {
    private calendarActivtiy activity;
    private List<Model> mList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public Adapter(calendarActivtiy activity, List<Model> mList){
        this.activity= activity;
        this.mList = mList;
    }

    public void updateData(int position){
        Model item = mList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("uId", item.getId());// nalagyan ko na ng ID
        bundle.putString("uTask", item.getTaskm());
        bundle.putString("uTime", item.getTimem());
        bundle.putString("uDate", item.getDatem());
        Intent intent = new Intent(activity, EditTask.class);//pass yung data sa EditTask.class
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public void deleteData(int position){
        Model item = mList.get(position);
        db.collection("Task").document(item.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            notifyRemove(position);
                            Toast.makeText(activity, "Data Deleted", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(activity, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void notifyRemove(int position){
        mList.remove(position);
        notifyItemRemoved(position);
        activity.showData();
    }
    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item, parent, false);
        return new mViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, int position) {
        holder.task.setText(mList.get(position).getTaskm());
        holder.time.setText(mList.get(position).getTimem());
    }

    @Override
    public int getItemCount() {

        return mList.size();
    }

    public static class mViewHolder extends RecyclerView.ViewHolder{

        TextView task, time;
        public mViewHolder(@NonNull View itemView) {
            super(itemView);

            task = itemView.findViewById(R.id.task_text);
            time = itemView.findViewById(R.id.time_text);
        }
    }
}
