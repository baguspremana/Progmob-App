package com.example.user_pc.semnas_ti.admin.datafaq.detaildatafaq;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.admin.AdminActivity;
import com.example.user_pc.semnas_ti.admin.datafaq.FaqAdminFragment;
import com.example.user_pc.semnas_ti.api.ApiClient;
import com.example.user_pc.semnas_ti.api.ApiService;
import com.example.user_pc.semnas_ti.bantuan.DateFormated;
import com.example.user_pc.semnas_ti.model.FaqResponse;
import com.example.user_pc.semnas_ti.model.Response;

public class DetailFaqAdminActivity extends AppCompatActivity implements View.OnClickListener, DetailFaqAdminView {
    public static final String KEY_ADMIN = "faqResponse";

    TextView tvQuestion, tvAnswer, tvName, tvEmail, tvTanggal, tvUpdate;
    ImageButton btnEdit, btnDetele;

    FaqResponse faqResponse;

    DetailFaqAdminPresenter presenter;
    ApiService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_faq_admin);
        service = ApiClient.getService(this);
        presenter = new DetailFaqAdminPresenter(this, service);

        init();
        setView();

        getSupportActionBar().hide();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void init() {
        faqResponse = getIntent().getParcelableExtra(KEY_ADMIN);

        presenter = new DetailFaqAdminPresenter(this, ApiClient.getService(this));

        tvQuestion = findViewById(R.id.tv_detail_question);
        tvAnswer = findViewById(R.id.tv_detail_answer);
        tvName = findViewById(R.id.tv_detail_created_faq);
        tvEmail = findViewById(R.id.tv_detail_created_faq_email);
        tvTanggal = findViewById(R.id.tv_detail_created_faq_tanggal);
        tvUpdate = findViewById(R.id.tv_detail_created_faq_update);
        btnEdit = findViewById(R.id.edit_faq);
        btnDetele = findViewById(R.id.delete_faq);

        btnEdit.setOnClickListener(this);
        btnDetele.setOnClickListener(this);
    }

    private void setView() {
        String tanggal = DateFormated.setDate(faqResponse.getCreatedAt());
        String update = DateFormated.setDate(faqResponse.getUpdatedAt());

        tvQuestion.setText(faqResponse.getQuestion());
        tvAnswer.setText(faqResponse.getAnswer());
        tvName.setText(faqResponse.getName());
        tvEmail.setText(faqResponse.getEmail());
        tvTanggal.setText(tanggal);
        tvUpdate.setText(update);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_faq:
                showDialog();
                break;
            case R.id.delete_faq:
                showDialogDelet();
                break;
        }
    }

    private void showDialog() {
        Button btnEditDialog;
        final EditText etQuestion, etAnswer;

        AlertDialog.Builder dialog;
        dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_faq, null );

        service = ApiClient.getService(this);
        presenter = new DetailFaqAdminPresenter(this, service);

        btnEditDialog = dialogView.findViewById(R.id.btn_edit_faq_admin);
        etQuestion = dialogView.findViewById(R.id.et_edit_question);
        etAnswer = dialogView.findViewById(R.id.et_edit_answer);

        etQuestion.setText(faqResponse.getQuestion());
        etAnswer.setText(faqResponse.getAnswer());

        btnEditDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = etQuestion.getText().toString();
                String answer = etAnswer.getText().toString();

                presenter.updateFaq(faqResponse.getId(), question, answer);
            }
        });

        dialog.setView(dialogView);
        dialog.show();
    }

    private void showDialogDelet() {
        Button btnDeletDialog;

        AlertDialog.Builder dialogDelete;
        dialogDelete = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogDeleteView = inflater.inflate(R.layout.dialog_delete_faq, null);

        btnDeletDialog = dialogDeleteView.findViewById(R.id.btn_delete_faq_admin);

        btnDeletDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.deleteFAQ(faqResponse.getId());
            }
        });

        dialogDelete.setView(dialogDeleteView);
        dialogDelete.show();

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
