package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Domain.SaveOrder;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.RecycleViewHolder> {
    Context context;
    List<SaveOrder> list = new ArrayList<>();

    public MyAdapter(Context context, ArrayList<SaveOrder> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog, parent, false);
        return new RecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder holder, int position) {

        SaveOrder book = list.get(position);
        //binding the data with the viewholder views
        holder.order.setText(book.getOrderID());
        holder.title.setText(book.getTitles());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public static class RecycleViewHolder extends RecyclerView.ViewHolder {
        TextView order, title;

        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);

            order = itemView.findViewById(R.id.orderId);
            title = itemView.findViewById(R.id.title);


        }

    }
}
