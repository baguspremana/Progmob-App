package com.example.user_pc.semnas_ti.user;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.api.ApiClient;
import com.example.user_pc.semnas_ti.bantuan.PreferencesHelper;
import com.example.user_pc.semnas_ti.model.Profile;
import com.example.user_pc.semnas_ti.user.home.HomeUserFragment;
import com.example.user_pc.semnas_ti.user.profile.ProfileUserFragment;
import com.example.user_pc.semnas_ti.user.ticket.TiketUserFragment;
import com.example.user_pc.semnas_ti.user.ticketpayment.BayarTiketFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {
    PreferencesHelper preferencesHelper;
    Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        preferencesHelper = new PreferencesHelper(this);
        /*Toast.makeText(this, preferencesHelper.getFCMToken(), Toast.LENGTH_SHORT).show();*/
        Log.d("fcm", preferencesHelper.getFCMToken());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_user);
        bottomNavigationView.setOnNavigationItemSelectedListener(navUserListener);

        Bundle extra = getIntent().getExtras();
        if(extra != null){
            if(extra.containsKey("fragment")){
                bottomNavigationView.setSelectedItemId(R.id.nav_pesanTiket);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_user,
                        new TiketUserFragment()).commit();
            }else if (extra.containsKey("payment")){
                bottomNavigationView.setSelectedItemId(R.id.nav_uploadPembayaran);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_user,
                        new BayarTiketFragment()).commit();
            }else if (extra.containsKey("profile")){
                bottomNavigationView.setSelectedItemId(R.id.nav_profile);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_user,
                        new ProfileUserFragment()).commit();
            }
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_user,
                    new HomeUserFragment()).commit();
        }

        showProfile();
    }

    private void showProfile() {
        ApiClient.getService(this)
                .showProfile()
                .enqueue(new Callback<Profile>() {
                    @Override
                    public void onResponse(Call<Profile> call, Response<Profile> response) {
                        if (response.isSuccessful()){
                            profile = response.body();
//                            Log.d("tokenUser", profile.getFcmToken());
//                            Toast.makeText(UserActivity.this, String.valueOf(profile.getId()), Toast.LENGTH_SHORT).show();
//                            Toast.makeText(UserActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            if (profile.getFcmToken()==null && profile.getRole()==1){
                                saveFCM(profile.getId(), preferencesHelper.getFCMToken());
                            }else if (profile.getFcmToken()!=null && profile.getFcmToken()!=preferencesHelper.getFCMToken() && profile.getRole()!=1){
                                saveFCM(profile.getId(), preferencesHelper.getFCMToken());
                            }

                        }else {
                            Toast.makeText(UserActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Profile> call, Throwable t) {
                        Toast.makeText(UserActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveFCM(int id, String fcm){
        ApiClient.getService(this)
                .saveFCM(id, fcm)
                .enqueue(new Callback<com.example.user_pc.semnas_ti.model.Response>() {
                    @Override
                    public void onResponse(Call<com.example.user_pc.semnas_ti.model.Response> call, Response<com.example.user_pc.semnas_ti.model.Response> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(UserActivity.this, "Token Tersimpan", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(UserActivity.this, "Response Failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<com.example.user_pc.semnas_ti.model.Response> call, Throwable t) {
                        Toast.makeText(UserActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
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
