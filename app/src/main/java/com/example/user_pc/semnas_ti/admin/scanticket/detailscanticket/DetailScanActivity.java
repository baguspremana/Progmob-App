package com.example.user_pc.semnas_ti.admin.scanticket.detailscanticket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.model.PesertaResponse;

public class DetailScanActivity extends AppCompatActivity {
    public static final String KEY_PESERTA = "pesertaResponse";

    TextView tvName, tvEmail, tvKontak, tvKonsumsi, tvInstansi;
    ImageView imageView;

    PesertaResponse pesertaResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_scan);

        getSupportActionBar().hide();
        init();
        setView();
    }

    private void setView() {
        tvName.setText(pesertaResponse.getBookingName());
        tvEmail.setText(pesertaResponse.getBookingEmail());
        tvKontak.setText(pesertaResponse.getBookingContact());
        tvInstansi.setText(pesertaResponse.getBookingInstitution());
        imageView.setImageResource(R.drawable.user_icon);
        if (pesertaResponse.getBookingVeget()==0){
            tvKonsumsi.setText("Non Veget");
        }else {
            tvKonsumsi.setText("Veget");
        }
    }

    private void init() {
        pesertaResponse = getIntent().getParcelableExtra(KEY_PESERTA);

        imageView = findViewById(R.id.image_detail_data_peserta);
        tvName = findViewById(R.id.tv_detail_peserta_name);
        tvEmail = findViewById(R.id.tv_detail_email_peserta);
        tvKontak = findViewById(R.id.tv_detail_kontak_peserta);
        tvKonsumsi = findViewById(R.id.tv_detail_konsumsi_peserta);
        tvInstansi = findViewById(R.id.tv_detail_instansi_peserta);
    }
}
