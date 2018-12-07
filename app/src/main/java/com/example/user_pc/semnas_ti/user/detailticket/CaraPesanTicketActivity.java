package com.example.user_pc.semnas_ti.user.detailticket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.user_pc.semnas_ti.R;

import me.biubiubiu.justifytext.library.JustifyTextView;

public class CaraPesanTicketActivity extends AppCompatActivity {
    private JustifyTextView tvSatu, tvDua, tvTiga, tvEmpat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cara_pesan_ticket);

        getSupportActionBar().hide();

        init();
        setView();
    }

    private void setView() {
        tvSatu.setText("\nInput data pemesanan tiket sesuai dengan hal yang diperlukan dalam form pemesanan tiket\n");
        tvDua.setText("\nAnda dapat memesanan lebih dari satu tiket, tapi dengan data yang berbeda, sehingga memungkinkan anda" +
                " untuk memesankan tiket seminar untuk teman anda\n");
        tvTiga.setText("\nPastikan ALAMAT E-MAIL yang anda inputkan telah benar, karena invoice (bukti) pemesanan tiket, dan QR Code" +
                " tiket yang akan digunakan saat hari H seminar akan dikirimkan ke masing-masing ALAMAT E-MAIL pemesanan\n");
        tvEmpat.setText("\nSetelah anda selesai melakukan pemesanan anda dapat melakukan pembayaran tiket, sesuai dengan total tagihan yang" +
                " dapat anda lihat di menu BAYAR, dan pembayaran dapat ditransfer ke Rekening BNI Atas Nama dengan Nomor Rekening xxxxxxx, dan" +
                " bukti pembayaran dapat anda upload pada menu bayar, kemudian tunggu hingga pembayaran yang anda lakukan diverifikasi oleh Admin\n");
    }

    private void init() {
        tvSatu = findViewById(R.id.detail1);
        tvDua = findViewById(R.id.detail2);
        tvTiga = findViewById(R.id.detail3);
        tvEmpat = findViewById(R.id.detail4);
    }
}
