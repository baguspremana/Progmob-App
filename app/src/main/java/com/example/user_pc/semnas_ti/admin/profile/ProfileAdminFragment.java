package com.example.user_pc.semnas_ti.admin.profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.admin.profile.editprofile.EditProfileAdminActivity;
import com.example.user_pc.semnas_ti.api.ApiClient;
import com.example.user_pc.semnas_ti.bantuan.ConstantURL;
//import com.example.user_pc.semnas_ti.bantuan.PreferencesHelper;
import com.example.user_pc.semnas_ti.model.Profile;

public class ProfileAdminFragment extends Fragment implements View.OnClickListener, ProfileAdminView{
    protected ImageView imgProfile;
    protected TextView tvName, tvEmail, tvContact, tvGender;
    protected Button btnEdit;
    protected ProfileAdminPresenter presenter;
    Profile profile;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_admin, container, false);
        imgProfile = view.findViewById(R.id.image_profile_admin);

        tvName = view.findViewById(R.id.tv_profile_admin_name);
        tvEmail = view.findViewById(R.id.tv_profile_admin_email);
        tvContact = view.findViewById(R.id.tv_profile_admin_kontak);
        tvGender = view.findViewById(R.id.tv_profile_admin_gender);

        btnEdit = view.findViewById(R.id.btn_edit_profile_admin);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Now Loading...");

        presenter = new ProfileAdminPresenter(this, ApiClient.getService(getContext()));
        presenter.showProfile();

        btnEdit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_edit_profile_admin:
//                Toast.makeText(getContext(), "Edit Profile", Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putParcelable(EditProfileAdminActivity.KEY_PROFILE, profile);
                Intent intent = new Intent(getContext(), EditProfileAdminActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
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
        this.profile = profile;
        tvName.setText(profile.getName());
        tvEmail.setText(profile.getEmail());
        tvContact.setText(profile.getContact());
        if (profile.getGender()==1){
            tvGender.setText("Laki-laki");
        }else {
            tvGender.setText("Perempuan");
        }

        if (profile.getPhotoProfile()==null){
            imgProfile.setImageResource(R.drawable.cute_profile);
        }else {
            Glide.with(getContext()).load(ConstantURL.URL.imgUser(profile.getPhotoProfile())).into(imgProfile);
        }
    }

    @Override
    public void onError() {
        Toast.makeText(getContext(), "Response Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(Throwable t) {
        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
    }
}
