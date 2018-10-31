package com.example.user_pc.semnas_ti.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.admin.AdminActivity;
import com.example.user_pc.semnas_ti.api.ApiClient;
import com.example.user_pc.semnas_ti.bantuan.ConstantURL;
import com.example.user_pc.semnas_ti.bantuan.PreferencesHelper;
import com.example.user_pc.semnas_ti.model.User;
import com.example.user_pc.semnas_ti.user.UserActivity;

public class LoginFragment extends Fragment implements AuthView, View.OnClickListener {
    protected Button btnLogin;
    protected EditText etEmailLogin, etPasswordLogin;

    private PreferencesHelper preferencesHelper;

    private AuthPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_login, container, false);
        btnLogin = view.findViewById(R.id.btnLogin);
        etEmailLogin = view.findViewById(R.id.etEmailLogin);
        etPasswordLogin = view.findViewById(R.id.etPasswordLogin);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnLogin.setOnClickListener(this);
        presenter=new AuthPresenter(this,ApiClient.getService(getContext()));

        preferencesHelper=new PreferencesHelper(getContext());
    }

    @Override
    public void onSuccess(User user) {
        preferencesHelper.setUserLogin(user);
        if (user.getRole()==ConstantURL.Role.USER){
            startActivity(new Intent(getContext(),UserActivity.class));
        }else {
            startActivity(new Intent(getContext(),AdminActivity.class));
        }
        getActivity().finish();
        Toast.makeText(getContext(), "Login Berhasil", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError() {
        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(Throwable t) {
        Toast.makeText(getContext(), "Failed :"+t, Toast.LENGTH_SHORT).show();
    }

    public void login(){
        String email = etEmailLogin.getText().toString();
        String password = etPasswordLogin.getText().toString();

        if(validate(email, password)){
            presenter.login(email,password);
        }
    }

    public boolean validate(String email, String password){
        if (email.equals("")){
            etEmailLogin.setError("Field email tidak boleh kosong");
            return false;
        }

        if (password.equals("")){
            etPasswordLogin.setError("Field password tidak boleh kosong");
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                login();
                break;
        }
    }
}
