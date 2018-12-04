package com.example.user_pc.semnas_ti.auth;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.auth.faquser.FaqFragment;
import com.example.user_pc.semnas_ti.auth.login.LoginFragment;
import com.example.user_pc.semnas_ti.auth.register.SignUpFragment;
import com.example.user_pc.semnas_ti.bantuan.DbHelper;

public class LoginRegisterActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setBackgroundDrawableResource(R.drawable.bg_satu);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        BottomNavigationView btnNav = findViewById(R.id.bottom_navigation);
        btnNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new LoginFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.nav_login:
                            selectedFragment = new LoginFragment();
                            break;
                        case R.id.nav_signUp:
                            selectedFragment = new SignUpFragment();
                            break;
                        case R.id.nav_faq:
                            selectedFragment = new FaqFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };


}
