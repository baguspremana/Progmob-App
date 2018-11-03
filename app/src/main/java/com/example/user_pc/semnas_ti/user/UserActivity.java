package com.example.user_pc.semnas_ti.user;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.user_pc.semnas_ti.R;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_user);
        bottomNavigationView.setOnNavigationItemSelectedListener(navUserListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_user,
                new HomeUserFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navUserListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedUserFragment = null;

                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedUserFragment = new HomeUserFragment();
                            break;
                        case R.id.nav_pesanTiket:
                            selectedUserFragment = new TiketUserFragment();
                            break;
                        case R.id.nav_uploadPembayaran:
                            selectedUserFragment = new BayarTiketFragment();
                            break;
                        case R.id.nav_profile:
                            selectedUserFragment = new ProfileUserFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_user,
                            selectedUserFragment).commit();

                    return true;
                }
            };
}
