package com.example.user_pc.semnas_ti.user;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.user.home.HomeUserFragment;
import com.example.user_pc.semnas_ti.user.profile.ProfileUserFragment;
import com.example.user_pc.semnas_ti.user.ticket.TiketUserFragment;
import com.example.user_pc.semnas_ti.user.ticketpayment.BayarTiketFragment;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_user);
        bottomNavigationView.setOnNavigationItemSelectedListener(navUserListener);

        Bundle extra = getIntent().getExtras();
        if(extra != null){
            if(extra.containsKey("fragment")){
                bottomNavigationView.setSelectedItemId(R.id.nav_pesanTiket);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_user,
                        new TiketUserFragment()).commit();
            }
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_user,
                    new HomeUserFragment()).commit();
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navUserListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedUserFragment = null;

                    switch (item.getItemId()) {
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
