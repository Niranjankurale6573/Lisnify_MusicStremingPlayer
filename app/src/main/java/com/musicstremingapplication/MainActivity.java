package com.musicstremingapplication;

import static android.app.ActivityOptions.makeSceneTransitionAnimation;
import static android.os.Build.VERSION_CODES.LOLLIPOP;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private Handler handler;
    private FirebaseAuth mAuth;
    private int splash_screen=2000;
    Animation topAnim,bottomAnim;
    ImageView image;
    TextView slogon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler=new Handler();
        mAuth=FirebaseAuth.getInstance();
        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        image=findViewById(R.id.imageview_m);
        //image.setAnimation(topAnim);
        slogon=findViewById(R.id.logo_name_m);
        slogon.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = null;
                if (mAuth.getCurrentUser()!= null) {
                    intent = new Intent(MainActivity.this, Dashboard.class);
                        startActivity(intent);
                        finish();
                }
                else {
                    intent = new Intent(MainActivity.this, SignIn.class);
                        if (Build.VERSION.SDK_INT >= LOLLIPOP) {
                            //Using Animation
                            Pair[] pairs = new Pair[2];
                            pairs[0] = new Pair<View, String>(image, "logo_image");
                            pairs[1] = new Pair<View, String>(slogon, "logo_name");
                            ActivityOptions option = makeSceneTransitionAnimation(MainActivity.this, pairs);
                            startActivity(intent, option.toBundle());
                            finish();
                        }
                        else {
                            startActivity(intent);
                            finish();
                        }
                }
            }
        },splash_screen);

    }
}