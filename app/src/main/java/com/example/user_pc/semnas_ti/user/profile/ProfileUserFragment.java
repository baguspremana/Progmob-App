package com.example.user_pc.semnas_ti.user.profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.api.ApiClient;
import com.example.user_pc.semnas_ti.auth.LoginRegisterActivity;
import com.example.user_pc.semnas_ti.bantuan.ConstantURL;
import com.example.user_pc.semnas_ti.bantuan.DbHelper;
import com.example.user_pc.semnas_ti.bantuan.PreferencesHelper;
import com.example.user_pc.semnas_ti.model.Profile;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileUserFragment extends Fragment implements ProfileUserView {
    protected ImageView btnLogout, btnEditProfile;
    protected TextView profileName;
    protected TextView emailProfile;
    protected TextView contactProfile;
    protected TextView genderProfile;
    protected ProfileUserPresenter presenter;
    CircleImageView imageView;

    Profile profile;

    private PreferencesHelper preferencesHelper;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile_user, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        btnLogout = view.findViewById(R.id.logoutApp);
        btnEditProfile = view.findViewById(R.id.editProfile);
        profileName = view.findViewById(R.id.profile_name);
        emailProfile = view.findViewById(R.id.tvEmailProfile);
        contactProfile = view.findViewById(R.id.tvContactProfile);
        genderProfile = view.findViewById(R.id.tvGenderProfile);
        imageView = view.findViewById(R.id.image_profile_user);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        preferencesHelper = new PreferencesHelper(getContext());

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Now Loading...");

        showProfileLocal();
        presenter = new ProfileUserPresenter(this, ApiClient.getService(getContext()));
        presenter.showProfile();

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(EditProfileUserActivity.KEY_PROFILE, profile);
                Intent intent = new Intent(getContext(), EditProfileUserActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferencesHelper.logout();
                Intent intent = new Intent(getContext(), LoginRegisterActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });
    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        progressDialog.hide();
    }

    @Override
    public void onSuccess(Profile profile) {
        DbHelper dbHelper = new DbHelper(getContext());
        dbHelper.deleteProfile();

        this.profile = profile;
        dbHelper.insertProfile(profile.getId(), profile.getName(), profile.getEmail(), profile.getContact(),
                profile.getGender(), profile.getPhotoProfile());

        profileName.setText(profile.getName());
        emailProfile.setText(profile.getEmail());
        contactProfile.setText(profile.getContact());
        if (profile.getGender()==1){
            genderProfile.setText("Laki-Laki");
        }else {
            genderProfile.setText("Perempuan");
        }

        if (profile.getPhotoProfile()==null){
            imageView.setImageResource(R.drawable.cute_profile);
        }else {
            Glide.with((getContext())).load(ConstantURL.URL.imgUser(profile.getPhotoProfile())).into(imageView);
        }
    }

    @Override
    public void onError() {
        Toast.makeText(getContext(), "Response Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(Throwable t) {
        Toast.makeText(getContext(), "Anda Sedang Offline", Toast.LENGTH_SHORT).show();
    }

    private void showProfileLocal(){
        DbHelper dbHelper=new DbHelper(getContext());
        profile=dbHelper.selectProfile();

        profileName.setText(profile.getName());
        emailProfile.setText(profile.getEmail());
        contactProfile.setText(profile.getContact());
        if (profile.getGender()==1){
            genderProfile.setText("Laki-Laki");
        }else {
            genderProfile.setText("Perempuan");
        }

        if (profile.getPhotoProfile()==null){
            imageView.setImageResource(R.drawable.cute_profile);
        }else {
            Glide.with((getContext())).load(ConstantURL.URL.imgUser(profile.getPhotoProfile())).into(imageView);
        }
    }
}
