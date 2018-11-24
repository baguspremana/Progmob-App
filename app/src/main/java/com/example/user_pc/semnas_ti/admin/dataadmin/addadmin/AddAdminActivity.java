package com.example.user_pc.semnas_ti.admin.dataadmin.addadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.admin.AdminActivity;
import com.example.user_pc.semnas_ti.admin.dataadmin.AdminFragment;
import com.example.user_pc.semnas_ti.api.ApiClient;
import com.example.user_pc.semnas_ti.api.ApiService;
import com.example.user_pc.semnas_ti.model.Response;

public class AddAdminActivity extends AppCompatActivity implements View.OnClickListener, AddAdminView{
    EditText etAddName, etAddEmail, etAddContact, etAddPassword;
    RadioGroup radioGroup;
    int gender = 0;
    Button btnAddAdmin;
    AddAdminPresenter presenter;
    ApiService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);

        getSupportActionBar().hide();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        init();
        service = ApiClient.getService(this);
        presenter = new AddAdminPresenter(this, service);
    }

    private void init() {
        etAddName = findViewById(R.id.et_add_admin_name);
        etAddEmail = findViewById(R.id.et_add_admin_email);
        etAddContact = findViewById(R.id.et_add_admin_contact);
        etAddPassword = findViewById(R.id.et_add_admin_password);
        btnAddAdmin = findViewById(R.id.btn_add_admin);
        radioGroup = findViewById(R.id.rg_add_admin_gender);

        btnAddAdmin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_admin:
                simpan();
                break;
        }
    }

    private void simpan() {
        String name = etAddName.getText().toString();
        String email = etAddEmail.getText().toString();
        String contact = etAddContact.getText().toString();
        String password = etAddPassword.getText().toString();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.rb_female){
                    gender = 0;
                }else if (checkedId==R.id.rb_male){
                    gender = 1;
                }
            }
        });

        if (validate(name,email,contact,password)){
            presenter.addAdmin(name,email,gender,contact,password);
        }
    }

    public boolean validate(String name, String email, String contact, String password){
        if (name.equals("")){
            etAddName.setError("Field Tidak Boleh Kosong");
            return false;
        }

        if (email.equals("")){
            etAddEmail.setError("Field Tidak Boleh Kosong");
            return false;
        }

        if (contact.equals("")){
            etAddContact.setError("Field Tidak Boleh Kosong");
            return false;
        }

        if (password.equals("")){
            etAddPassword.setError("Field Tidak Boleh Kosong");
            return false;
        }

        return true;
    }

    @Override
    public void onSuccess(Response response) {
        Intent intent = new Intent(AddAdminActivity.this, AdminActivity.class);
        intent.putExtra("addAdmin", AdminFragment.class);
        startActivity(intent);
        finish();
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError() {
        Toast.makeText(this, "Response Valied", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(Throwable t) {
        Toast.makeText(this, "Error"+t, Toast.LENGTH_SHORT).show();
    }
}
