package com.example.user_pc.semnas_ti.user.detailticketpayment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.user_pc.semnas_ti.R;

public class DetailTicketPaymentActivity extends AppCompatActivity {
    public static final String KEY_TICKET_PAYMENT = "ticketPayment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ticket_payment);

        getSupportActionBar().hide();
    }
}
