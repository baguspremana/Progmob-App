package com.example.user_pc.semnas_ti.user.addticket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.api.ApiClient;
import com.example.user_pc.semnas_ti.api.ApiService;
import com.example.user_pc.semnas_ti.model.Response;
import com.example.user_pc.semnas_ti.user.UserActivity;
import com.example.user_pc.semnas_ti.user.ticket.TiketUserFragment;

import es.dmoral.toasty.Toasty;

public class AddTicketAcitivity extends AppCompatActivity implements View.OnClickListener, AddTicketView {

    EditText etNama, etEmail, etContact, etInstansi;

    RadioGroup radioGroup;

    int veget = 0;

    Button btnAdd;

    AddTicketPresenter presenter;
    ApiService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ticket);

        service = ApiClient.getService(this);
        presenter = new AddTicketPresenter(service, this);

        getSupportActionBar().hide();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        init();
    }

    private void init() {
        etNama = findViewById(R.id.et_booking_name);
        etEmail = findViewById(R.id.et_booking_email);
        etContact = findViewById(R.id.et_booking_contact);
        etInstansi = findViewById(R.id.et_booking_instansi);
        radioGroup = findViewById(R.id.rg_booking_konsum);
        btnAdd = findViewById(R.id.add_ticket);

        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_ticket:
                addTicket();
        }
    }

    private void addTicket() {
        String bookingName = etNama.getText().toString();
        String bookingEmail = etEmail.getText().toString();
        String bookingContact = etContact.getText().toString();
        String bookingInstitution = etInstansi.getText().toString();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.rb_veget){
                    veget = 1;
                }else if (checkedId==R.id.rb_non_veget){
                    veget = 0;
                }
            }
        });

        if (validate(bookingName, bookingEmail, bookingContact, bookingInstitution)){
            presenter.addBookingTicket(bookingName, bookingEmail, bookingContact, veget, bookingInstitution);
        }
    }

    public boolean validate(String name, String email, String contact, String instansi){
        if (name.equals("")){
            etNama.setError("Field Nama Tidak Boleh Kosong");
            return false;
        }

        if (email.equals("")){
            etEmail.setError("Field E-Mail Tidak Boleh Kosong");
            return false;
        }

        if (contact.equals("")){
            etContact.setError("Field Kontak Tidak Boleh Kosong");
            return false;
        }

        if (instansi.equals("")){
            etInstansi.setError("Field Instansi Tidak Boleh Kosong");
            return false;
        }

        return true;
    }

    @Override
    public void OnSuccess(Response response) {
        etNama.setText("");
        etEmail.setText("");
        etContact.setText("");
        etInstansi.setText("");
        radioGroup.clearCheck();
        Intent intent = new Intent(AddTicketAcitivity.this, UserActivity.class);
        intent.putExtra("fragment", TiketUserFragment.class);
        startActivity(intent);
        finish();
        Toasty.success(this, "Success", Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void OnError() {
        Toasty.warning(this, "Response Failed", Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void OnFailure(Throwable t) {
        Toasty.error(this, "Error", Toast.LENGTH_SHORT, true).show();
    }
}
