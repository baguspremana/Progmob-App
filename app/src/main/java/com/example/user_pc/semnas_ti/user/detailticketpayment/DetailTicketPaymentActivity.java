package com.example.user_pc.semnas_ti.user.detailticketpayment;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;
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

    private static final int IMG_REQUEST = 100;
    private Bitmap bitmap;
    MultipartBody.Part body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ticket_payment);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);

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
            btnDel.setVisibility(View.GONE);
        }else {
            tvStatusPayment.setText("Belum Terverifikasi");
            btnDel.setVisibility(View.VISIBLE);
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
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data!= null && data.getData() != null){
            Uri selectedImage = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                imgPayment.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String filePath = getRealPathFromURI_API19(this,selectedImage);
            File fileImg = new File(filePath);

            RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), fileImg);

            body = MultipartBody.Part.createFormData("photo", fileImg.getName(), reqFile);
        }
    }

    private String getRealPathFromURI_API19(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)){

            if (isExternalStorageDocument(uri)){
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)){
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }

            else if (isDownloadsDocument(uri)){
                Cursor cursor = null;

                try {
                    String[] s ={MediaStore.MediaColumns.DISPLAY_NAME};
                    cursor = context.getContentResolver().query(uri,s,null,null,null);
                    String filename = cursor.getString(0);
                    String path = Environment.getExternalStorageDirectory().toString()+"/Download/"+filename;
                    if (!TextUtils.isEmpty(path)){
                        return path;
                    }
                }finally {
                    cursor.close();
                }

                String id = DocumentsContract.getDocumentId(uri);
                if (id.startsWith("raw:")){
                    return id.replaceFirst(Pattern.quote("raw:"), "");
                }
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }

            else if (isMediaDocument(uri)){
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
                
            }
        }

        else if ("content".equalsIgnoreCase(uri.getScheme())){
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }

        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;

    }

    private String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    private boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
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
                        Toasty.error(this, "Permission Denied", Toast.LENGTH_SHORT, true).show();
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
                            Toasty.success(DetailTicketPaymentActivity.this, "Success", Toast.LENGTH_SHORT, true).show();
                        }else {
                            Toasty.warning(DetailTicketPaymentActivity.this, "Response Failed", Toast.LENGTH_SHORT, true).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        Toasty.error(DetailTicketPaymentActivity.this, "Error", Toast.LENGTH_SHORT, true).show();
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
                                    Toasty.success(DetailTicketPaymentActivity.this, "Success", Toast.LENGTH_SHORT, true).show();
                                }else {
                                    Toasty.warning(DetailTicketPaymentActivity.this, "Response Failed", Toast.LENGTH_SHORT, true).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Response> call, Throwable t) {
                                Toasty.error(DetailTicketPaymentActivity.this, "Error", Toast.LENGTH_SHORT, true).show();
                            }
                        });
            }
        });

        dialog.setView(dialogView);
        dialog.show();
    }
}
