package com.example.user_pc.semnas_ti.admin.datafaq.adddatafaq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.admin.AdminActivity;
import com.example.user_pc.semnas_ti.admin.datafaq.FaqAdminFragment;
import com.example.user_pc.semnas_ti.api.ApiClient;
import com.example.user_pc.semnas_ti.api.ApiService;
import com.example.user_pc.semnas_ti.model.Response;

public class AddFaqAdminActivity extends AppCompatActivity implements View.OnClickListener, AddFaqAdminView {
    EditText etQuestion, etAnswer;
    Button btnAdd;

    AddFaqAdminPresenter presenter;
    ApiService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faq_admin);

        service = ApiClient.getService(this);
        presenter = new AddFaqAdminPresenter(this, service);

        getSupportActionBar().hide();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        init();
    }

    private void init() {
        etQuestion = findViewById(R.id.et_add_question);
        etAnswer = findViewById(R.id.et_add_answer);
        btnAdd = findViewById(R.id.btn_add_faq_admin);

        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_faq_admin:
                tambah();
        }
    }

    private void tambah() {
        String question = etQuestion.getText().toString();
        String answer = etAnswer.getText().toString();

        if (validate(question, answer)){
            presenter.addFAQ(question, answer);
        }
    }

    public boolean validate(String question, String answer){
        if (question.equals("")){
            etQuestion.setError("Field Nama Tidak Boleh Kosong");
            return false;
        }

        if (answer.equals("")){
            etAnswer.setError("Field E-Mail Tidak Boleh Kosong");
            return false;
        }

        return true;
    }

    @Override
    public void onSuccess(Response response) {
        Intent intent = new Intent(this, AdminActivity.class);
        intent.putExtra("faq", FaqAdminFragment.class);
        startActivity(intent);
        finish();
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError() {
        Toast.makeText(this, "Response Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(Throwable t) {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }
}
