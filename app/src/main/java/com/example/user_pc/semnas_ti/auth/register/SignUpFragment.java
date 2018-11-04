package com.example.user_pc.semnas_ti.auth.register;

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
import com.example.user_pc.semnas_ti.auth.AuthPresenter;
import com.example.user_pc.semnas_ti.auth.AuthView;
import com.example.user_pc.semnas_ti.auth.LoginRegisterActivity;
import com.example.user_pc.semnas_ti.bantuan.ConstantURL;
import com.example.user_pc.semnas_ti.bantuan.PreferencesHelper;
import com.example.user_pc.semnas_ti.model.User;
import com.example.user_pc.semnas_ti.user.UserActivity;

public class SignUpFragment extends Fragment implements AuthView, View.OnClickListener{

    Button btnSignUp;
    EditText etName;
    EditText etEmail;
    EditText etPassword;
    EditText etContact;

    private PreferencesHelper preferencesHelper;

    private AuthPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_sign_up, container, false);
        btnSignUp = view.findViewById(R.id.btnSignUp);
        etName = view.findViewById(R.id.etname);
        etEmail = view.findViewById(R.id.etEmail);
        etContact = view.findViewById(R.id.etContact);
        etPassword = view.findViewById(R.id.etPassword);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnSignUp.setOnClickListener(this);
        presenter=new AuthPresenter(this,ApiClient.getService(getContext()));

        preferencesHelper=new PreferencesHelper(getContext());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSignUp:
                register();
                break;
        }
    }

    @Override
    public void onSuccess(User user) {
        preferencesHelper.setUserLogin(user);
        startActivity(new Intent(getContext(), LoginRegisterActivity.class));
        getActivity().finish();
        Toast.makeText(getContext(), "Registrasi Akun Berhasil", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError() {
        Toast.makeText(getContext(), "Response Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(Throwable t) {
        Toast.makeText(getContext(), "Eror :"+t, Toast.LENGTH_SHORT).show();
    }

    private void register(){
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String contact = etContact.getText().toString();
        String password = etPassword.getText().toString();

        if (validate(name, email, contact, password)){
            presenter.register(name, email, contact, password);
        }

    }

    public boolean validate(String name, String email, String contact, String password){
        if (name.equals("")){
            etName.setError("Field Nama Tidak Boleh Kosong");
            return false;
        }

        if (email.equals("")){
            etEmail.setError("Field E-Mail Tidak Boleh Kosong");
            return false;
        }

        if (contact.equals("")){
            etContact.setError("Field Nomor Telepon Tidak Boleh Kosong");
            return false;
        }

        if (password.equals("")){
            etPassword.setError("Field Password Tidak Boleh Kosong");
            return false;
        }

        return true;
    }
}
