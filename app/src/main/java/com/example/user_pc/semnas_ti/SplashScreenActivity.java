package com.example.user_pc.semnas_ti;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.user_pc.semnas_ti.admin.AdminActivity;
import com.example.user_pc.semnas_ti.auth.LoginRegisterActivity;
import com.example.user_pc.semnas_ti.bantuan.ConstantURL;
import com.example.user_pc.semnas_ti.bantuan.PreferencesHelper;
import com.example.user_pc.semnas_ti.user.UserActivity;

public class SplashScreenActivity extends AppCompatActivity {

    PreferencesHelper preferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        getSupportActionBar().hide();

        preferencesHelper=new PreferencesHelper(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (preferencesHelper.getLogin()){
                    if (preferencesHelper.getRole()==ConstantURL.Role.USER){
                        intent=new Intent(SplashScreenActivity.this,UserActivity.class);
                    }else {
                        intent=new Intent(SplashScreenActivity.this,AdminActivity.class);
                    }
                }else {
                    intent=new Intent(SplashScreenActivity.this,LoginRegisterActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
