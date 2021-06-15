package com.clbyte.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegistrationForm extends AppCompatActivity {
    TextView create;
    TextView username,email,password;
    Button registerbtn;
    FirebaseAuth auth;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);


        username = findViewById(R.id.usernamefield);
        email = findViewById(R.id.emailfield);
        password = findViewById(R.id.passwordfield);
        registerbtn = findViewById(R.id.registerbtn);

        auth = FirebaseAuth.getInstance();

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String user = username.getText().toString();
               String userEmail = email.getText().toString();
               String userPass = password.getText().toString();

               if(TextUtils.isEmpty(user) || TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userPass))
               {
                   Toast.makeText(RegistrationForm.this,"All Fields are required",Toast.LENGTH_SHORT).show();
               }
               else if(userPass.length()<6)
                {
                    Toast.makeText(RegistrationForm.this,"Password must be atleast 6 digits",Toast.LENGTH_SHORT).show();
                }
               else
               {
                   Register(user,userEmail,userPass);
               }
            }
        });
        create = findViewById(R.id.createaccount);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallLoginActivity(v);
            }
        });
    }
    public void CallLoginActivity(View v){

        Intent it = new Intent(RegistrationForm.this, login.class);
        startActivity(it);
        finish();
    }

    private void Register(final String user_name, String user_email, String user_password){
            auth.createUserWithEmailAndPassword(user_email,user_password).
                    addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                FirebaseUser firebaseUser = auth.getCurrentUser();
                                assert firebaseUser != null;
                                String userID = firebaseUser.getUid();
                                myRef = FirebaseDatabase.getInstance().getReference("Users").child(userID);

                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("id", userID);
                                hashMap.put("username", user_name);
                                hashMap.put("imageURL", "default");
                                hashMap.put("status", "offline");
                                hashMap.put("search", username.toString().toLowerCase());

                                myRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Intent it = new Intent(RegistrationForm.this, MainScreen.class);
                                            it.addFlags(it.FLAG_ACTIVITY_CLEAR_TASK | it.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(it);
                                            finish();
                                        }
                                    }
                                });
                            }
                            else
                            {
                                Toast.makeText(RegistrationForm.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
    }
}
