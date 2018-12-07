package com.example.user_pc.semnas_ti.admin.profile.editprofile;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.admin.AdminActivity;
import com.example.user_pc.semnas_ti.admin.profile.ProfileAdminFragment;
import com.example.user_pc.semnas_ti.api.ApiClient;
import com.example.user_pc.semnas_ti.model.Profile;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileAdminActivity extends AppCompatActivity {
    public static final String KEY_PROFILE = "profile";

    EditText etName, etEmail, etKontak;
    TextView tvUpload;
    ImageView imProfile;
    Button btnSave;
    RadioGroup radioGroup;

    Profile profile;

    int userGender;

    private static final int IMG_REQUEST = 100;
    private Bitmap bitmap;
    MultipartBody.Part body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_admin);

        getSupportActionBar().hide();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        init();
        setView();
    }

    private void setView() {
        etName.setText(profile.getName());
        etEmail.setText(profile.getEmail());
        etKontak.setText(profile.getContact());
        if (profile.getGender()==1){
            ((RadioButton)radioGroup.getChildAt(1)).setChecked(true);
        }else {
            ((RadioButton)radioGroup.getChildAt(0)).setChecked(true);
        }
    }

    private void init() {
        profile = getIntent().getParcelableExtra(KEY_PROFILE);

        etName = findViewById(R.id.et_edit_name);
        etEmail = findViewById(R.id.et_edit_email);
        etKontak = findViewById(R.id.et_edit_kontak);

        tvUpload = findViewById(R.id.tv_upload_foto_profile);
        imProfile = findViewById(R.id.edit_profile_image);

        btnSave = findViewById(R.id.save_profile);
        radioGroup = findViewById(R.id.rg_edit_gender);

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

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpan();
            }
        });
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

        if (requestCode==IMG_REQUEST && resultCode==RESULT_OK&&data!=null){
            Uri selectedImage=data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                imProfile.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String filePath = getRealPathFromURI_API19(this,selectedImage);
            File fileImg = new File(filePath);

            RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), fileImg);

            body = MultipartBody.Part.createFormData("photo_profile", fileImg.getName(), reqFile);
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
                        Toast.makeText(this, "permision denied", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
            }
        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void simpan() {
        RequestBody name = RequestBody.create(okhttp3.MultipartBody.FORM, etName.getText().toString());
        RequestBody email = RequestBody.create(okhttp3.MultipartBody.FORM, etEmail.getText().toString());
        RequestBody contact = RequestBody.create(okhttp3.MultipartBody.FORM, etKontak.getText().toString());
        int selectedID = radioGroup.getCheckedRadioButtonId();

        if ( selectedID == R.id.rb_edit_female){
            userGender = 0;
        }else if (selectedID == R.id.rb_edit_male){
            userGender = 1;
        }
        RequestBody gender = RequestBody.create(MultipartBody.FORM, String.valueOf(userGender));
        ApiClient.getService(this)
                .saveProfile(name, email, body, contact, gender)
                .enqueue(new Callback<Profile>() {
                    @Override
                    public void onResponse(Call<Profile> call, Response<Profile> response) {
                        if (response.isSuccessful()){
                            Intent intent = new Intent(EditProfileAdminActivity.this, AdminActivity.class);
                            intent.putExtra("profile", ProfileAdminFragment.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(EditProfileAdminActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(EditProfileAdminActivity.this, "Response Failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Profile> call, Throwable t) {
                        Toast.makeText(EditProfileAdminActivity.this, "Error"+t, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
