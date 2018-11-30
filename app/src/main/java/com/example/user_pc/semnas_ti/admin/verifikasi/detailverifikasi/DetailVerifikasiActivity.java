package com.example.user_pc.semnas_ti.admin.verifikasi.detailverifikasi;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.admin.AdminActivity;
import com.example.user_pc.semnas_ti.admin.verifikasi.VerifikasiFragment;
import com.example.user_pc.semnas_ti.api.ApiClient;
import com.example.user_pc.semnas_ti.bantuan.ConstantURL;
import com.example.user_pc.semnas_ti.bantuan.CurrencyFormated;
import com.example.user_pc.semnas_ti.bantuan.DateFormated;
import com.example.user_pc.semnas_ti.model.Response;
import com.example.user_pc.semnas_ti.model.TicketPaymentAdmin;

public class DetailVerifikasiActivity extends AppCompatActivity implements View.OnClickListener, DetailVerifikasiView {
    public static final String KEY_TICKET = "ticketPaymentAdmin";

    TextView tvName, tvEmail, tvJumlah, tvHarga, tvTanggal, tvUpdate, tvStatus;
    ImageView imVerif;
    ImageButton imbVerif, imbSend, imbCancel;

    TicketPaymentAdmin ticketPaymentAdmin;

    DetailVerifikasiPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_verifikasi);

        getSupportActionBar().hide();

        init();
        setView();

        presenter = new DetailVerifikasiPresenter(this, ApiClient.getService(this));
    }

    private void init() {
        ticketPaymentAdmin = getIntent().getParcelableExtra(KEY_TICKET);

        tvName = findViewById(R.id.tv_detail_verif_name);
        tvEmail = findViewById(R.id.tv_detail_verif_email);
        tvJumlah = findViewById(R.id.tv_detail_verif_tiket);
        tvHarga = findViewById(R.id.tv_detail_verif_price);
        tvTanggal = findViewById(R.id.tv_detail_verif_created_at);
        tvUpdate = findViewById(R.id.tv_detail_verif_updated_at);
        tvStatus = findViewById(R.id.tv_detail_verif_status);

        imVerif = findViewById(R.id.img_verif);
        imbVerif = findViewById(R.id.btn_verif);
        imbSend = findViewById(R.id.send_notif);
        imbCancel = findViewById(R.id.send_notif_cancel);

        imbVerif.setOnClickListener(this);
        imbSend.setOnClickListener(this);
        imbCancel.setOnClickListener(this);
    }

    private void setView(){
        String harga = CurrencyFormated.toRupiah(Integer.parseInt(ticketPaymentAdmin.getTotalHarga()));
        String tanggal = DateFormated.setDate(ticketPaymentAdmin.getCreatedAt());
        String update = DateFormated.setDate(ticketPaymentAdmin.getUpdatedAt());
        tvName.setText(ticketPaymentAdmin.getName());
        tvEmail.setText(ticketPaymentAdmin.getEmail());
        tvJumlah.setText(String.valueOf(ticketPaymentAdmin.getJumlahTicket()));
        tvHarga.setText(harga);
        tvTanggal.setText(tanggal);
        tvUpdate.setText(update);
        if (ticketPaymentAdmin.getStatus()==2){
            tvStatus.setText("Terverifikasi");
        }else {
            tvStatus.setText("Belum Terverifikasi");
        }

        if (ticketPaymentAdmin.getPhoto()==null){
            imVerif.setImageResource(R.drawable.bg_gradient);
        }else {
            Glide.with(this).load(ConstantURL.URL.imgPhoto(ticketPaymentAdmin.getPhoto())).into(imVerif);
        }

        if (ticketPaymentAdmin.getStatus()==2){
            imbVerif.setVisibility(View.GONE);
            imbSend.setVisibility(View.GONE);
            imbCancel.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_verif:
                showdialog();
                break;
            case R.id.send_notif:
                sendNotif();
                break;
            case R.id.send_notif_cancel:
                sendCancelNotif();
                break;
        }
    }

    private void showdialog(){
        Button btnDialog;

        AlertDialog.Builder dialog;
        dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogVerif = inflater.inflate(R.layout.dialog_verif, null);

        btnDialog = dialogVerif.findViewById(R.id.btn_verif_dialog);

        btnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.verifTicket(ticketPaymentAdmin.getId());
            }
        });

        dialog.setView(dialogVerif);
        dialog.show();
    }

    private void sendNotif(){
        Button btnDialogSend;

        AlertDialog.Builder dialog;
        dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_send_notif, null);

        btnDialogSend = dialogView.findViewById(R.id.btn_send_notif_dialog);

        btnDialogSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.sendNotif(ticketPaymentAdmin.getUserId());
            }
        });

        dialog.setView(dialogView);
        dialog.show();
    }

    private void sendCancelNotif() {
        Button btnDialog;

        AlertDialog.Builder dialog;
        dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_send_cancel_notif, null);

        btnDialog = dialogView.findViewById(R.id.btn_send_cancel_dialog);

        btnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.sendCancelNotif(ticketPaymentAdmin.getUserId());
            }
        });

        dialog.setView(dialogView);
        dialog.show();
//        Toast.makeText(this, "Kirim Notif Tolak", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(Response response) {
        Intent intent = new Intent(this, AdminActivity.class);
        intent.putExtra("verifikasi", VerifikasiFragment.class);
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
