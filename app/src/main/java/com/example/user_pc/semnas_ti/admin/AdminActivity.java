package com.example.user_pc.semnas_ti.admin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.admin.dataadmin.AdminFragment;
import com.example.user_pc.semnas_ti.admin.infoseminar.InfoSeminarFragment;
import com.example.user_pc.semnas_ti.admin.profile.ProfileAdminFragment;
import com.example.user_pc.semnas_ti.admin.verifikasi.VerifikasiFragment;
import com.example.user_pc.semnas_ti.auth.LoginRegisterActivity;
import com.example.user_pc.semnas_ti.bantuan.PreferencesHelper;

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private PreferencesHelper preferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preferencesHelper = new PreferencesHelper(this);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Bundle extra = getIntent().getExtras();
        if(extra != null){
            if (extra.containsKey("tiket")){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin,
                        new InfoSeminarFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_seminar);
            }else if (extra.containsKey("addAdmin")){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin,
                        new AdminFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_admin);
            }
        }else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin,
                    new DashboardFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_dashboard);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_dashboard:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin,
                        new DashboardFragment()).commit();
                break;
            case R.id.nav_verifikasi:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin,
                        new VerifikasiFragment()).commit();
                break;
            case R.id.nav_ticket_scanner:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin,
                        new ScanFragment()).commit();
                break;
            case R.id.nav_seminar:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin,
                        new InfoSeminarFragment()).commit();
                break;
            case R.id.nav_admin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin,
                        new AdminFragment()).commit();
                break;
            case R.id.nav_faq_admin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin,
                        new FaqAdminFragment()).commit();
                break;
            case R.id.nav_admin_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin,
                        new ProfileAdminFragment()).commit();
                break;
            case R.id.nav_logout:
                preferencesHelper.logout();
                Intent intent = new Intent(AdminActivity.this, LoginRegisterActivity.class);
                startActivity(intent);
                finish();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}
