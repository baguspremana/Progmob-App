package com.example.user_pc.semnas_ti.user.detailticketpayment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.api.ApiClient;
import com.example.user_pc.semnas_ti.api.ApiService;
import com.example.user_pc.semnas_ti.bantuan.CurrencyFormated;
import com.example.user_pc.semnas_ti.bantuan.DateFormated;
import com.example.user_pc.semnas_ti.model.Response;
import com.example.user_pc.semnas_ti.model.TicketPayment;
import com.example.user_pc.semnas_ti.user.UserActivity;
import com.example.user_pc.semnas_ti.user.ticketpayment.BayarTiketFragment;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class DetailTicketPaymentActivity extends AppCompatActivity {
    public static final String KEY_TICKET_PAYMENT = "ticketPayment";

    TextView tvJumlah, tvTotalHarga, tvTanggalPesan, tvTanggalUpdate, tvStatusPayment, tvUpload;
    EditText etEtc;
    ImageView imgPayment;
    ImageButton btnUpload, btnDel;

    TicketPayment ticketPayment;

    ApiService service;

    private static final int IMG_REQUEST=777;
    private Bitmap bitmap;
    MultipartBody.Part body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ticket_payment);
        init();
        setView();

        getSupportActionBar().hide();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void init() {
        ticketPayment = getIntent().getParcelableExtra(KEY_TICKET_PAYMENT);

        tvJumlah = findViewById(R.id.tv_detail_ticket_jumlah);
        tvTotalHarga = findViewById(R.id.tv_detail_ticket_sum_payment);
        tvTanggalPesan = findViewById(R.id.tv_detail_payment_pesan);
        tvTanggalUpdate = findViewById(R.id.tv_detail_payment_update);
        tvStatusPayment = findViewById(R.id.tv_detail_payment_status);
        tvUpload = findViewById(R.id.tv_upload);
        imgPayment = findViewById(R.id.payment_image);
        btnUpload = findViewById(R.id.upload_payment);
        btnDel = findViewById(R.id.delete_payment);
        etEtc = findViewById(R.id.et_etc);

        tvUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkStorePermission()){
                        selectImage();
                    }
                }else {
                    selectImage();
                }
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(DetailTicketPaymentActivity.this, "Test", Toast.LENGTH_SHORT).show();
                showDialog();
            }
        });

    }

    private void setView() {
        tvJumlah.setText(String.valueOf(ticketPayment.getJumlahTicket()));
        String ticketPrice = CurrencyFormated.toRupiah(Integer.parseInt(ticketPayment.getTotalHarga()));
        String tanggalPesan = DateFormated.formatDate(ticketPayment.getCreatedAt());
        String tanggalUpdate = DateFormated.formatDate(ticketPayment.getUpdatedAt());
        tvTotalHarga.setText(ticketPrice);
        tvTanggalPesan.setText(tanggalPesan);
        tvTanggalUpdate.setText(tanggalUpdate);
        if (ticketPayment.getStatus()==2){
            tvStatusPayment.setText("Terverifikasi");
        }else {
            tvStatusPayment.setText("Belum Terverifikasi");
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_STORAGE= 1;
    private boolean checkStorePermission() {
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(this,
                 new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_STORAGE);
            }else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_STORAGE);
            }
            return false;
        }else {
            return true;
        }
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==IMG_REQUEST && resultCode==RESULT_OK&&data!=null){
            Uri selectedImage=data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                imgPayment.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String wholeID = DocumentsContract.getDocumentId(selectedImage);

            String id = wholeID.split(":")[1];

            String[] column = {MediaStore.Images.Media.DATA};

            String sel = MediaStore.Images.Media._ID+ "=?";

            Cursor cursor = getContentResolver()
                    .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            column, sel, new String[]{id}, null);

            String filePath = "";

            int columnIndex = cursor.getColumnIndex(column[0]);

            if (cursor.moveToFirst()){
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
            File file = new File(filePath);

            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);

            body = MultipartBody.Part.createFormData("photo", file.getName(), reqFile);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_STORAGE: {
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_GRANTED){

                        selectImage();
                    }else {
                        Toast.makeText(this, "permision denied", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
            }
        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void upload() {
        RequestBody etc = RequestBody.create(okhttp3.MultipartBody.FORM, etEtc.getText().toString());
        ApiClient.getService(this)
                .addPayment(ticketPayment.getId(), body, etc)
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        if (response.isSuccessful()){
                            Intent intent = new Intent(DetailTicketPaymentActivity.this, UserActivity.class);
                            intent.putExtra("payment", BayarTiketFragment.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(DetailTicketPaymentActivity.this, "Sukses", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(DetailTicketPaymentActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        Toast.makeText(DetailTicketPaymentActivity.this, "Error"+t, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showDialog() {
        Button deleteDialog;
        service = ApiClient.getService(this);

        AlertDialog.Builder dialog;
        dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_delete_master, null);

        deleteDialog = dialogView.findViewById(R.id.btn_delete_master_ticket);

        deleteDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service
                        .deleteMaster(ticketPayment.getId())
                        .enqueue(new Callback<Response>() {
                            @Override
                            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                                if (response.isSuccessful()){
                                    Intent intent = new Intent(DetailTicketPaymentActivity.this, UserActivity.class);
                                    intent.putExtra("payment", BayarTiketFragment.class);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(DetailTicketPaymentActivity.this, "Sukses", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(DetailTicketPaymentActivity.this, "Response Failed", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Response> call, Throwable t) {
                                Toast.makeText(DetailTicketPaymentActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        dialog.setView(dialogView);
        dialog.show();
    }
}
