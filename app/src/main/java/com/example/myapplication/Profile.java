package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity implements View.OnClickListener{

    TextView textname, textname1, textemail1, textphone;
    FirebaseDatabase mdb;
    FirebaseAuth fAuth;
    DatabaseReference dref;
    String email, uid;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textname = findViewById(R.id.name);
        textname1 = findViewById(R.id.name1);
        textemail1 = findViewById(R.id.email1);
        textphone = findViewById(R.id.phone_number1);
        img = findViewById(R.id.back);
        img.setOnClickListener(this);
        mdb = FirebaseDatabase.getInstance();
        //dref = mdb.getReference("users");
        dref = FirebaseDatabase.getInstance().getReference("users");
        fAuth = FirebaseAuth.getInstance();
        FirebaseUser user1 = fAuth.getCurrentUser();
        uid = user1.getUid();

        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                textname.setText(snapshot.child(uid).child("username").getValue(String.class));
                textname1.setText(snapshot.child(uid).child("username").getValue(String.class));
                textemail1.setText(snapshot.child(uid).child("email").getValue(String.class));
                textphone.setText(snapshot.child(uid).child("phone").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}