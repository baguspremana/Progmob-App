package com.example.user_pc.semnas_ti.bantuan;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;

public class FirebaseHelper extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {
        PreferencesHelper preferencesHelper = new PreferencesHelper(this);
        preferencesHelper.setFCMToken(s);
        Log.d("asa",s);
    }
}
