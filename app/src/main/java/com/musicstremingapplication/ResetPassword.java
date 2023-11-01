package com.musicstremingapplication;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button reset, login;
    private TextInputEditText email;
    private TextView show;
    private ProgressBar progressBar;
    private Drawable errorIcon;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        mAuth=FirebaseAuth.getInstance();

        //errorIcon=getResources().getDrawable(R.drawable.ic_)
        email = findViewById(R.id.reset_email);
        reset = findViewById(R.id.reset_submit);
        show= findViewById(R.id.show_text);
        progressBar=findViewById(R.id.restProgressBar);
        login = findViewById(R.id.back_login);

        login.setOnClickListener(view -> {
            Intent intent = new Intent(ResetPassword.this, SignIn.class);
            startActivity(intent);
            finish();
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
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
    }

    private void checkInput() {
        if (!email.getText().toString().isEmpty()) {
            reset.setEnabled(true);
            reset.setTextColor(getResources().getColor(R.color.skyblue));
        } else {
            reset.setEnabled(false);
            reset.setTextColor(getResources().getColor(R.color.red));
        }
    }
    private void resetPassword() {
        if (email.getText().toString().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                progressBar.setVisibility(View.VISIBLE);
                mAuth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       // show.setText(task.getException().getMessage());
                        if (task.isSuccessful()) {
                            show.setText("Check Your Email Reset Link Sended.");
                            show.setTextColor(getResources().getColor(R.color.skyblue));
                        } else {
                            show.setText("There are Issue To Sending Email Try After Sometime.");
                            show.setTextColor(getResources().getColor(R.color.red));
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                        show.setVisibility(View.VISIBLE);
                    }
                });
            }
        else {
                email.setError("Check Email Address");
                reset.setEnabled(true);
                reset.setTextColor(getResources().getColor(R.color.red));
            }
        }
}