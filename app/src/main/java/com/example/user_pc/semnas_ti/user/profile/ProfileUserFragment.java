package com.example.user_pc.semnas_ti.user.profile;

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

import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.auth.LoginRegisterActivity;
import com.example.user_pc.semnas_ti.bantuan.PreferencesHelper;

public class ProfileUserFragment extends Fragment {
    protected ImageView btnLogout;
    protected TextView profileName;
    protected TextView emailProfile;
    protected TextView contactProfile;

    private PreferencesHelper preferencesHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile_user, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        btnLogout = view.findViewById(R.id.logoutApp);
        profileName = view.findViewById(R.id.profile_name);
        emailProfile = view.findViewById(R.id.tvEmailProfile);
        contactProfile = view.findViewById(R.id.tvContactProfile);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        preferencesHelper = new PreferencesHelper(getContext());

        profileName.setText(preferencesHelper.getName());
        emailProfile.setText(preferencesHelper.getEmail());
        contactProfile.setText(preferencesHelper.getContact());

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
}
