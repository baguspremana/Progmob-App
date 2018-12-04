package com.example.user_pc.semnas_ti.auth.faquser.detailfaquser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.bantuan.DateFormated;
import com.example.user_pc.semnas_ti.model.FaqUserResponse;

public class DetailFaqUserActivity extends AppCompatActivity {
    public static final String KEY_FAQ = "faqUserResponse";

    TextView tvQuestion, tvAnswer, tvUpdated;

    FaqUserResponse faqUserResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_faq_user);

        getSupportActionBar().hide();

        init();
        setView();
    }

    private void setView() {
        String tanggal = DateFormated.setDate(faqUserResponse.getUpdatedAt());
        tvQuestion.setText(faqUserResponse.getQuestion());
        tvAnswer.setText(faqUserResponse.getAnswer());
        tvUpdated.setText(tanggal);
    }

    private void init() {
        faqUserResponse = getIntent().getParcelableExtra(KEY_FAQ);

        tvQuestion = findViewById(R.id.tv_detail_question_user);
        tvAnswer = findViewById(R.id.tv_detail_answer_user);
        tvUpdated = findViewById(R.id.tv_detail_faq_update_user);
    }
}
