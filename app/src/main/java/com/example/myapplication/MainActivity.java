package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static Button mbtnlogin, mbtnregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mbtnlogin = findViewById(R.id.login);
        mbtnlogin.setOnClickListener(this);
        mbtnregister = findViewById(R.id.register);
        mbtnregister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.login:
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
                break;
            case R.id.register:
                startActivity(new Intent(getApplicationContext(), Register.class));
                finish();
                break;
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}