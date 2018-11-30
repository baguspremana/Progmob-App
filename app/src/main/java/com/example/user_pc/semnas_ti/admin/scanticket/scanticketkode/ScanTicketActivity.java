package com.example.user_pc.semnas_ti.admin.scanticket.scanticketkode;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.admin.AdminActivity;
import com.example.user_pc.semnas_ti.admin.scanticket.ScanFragment;
import com.example.user_pc.semnas_ti.api.ApiClient;
import com.example.user_pc.semnas_ti.model.ScanTicketResponse;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanTicketActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnScan;
    private TextView tvName, tvEmail, tvKontak, tvKonsumsi, tvInstansi;
    ScanTicketResponse scanTicketResponse;

    private IntentIntegrator intentIntegrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_ticket);

        btnScan = findViewById(R.id.btnScan);

        tvName = findViewById(R.id.tv_detail_name_scan);
        tvEmail = findViewById(R.id.tv_detail_email_scan);
        tvKontak = findViewById(R.id.tv_detail_kontak_scan);
        tvKonsumsi = findViewById(R.id.tv_detail_konsumsi_scan);
        tvInstansi = findViewById(R.id.tv_detail_instansi_scan);

        btnScan.setOnClickListener(this);

        getSupportActionBar().hide();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null){
            if (result.getContents() == null){
                Toast.makeText(this, "Hasil Tidak Ditemukan", Toast.LENGTH_SHORT).show();
            }else {
                try{
                    JSONObject object = new JSONObject(result.getContents());
                    tvName.setText(object.getString("kode"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    ApiClient.getService(this)
                            .scanTicket(result.getContents())
                            .enqueue(new Callback<ScanTicketResponse>() {
                                @Override
                                public void onResponse(Call<ScanTicketResponse> call, Response<ScanTicketResponse> response) {
                                    if (response.isSuccessful()){
                                        scanTicketResponse = response.body();
                                        tvName.setText(scanTicketResponse.getBookingName());
                                        tvEmail.setText(scanTicketResponse.getBookingEmail());
                                        tvKontak.setText(scanTicketResponse.getBookingContact());
                                        tvInstansi.setText(scanTicketResponse.getBookingInstitution());
                                        if (scanTicketResponse.getBookingVeget()==0){
                                            tvKonsumsi.setText("Non Veget");
                                        }else {
                                            tvKonsumsi.setText("Veget");
                                        }
                                    }else {
                                        Toast.makeText(ScanTicketActivity.this, "Response Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ScanTicketResponse> call, Throwable t) {
                                    Toast.makeText(ScanTicketActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View v) {
        intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.initiateScan();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent(ScanTicketActivity.this, AdminActivity.class);
        intent.putExtra("scan", ScanFragment.class);
        startActivity(intent);
        finish();
    }
}
