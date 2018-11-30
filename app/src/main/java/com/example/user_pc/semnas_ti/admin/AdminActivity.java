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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.admin.dashboardadmin.DashboardFragment;
import com.example.user_pc.semnas_ti.admin.dataadmin.AdminFragment;
import com.example.user_pc.semnas_ti.admin.datafaq.FaqAdminFragment;
import com.example.user_pc.semnas_ti.admin.infoseminar.InfoSeminarFragment;
import com.example.user_pc.semnas_ti.admin.profile.ProfileAdminFragment;
import com.example.user_pc.semnas_ti.admin.scanticket.ScanFragment;
import com.example.user_pc.semnas_ti.admin.verifikasi.VerifikasiFragment;
import com.example.user_pc.semnas_ti.api.ApiClient;
import com.example.user_pc.semnas_ti.auth.LoginRegisterActivity;
import com.example.user_pc.semnas_ti.bantuan.ConstantURL;
import com.example.user_pc.semnas_ti.bantuan.PreferencesHelper;
import com.example.user_pc.semnas_ti.model.Profile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private PreferencesHelper preferencesHelper;
    Profile profile;
    TextView navUser, navEmail;
    ImageView imgNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preferencesHelper = new PreferencesHelper(this);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        navUser = headerView.findViewById(R.id.header_name);
        navEmail = headerView.findViewById(R.id.header_email);
        imgNav = headerView.findViewById(R.id.header_profile);

        ApiClient.getService(this)
                .showProfile()
                .enqueue(new Callback<Profile>() {
                    @Override
                    public void onResponse(Call<Profile> call, Response<Profile> response) {
                        if (response.isSuccessful()){
                            profile = response.body();
                            navUser.setText(profile.getName());
                            navEmail.setText(profile.getEmail());
                            if (profile.getPhotoProfile()==null){
                                imgNav.setImageResource(R.drawable.user_icon);
                            }else {
                                Glide.with(AdminActivity.this).load(ConstantURL.URL.imgUser(profile.getPhotoProfile())).into(imgNav);
                            }
                        }else {
                            Toast.makeText(AdminActivity.this, "Response Failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Profile> call, Throwable t) {
                        Toast.makeText(AdminActivity.this, "Error"+t, Toast.LENGTH_SHORT).show();
                    }
                });
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
            }else if (extra.containsKey("faq")){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin,
                        new FaqAdminFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_faq_admin);
            }else if(extra.containsKey("verifikasi")){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin,
                        new VerifikasiFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_verifikasi);
            }else if(extra.containsKey("profile")){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin,
                        new ProfileAdminFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_admin_profile);
            }else if(extra.containsKey("scan")){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin,
                        new ScanFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_ticket_scanner);
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
