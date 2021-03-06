package com.example.user_pc.semnas_ti.admin.infoseminar.addseminarinfo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.admin.AdminActivity;
import com.example.user_pc.semnas_ti.admin.infoseminar.InfoSeminarFragment;
import com.example.user_pc.semnas_ti.api.ApiClient;
import com.example.user_pc.semnas_ti.bantuan.DateFormated;
import com.example.user_pc.semnas_ti.model.Response;

public class AddSeminarActivity extends AppCompatActivity implements View.OnClickListener, AddSeminarView {
    EditText etSeminarName, etSeminarTheme, etSeminarDesc, etSeminarLocation, etTicket;
    Button btnAdd;
    TextView tvTanggal, tvBulan, tvTahun, tvWaktu;

    int year_x,month_x,day_x;

    String dateSeminar, waktuSeminar;
    AddSeminarPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_seminar);
        init();

        getSupportActionBar().hide();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        year_x=2018;
        day_x=11;
        month_x=9;
        waktuSeminar="10:00";
    }

    private void init() {
        presenter = new AddSeminarPresenter(this, ApiClient.getService(this));

        etSeminarName = findViewById(R.id.et_seminar_name);
        etSeminarTheme = findViewById(R.id.et_seminar_theme);
        etSeminarDesc = findViewById(R.id.et_seminar_desc);
        etSeminarLocation = findViewById(R.id.et_seminar_location);
        etTicket = findViewById(R.id.et_seminar_tiket);

        btnAdd = findViewById(R.id.btn_add_info_seminar);

        tvTanggal = findViewById(R.id.tv_tanggal_seminar);
        tvBulan = findViewById(R.id.tv_bulan_seminar);
        tvTahun = findViewById(R.id.tv_tahun_seminar);
        tvWaktu = findViewById(R.id.tv_waktu_seminar);

        btnAdd.setOnClickListener(this);
        tvTanggal.setOnClickListener(this);
        tvWaktu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_info_seminar:
                simpan();
                break;
            case R.id.tv_tanggal_seminar:
                showDatePickerDialog(tvTanggal, tvBulan, tvTahun);
                break;
            case R.id.tv_waktu_seminar:
                showTimeTicketDialog(tvWaktu);
                break;
        }

    }

    private void showTimeTicketDialog(final TextView tvWaktu) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time = hourOfDay+":"+DateFormated.dateValidation(minute);
                tvWaktu.setText(time);

                waktuSeminar = time;
            }
        }, 10, 0, true);

        timePickerDialog.show();
    }

    private void showDatePickerDialog(final TextView tvTanggal, final TextView tvBulan, final TextView tvTahun) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String strMonth = DateFormated.dateValidation(month+1);
                String strDayOfMonth = DateFormated.dateValidation(dayOfMonth);
                String strDate = year+"-"+DateFormated.dateValidation(month+1)+"-"+DateFormated.dateValidation(dayOfMonth);
                tvTahun.setText(String.valueOf(year));
                tvBulan.setText(DateFormated.getMonthName(strMonth));
                tvTanggal.setText(strDayOfMonth);

                dateSeminar = strDate;
            }
        }, year_x, month_x, day_x);
        datePickerDialog.show();
    }

    private void simpan() {
        String seminarName = etSeminarName.getText().toString();
        String seminarTheme = etSeminarTheme.getText().toString();
        String seminarDesc = etSeminarDesc.getText().toString();
        String seminarLoc = etSeminarLocation.getText().toString();
        int seminarTiket = Integer.parseInt(etTicket.getText().toString());
        String seminarSche = dateSeminar+" "+waktuSeminar;

        if (validate(seminarName, seminarTheme, seminarDesc, seminarLoc, seminarSche, seminarTiket)){
            presenter.addSeminarInfo(seminarName, seminarTheme, seminarDesc, seminarSche, seminarLoc, seminarTiket);
        }
    }

    public boolean validate(String name, String tema, String desc, String location, String tanggal, int tiket){
        if (name.equals("")){
            etSeminarName.setError("Field Tidak Boleh Kosong");
            return false;
        }

        if (tema.equals("")){
            etSeminarTheme.setError("Field Tidak Boleh Kosong");
            return false;
        }

        if (desc.equals("")){
            etSeminarDesc.setError("Field Tidak Boleh Kosong");
            return false;
        }

        if (location.equals("")){
            etSeminarLocation.setError("Field Tidak Boleh Kosong");
            return false;
        }

        if (tanggal.equals("")){
            tvTanggal.setError("Field Tidak Boleh Kosong");
            return false;
        }

        if (String.valueOf(tiket).equals("")){
            etTicket.setError("Field Tidak Boleh Kosong");
            return false;
        }
        return true;
    }

    @Override
    public void onSuccess(Response response) {
        Intent intent = new Intent(AddSeminarActivity.this, AdminActivity.class);
        intent.putExtra("tiket", InfoSeminarFragment.class);
        startActivity(intent);
        finish();
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError() {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(Throwable t) {
        Toast.makeText(this, "Failde"+t, Toast.LENGTH_SHORT).show();
    }
}
