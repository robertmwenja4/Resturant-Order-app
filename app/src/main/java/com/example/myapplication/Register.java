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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class Register extends AppCompatActivity implements View.OnClickListener{

    private TextView tvClickme;
    private Button mbtnReg;
    EditText editusername, editpassword,editphone,editemail;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tvClickme = findViewById(R.id.log);
        tvClickme.setOnClickListener(this);
        mbtnReg = findViewById(R.id.registration);
        mbtnReg.setOnClickListener(this);
        editusername = findViewById(R.id.username);
        editpassword = findViewById(R.id.password);
        editemail = findViewById(R.id.email);
        editphone = findViewById(R.id.phoneNumber);
        fAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.log:
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
                break;
            case R.id.registration:
                register();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
                break;
        }

    }

    private void register() {
        String name = editusername.getText().toString().trim();
        String email = editemail.getText().toString().trim();
        String password = editpassword.getText().toString().trim();
        long phone = Integer.parseInt(editphone.getText().toString().trim());
        FirebaseMessaging.getInstance().getToken();

        //Verification for required fields and eligible
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
        if (TextUtils.isEmpty(name)){
            editusername.setError("Name is required");
            editusername.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(editphone.getText().toString())){
            editphone.setError("Phone number is required");
            editphone.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)){
            editpassword.setError("Password is required");
            editpassword.requestFocus();
            return;

        }

        if(password.length() < 8){
            editpassword.setError("A minimum of * characters required");
            editpassword.requestFocus();
            return;
        }


        //progress bar
//        progressBar.setVisibility(View.VISIBLE);
        //Registration using email and Password
        fAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseMessaging.getInstance().getToken()
                                    .addOnCompleteListener(new OnCompleteListener<String>() {
                                        @Override
                                        public void onComplete(@NonNull Task<String> task) {

                                            if (task.isSuccessful()){

                                                RegisterUser user = new RegisterUser(name, email, password,task.getResult(),phone);
                                                FirebaseDatabase.getInstance().getReference("users")
                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()){

                                                            startActivity(new Intent(Register.this, Login.class));
                                                            Toast.makeText(Register.this, "User Created", Toast.LENGTH_SHORT).show();
                                                            //progressBar.setVisibility(View.GONE);
                                                            finish();
                                                        }else {
                                                            Toast.makeText(Register.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    });


                        }else {
                            Toast.makeText(Register.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}