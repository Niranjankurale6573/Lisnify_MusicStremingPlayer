package com.musicstremingapplication;

import android.annotation.SuppressLint;

import com.google.firebase.firestore.auth.User;

@SuppressLint("RestrictedApi")
public class GlobalVariable {
    public static User currentUser = new User(null);

}
