package com.example.user_pc.semnas_ti.admin.profile;

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

import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.bantuan.PreferencesHelper;

public class ProfileAdminFragment extends Fragment {
    protected ImageView imgProfile;
    protected TextView tvName, tvEmail, tvContact, tvGender;
    protected Button btnEdit;

    private PreferencesHelper preferencesHelper;

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

        preferencesHelper = new PreferencesHelper(getContext());

        tvName.setText(preferencesHelper.getName());
        tvEmail.setText(preferencesHelper.getEmail());
        tvContact.setText(preferencesHelper.getContact());
        if (preferencesHelper.getGender()==0){
            tvGender.setText("Perempuan");
        }else {
            tvGender.setText("Laki-laki");
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Edit Profile", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
