package com.musicstremingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private Animation topAnim, bottomAnim;
    private Button submit, login;
    private TextInputEditText name, email, mobile, password, cpassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);


        name = findViewById(R.id.r_name);
        email = findViewById(R.id.r_email);
        mobile = findViewById(R.id.r_mobile);
        password = findViewById(R.id.r_password);
        cpassword = findViewById(R.id.r_cpassword);
        progressBar=findViewById(R.id.r_progressBar);
        login = findViewById(R.id.r_login);
        submit = findViewById(R.id.r_submit);

        login.setOnClickListener(view -> {
            Intent intent = new Intent(SignUp.this, SignIn.class);
            startActivity(intent);
            finish();
        });
        submit.setOnClickListener(view -> {
            SignUpWithFirebase();
            submit.setEnabled(false);
            submit.setTextColor(getResources().getColor(R.color.skyblue));
        });


        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        cpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void SignUpWithFirebase() {

        if (email.getText().toString().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            if (password.getText().toString().equals(cpassword.getText().toString())) {
                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.INVISIBLE);
                                if (task.isSuccessful()) {
                                    Map<String, String> user= new HashMap<>();
                                    user.put("name",name.getText().toString());
                                    user.put("emailId",email.getText().toString());
                                    db.collection("users")
                                            .document(task.getResult().getUser().getUid())
                                            .set(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Intent intent = new Intent(SignUp.this, SignIn.class);
                                                    progressBar.setVisibility(View.VISIBLE);
                                                    Toast.makeText(getApplicationContext(), "Registration Successfuly", Toast.LENGTH_LONG).show();
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                                                submit.setEnabled(true);
                                                submit.setTextColor(getResources().getColor(R.color.red));
                                        }
                                    });

                                } else {
                                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                          submit.setEnabled(true);
                                          submit.setTextColor(getResources().getColor(R.color.red));
                                          
                                }
                            }
                        });
            } else {
                cpassword.setError("Password doesnot match.");
                submit.setEnabled(true);
                submit.setTextColor(getResources().getColor(R.color.red));
                Toast.makeText(getApplicationContext(),"Check Password",Toast.LENGTH_SHORT).show();
            }
        } else {
            email.setError("Invalid Email Pattern.!");
            submit.setEnabled(true);
            submit.setTextColor(getResources().getColor(R.color.red));
            Toast.makeText(getApplicationContext(),"Invalid Email",Toast.LENGTH_SHORT).show();
        }
    }

    private void checkInput() {
        if (!name.getText().toString().isEmpty()) {
            if (!email.getText().toString().isEmpty()) {
                if (!mobile.getText().toString().isEmpty()) {
                    if (!password.getText().toString().isEmpty() && password.getText().toString().length() >6) {
                        if (!cpassword.getText().toString().isEmpty()) {
                            submit.setEnabled(true);
                            submit.setTextColor(getResources().getColor(R.color.skyblue));
                        } else {
                            submit.setEnabled(false);
                            submit.setTextColor(getResources().getColor(R.color.red));
                        }
                    } else {
                        submit.setEnabled(false);
                        submit.setTextColor(getResources().getColor(R.color.red));
                    }
                } else {
                    submit.setEnabled(false);
                    submit.setTextColor(getResources().getColor(R.color.red));
                }
            } else {
                submit.setEnabled(false);
                submit.setTextColor(getResources().getColor(R.color.red));
            }
        } else {
            submit.setEnabled(false);
            submit.setTextColor(getResources().getColor(R.color.red));
        }

    }
}
