package com.musicstremingapplication;

import static com.musicstremingapplication.R.color.red;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

public class SignIn extends AppCompatActivity {
    private FirebaseAuth mAuth;
   private Button submit,newuser,reset;
   private TextInputEditText username,password;
    static boolean flag;
   ImageView eye;
   private ProgressBar ProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_sign_in);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        newuser=findViewById(R.id.newuser);
        ProgressBar=findViewById(R.id.l_progressBar);
        submit=findViewById(R.id.submit);
        reset=findViewById(R.id.forget);
        eye=findViewById(R.id.image_eye);

        reset.setOnClickListener(view->{
            Intent intent=new Intent(SignIn.this,ResetPassword.class);
            startActivity(intent);
            finish();
        });
        newuser.setOnClickListener(view ->{
            Intent intent= new Intent(SignIn.this,SignUp.class);
            startActivity(intent);
            finish();
        } );

        flag=true;
        eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag){
                    password.setInputType(InputType.TYPE_CLASS_TEXT);
                    flag=false;
                }
                if(flag) {
                    password.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                    flag=true;
                }
            }
        });
        
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setEnabled(false);
                username.setTextColor(getResources().getColor(R.color.skyblue));
                SubmitWithFirebase();
            }
        });

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                chekInput();
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
                chekInput();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void SubmitWithFirebase() {
        if(username.getText().toString().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            if (password.getText().toString().equals(password.getText().toString())) {
                mAuth.signInWithEmailAndPassword(username.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                ProgressBar.setVisibility(View.INVISIBLE);
                                if (task.isSuccessful()) {

                                    Intent intent = new Intent(SignIn.this, Dashboard.class);
                                    ProgressBar.setVisibility(View.VISIBLE);
                                    Toast.makeText(getApplicationContext(), "Let's Play Beats", Toast.LENGTH_LONG).show();
                                    startActivity(intent);
                                    finish();


//                                    FirebaseDatabase.getInstance().getReference("User")
//                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                            .addListenerForSingleValueEvent(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                                    GlobalVariable.currentUser=snapshot.getValue(User.class);
//                                                    Intent intent = new Intent(SignIn.this, Dashboard.class);
//                                                    ProgressBar.setVisibility(View.VISIBLE);
//                                                    Toast.makeText(getApplicationContext(), "Let's Play Beats", Toast.LENGTH_LONG).show();
//                                                    startActivity(intent);
//                                                    finish();
//
//                                                }
//
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError error) {
//
//                                                }
//                                            });
                                    }
                                else {
                                    Toast.makeText(getApplicationContext(),"Check Email & Password", Toast.LENGTH_LONG).show();
                                    submit.setEnabled(true);
                                    submit.setTextColor(getResources().getColor(R.color.red));
                                }
                            }
                        });
            } else {
                password.setError("Invalid Password Check Password");
                password.setEnabled(true);
                password.setTextColor(getResources().getColor(R.color.red));
            }
        }
        else {
            username.setError("Invalid Email Check Email");
            username.setEnabled(true);
            username.setTextColor(getResources().getColor(R.color.red));
        }
    }

    private void chekInput() {
        if(!username.getText().toString().isEmpty()) {
            if(!password.getText().toString().isEmpty()) {
                submit.setEnabled(true);
                submit.setTextColor(getResources().getColor(R.color.skyblue));
            }
            else{
                    submit.setEnabled(false);
                    submit.setTextColor(getResources().getColor(R.color.red));
                }
        }
        else {
                    submit.setEnabled(false);
                    submit.setTextColor(getResources().getColor(R.color.red));
        }
    }
}