package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Activity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private TextView mtvclick;
    private Button mbtlogin;
    EditText editemail,editpassword;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mbtlogin = findViewById(R.id.loging);
        mbtlogin.setOnClickListener(this);
        mtvclick = findViewById(R.id.reg);
        mtvclick.setOnClickListener(this);
        editpassword = findViewById(R.id.password);
        editemail = findViewById(R.id.email);
        fAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        
        switch (view.getId()){
            case R.id.reg:
                startActivity(new Intent(getApplicationContext(), Register.class));
                finish();
                break;
            case R.id.loging:
                login();
                break;
        }

    }

    private void login() {

        String email = editemail.getText().toString().trim();
        String password = editpassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            editemail.setError("Email is required");
            editemail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editemail.setError("Please provide a valid Email");
            editemail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)){
            editpassword.setError("Password is required");
            editpassword.requestFocus();
            return;

        }
        if(password.length() < 8){
            editpassword.setError("A minimum of 8 characters required");
            editpassword.requestFocus();
            return;
        }

        //Login user with email and password
        //progress.setVisibility(View.VISIBLE);
        fAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //Go to Dashboard
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            Toast.makeText(Login.this, "Logged in Successfully!!", Toast.LENGTH_SHORT).show();
                            //progress.setVisibility(View.GONE);
                            finish();
                        }else {
                            Toast.makeText(Login.this, "Failed to Login, Verify your credentials", Toast.LENGTH_SHORT).show();
                            //progress.setVisibility(View.GONE);
                        }
                    }
                });

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}