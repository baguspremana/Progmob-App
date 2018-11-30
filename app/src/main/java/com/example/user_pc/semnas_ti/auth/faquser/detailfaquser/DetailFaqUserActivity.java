package com.example.user_pc.semnas_ti.auth.faquser.detailfaquser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.user_pc.semnas_ti.R;

public class DetailFaqUserActivity extends AppCompatActivity {
    public static final String KEY_FAQ = "faqUserResponse";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_faq_user);
    }
}
