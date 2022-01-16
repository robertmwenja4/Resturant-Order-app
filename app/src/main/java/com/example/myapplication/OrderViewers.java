package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.Adapter.MyAdapter;
import com.example.myapplication.Adapter.Recyclerview;
import com.example.myapplication.Domain.SaveOrder;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class OrderViewers extends AppCompatActivity {

    ArrayList<SaveOrder> list;
    RecyclerView recyclerview;
    DatabaseReference dref;
    Recyclerview recycler;//adaptar
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_viewers);

        recyclerview =  findViewById(R.id.recyclerview);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        String uid = fAuth.getInstance().getCurrentUser().getUid();
        dref = FirebaseDatabase.getInstance().getReference("OrderDetails").child(uid);

        FirebaseRecyclerOptions<SaveOrder> options = new FirebaseRecyclerOptions.Builder<SaveOrder>()
                .setQuery(dref, SaveOrder.class)
                .build();
        recycler = new Recyclerview(options);
        recyclerview.setAdapter(recycler);
    }
    @Override protected void onStart()
    {
        super.onStart();
        recycler.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stoping of the activity
    @Override protected void onStop()
    {
        super.onStop();
        recycler.stopListening();
    }
}