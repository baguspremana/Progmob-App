package com.example.user_pc.semnas_ti.user.detailticket;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.api.ApiClient;
import com.example.user_pc.semnas_ti.api.ApiService;
import com.example.user_pc.semnas_ti.bantuan.ConstantURL;
import com.example.user_pc.semnas_ti.bantuan.CurrencyFormated;
import com.example.user_pc.semnas_ti.bantuan.DateFormated;
import com.example.user_pc.semnas_ti.model.Response;
import com.example.user_pc.semnas_ti.model.Ticket;
import com.example.user_pc.semnas_ti.user.UserActivity;
import com.example.user_pc.semnas_ti.user.ticket.TiketUserFragment;

import es.dmoral.toasty.Toasty;

public class DetailTicketActivity extends AppCompatActivity implements View.OnClickListener, DetailTicketView {
    public static final String KEY_TICKET = "ticket";

    TextView tvNama, tvEmail, tvContact, tvPrice, tvTanggal, tvStatus;
    ImageView imgQR;
    ImageButton btnEdit, btnDelete;

    Ticket ticket;

    DetailTicketPresenter presenter;
    ApiService service;

    int veget = 0;

    RadioGroup rgKonsumsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ticket);
        service = ApiClient.getService(this);
        presenter = new DetailTicketPresenter(service, this);

        init();
        setView();

        getSupportActionBar().hide();
    }

    private void init() {
        ticket = getIntent().getParcelableExtra(KEY_TICKET);

        presenter = new DetailTicketPresenter(ApiClient.getService(this), this);

        imgQR = findViewById(R.id.img_qr_code);
        btnEdit = findViewById(R.id.edit_booking);
        btnDelete = findViewById(R.id.delete_booking);

        tvNama = findViewById(R.id.tv_detail_booking_name);
        tvEmail = findViewById(R.id.tv_detail_booking_email);
        tvContact = findViewById(R.id.tv_detail_booking_contact);
        tvPrice = findViewById(R.id.tv_detail_booking_price);
        tvTanggal = findViewById(R.id.tv_detail_booking_created_at);
        tvStatus = findViewById(R.id.tv_detail_booking_status);

        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    private void setView() {
        String ticketPrice = CurrencyFormated.toRupiah(ticket.getBookingPrice());
        String tanggal = DateFormated.formatDate(ticket.getCreatedAt());
        tvNama.setText(ticket.getBookingName());
        tvEmail.setText(ticket.getBookingEmail());
        tvContact.setText(ticket.getBookingContact());
        tvPrice.setText(ticketPrice);
        tvTanggal.setText(tanggal);
        if (ticket.getStatus() == 2) {
            tvStatus.setText("Terverifikasi");
            btnDelete.setVisibility(View.GONE);
            btnEdit.setVisibility(View.GONE);
        } else {
            tvStatus.setText("Belum Terverifikasi");
            btnDelete.setVisibility(View.VISIBLE);
        }

        Glide.with(this).load(ConstantURL.URL.qrCode(ticket.getQrcodePhoto())).into(imgQR);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_booking:
                showDialog();
                break;

            case R.id.delete_booking:
                showDialogDelete();
                break;
        }
    }

    private void showDialogDelete() {
        Button btnDeletDialog;

        AlertDialog.Builder dialogDelete;
        dialogDelete = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogDeleteView = inflater.inflate(R.layout.dialog_delete, null);

        btnDeletDialog = dialogDeleteView.findViewById(R.id.btn_delete_booking_ticket);

        btnDeletDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.deleteBooking(ticket.getId());
            }
        });

        dialogDelete.setView(dialogDeleteView);

        dialogDelete.show();
    }

    private void showDialog() {
        Button btnEditDialog;
        final EditText etBookingName, etBookingEmail, etBookingContact, etInstansi;

        AlertDialog.Builder dialog;
        dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit, null);

        service = ApiClient.getService(this);
        presenter = new DetailTicketPresenter(service, this);

        btnEditDialog = dialogView.findViewById(R.id.btn_edit_booking_ticket);
        etBookingName = dialogView.findViewById(R.id.et_edit_booking_name);
        etBookingEmail = dialogView.findViewById(R.id.et_edit_booking_email);
        etBookingContact = dialogView.findViewById(R.id.et_edit_booking_contact);
        etInstansi = dialogView.findViewById(R.id.et_edit_instansi);
        rgKonsumsi = dialogView.findViewById(R.id.rg_edit_konsum);

        etBookingName.setText(ticket.getBookingName());
        etBookingEmail.setText(ticket.getBookingEmail());
        etBookingContact.setText(ticket.getBookingContact());
        etInstansi.setText(ticket.getBookingInstitution());
        if (ticket.getBookingVeget() == 1) {
            ((RadioButton)rgKonsumsi.getChildAt(0)).setChecked(true);
        } else if (ticket.getBookingVeget() == 0) {
            ((RadioButton)rgKonsumsi.getChildAt(1)).setChecked(true);
        }

        btnEditDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookingName = etBookingName.getText().toString();
                String bookingEmail = etBookingEmail.getText().toString();
                String bookingContact = etBookingContact.getText().toString();
                String bookingInstansi = etInstansi.getText().toString();
                rgKonsumsi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (checkedId == R.id.rb_edit_veget) {
                            veget = 1;
                        } else if (checkedId == R.id.rb_edit_non_veget) {
                            veget = 0;
                        }
                    }
                });

                presenter.updateBooking(ticket.getId(), bookingName, bookingEmail, bookingContact, veget, bookingInstansi);
            }
        });

        dialog.setView(dialogView);

        dialog.show();
    }

    @Override
    public void onSuccess(Response response) {
        Intent intent = new Intent(DetailTicketActivity.this,UserActivity.class);
        intent.putExtra("fragment",TiketUserFragment.class);
        startActivity(intent);
        finish();
        Toasty.success(this, "Success", Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void onError() {
        Toasty.warning(this, "Response Failed", Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void onFailure(Throwable t) {
        Toasty.error(this, "Error", Toast.LENGTH_SHORT, true).show();
    }
}
