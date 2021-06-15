package com.clbyte.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class login extends AppCompatActivity {
    Button loginBTN;
    TextView forget_password, createAccount;
    TextView userN,pass;

    FirebaseAuth auth;
    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        forget_password = findViewById(R.id.forget_password);
        createAccount = findViewById(R.id.createaccount);
        userN = findViewById(R.id.usernamefield);
        pass = findViewById(R.id.passwordfield);
        loginBTN = findViewById(R.id.loginbtn2);

        auth = FirebaseAuth.getInstance();

        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this,ResetPasswordActivity.class));
            }
        });
        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = userN.getText().toString();
                String password = pass.getText().toString();

                if(TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(password)){
                    Toast.makeText(login.this,"All fields are required",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    auth.signInWithEmailAndPassword(userEmail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Intent it = new Intent(login.this, MainScreen.class);
                                it.addFlags(it.FLAG_ACTIVITY_CLEAR_TASK | it.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(it);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(login.this,"Authentication Failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(login.this, RegistrationForm.class);
                startActivity(it);
                finish();
            }
        });

    }
}
