package com.example.user_pc.semnas_ti.user;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.bigscreen.iconictabbar.view.IconicTab;
import com.bigscreen.iconictabbar.view.IconicTabBar;
import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.api.ApiClient;
import com.example.user_pc.semnas_ti.bantuan.PreferencesHelper;
import com.example.user_pc.semnas_ti.model.Profile;
import com.example.user_pc.semnas_ti.user.home.HomeUserFragment;
import com.example.user_pc.semnas_ti.user.profile.ProfileUserFragment;
import com.example.user_pc.semnas_ti.user.ticket.TiketUserFragment;
import com.example.user_pc.semnas_ti.user.ticketpayment.BayarTiketFragment;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {

    private static final String TAG = UserActivity.class.getSimpleName();

    PreferencesHelper preferencesHelper;
    Profile profile;
    private IconicTabBar iconicTabBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);

        preferencesHelper = new PreferencesHelper(this);
        Log.d("fcm", preferencesHelper.getFCMToken());

        iconicTabBar = findViewById(R.id.bottom_navigation_user);

        Bundle extra = getIntent().getExtras();
        if (extra != null){
            if (extra.containsKey("fragment")){
                iconicTabBar.setSelectedTab(1);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_user,
                        new TiketUserFragment()).commit();
            }else if (extra.containsKey("payment")){
                iconicTabBar.setSelectedTab(2);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_user,
                        new BayarTiketFragment()).commit();
            }else if (extra.containsKey("userProfile")){
                iconicTabBar.setSelectedTab(3);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_user,
                        new ProfileUserFragment()).commit();
            }
        }else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_user,
                    new HomeUserFragment()).commit();
        }

        initViews();
        showProfile();
    }

    private void initViews() {
        iconicTabBar.setOnTabSelectedListener(new IconicTabBar.OnTabSelectedListener() {
            @Override
            public void onSelected(IconicTab tab, int position) {
                Log.d(TAG, "selected tab on= "+position);
                Fragment selectedUserFragment = null;
                int tabId = tab.getId();
                switch (tabId){
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
            }

            @Override
            public void onUnselected(IconicTab tab, int position) {
                Log.d(TAG, "unselected tab on= "+position);
            }
        });
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
                            Toasty.warning(UserActivity.this, "Response Failed", Toast.LENGTH_SHORT, true).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<Profile> call, Throwable t) {
                        Toasty.error(UserActivity.this, "Anda Sedang Offline", Toast.LENGTH_SHORT, true).show();

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
                            Toasty.success(UserActivity.this, "Success", Toast.LENGTH_SHORT, true).show();
                        }else {
                            Toasty.warning(UserActivity.this, "Response Failed", Toast.LENGTH_SHORT, true).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<com.example.user_pc.semnas_ti.model.Response> call, Throwable t) {
                        Toasty.error(UserActivity.this, "Anda Sedang Offline", Toast.LENGTH_SHORT, true).show();
                    }
                });
    }

}
