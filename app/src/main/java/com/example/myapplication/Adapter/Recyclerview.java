package com.example.myapplication.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.myapplication.Domain.SaveOrder;
import com.example.myapplication.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Context;

import java.util.ArrayList;
import java.util.List;

public class Recyclerview extends FirebaseRecyclerAdapter<SaveOrder, Recyclerview.RecycleViewHolder> {


    public Recyclerview(
            @NonNull FirebaseRecyclerOptions<SaveOrder> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RecycleViewHolder holder, int position, @NonNull SaveOrder model) {

        holder.morder.setText(model.getOrderID());
        holder.date.setText(model.getTime());
        holder.mtitle.setText(model.getTitles());
//        holder.end.setText(String.valueOf(model.getTimee()));
//        holder.status.setText(model.getStatus());
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview, parent, false);
        return new RecycleViewHolder(view);
    }



    public static class RecycleViewHolder extends RecyclerView.ViewHolder {
        TextView morder,date,mtitle,end,status;
        CheckBox checkBox;
        Button view,cancel;
        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);

            morder = itemView.findViewById(R.id.orderId);
            date = itemView.findViewById(R.id.date);
            mtitle = itemView.findViewById(R.id.title);
//            end = itemView.findViewById(R.id.endtime);
//            status = itemView.findViewById(R.id.status);
//            checkBox = itemView.findViewById(R.id.checkbox);





            //imageView = itemView.findViewById(R.id.imageView);
        }
    }
}