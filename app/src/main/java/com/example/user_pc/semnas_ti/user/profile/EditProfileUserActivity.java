package com.example.user_pc.semnas_ti.user.profile;

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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.user_pc.semnas_ti.api.ApiClient;
import com.example.user_pc.semnas_ti.model.Profile;
import com.example.user_pc.semnas_ti.user.UserActivity;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileUserActivity extends AppCompatActivity {
    public static final String KEY_PROFILE = "profile";

    EditText etName, etEmail, etKontak;
    TextView tvUpload;
    ImageView imProfile;
    Button btnSave;
    RadioGroup radioGroup;

    Profile profile;

    int gender = 0;

    private static final int IMG_REQUEST = 777;
    private Bitmap bitmap;
    MultipartBody.Part body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_user);

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

        etName = findViewById(R.id.et_edit_name_user);
        etEmail = findViewById(R.id.et_edit_email_user);
        etKontak = findViewById(R.id.et_edit_kontak_user);

        tvUpload = findViewById(R.id.tv_upload_foto_profile_user);
        imProfile = findViewById(R.id.edit_profile_image_user);

        btnSave = findViewById(R.id.save_profile_user);
        radioGroup = findViewById(R.id.rg_edit_gender_user);

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
                imProfile.setImageBitmap(bitmap);
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

            body = MultipartBody.Part.createFormData("photo_profile", file.getName(), reqFile);
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

    private void simpan() {
        RequestBody name = RequestBody.create(okhttp3.MultipartBody.FORM, etName.getText().toString());
        RequestBody email = RequestBody.create(okhttp3.MultipartBody.FORM, etEmail.getText().toString());
        RequestBody contact = RequestBody.create(okhttp3.MultipartBody.FORM, etKontak.getText().toString());
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_edit_female_user){
                    gender = 0;
                }else if(checkedId == R.id.rb_edit_male_user){
                    gender = 1;
                }
            }
        });
        ApiClient.getService(this)
                .saveProfile(name, email, body, contact, gender)
                .enqueue(new Callback<Profile>() {
                    @Override
                    public void onResponse(Call<Profile> call, Response<Profile> response) {
                        if (response.isSuccessful()){
                            Intent intent = new Intent(EditProfileUserActivity.this, UserActivity.class);
                            intent.putExtra("profile", ProfileUserFragment.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(EditProfileUserActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(EditProfileUserActivity.this, "Response Failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Profile> call, Throwable t) {
                        Toast.makeText(EditProfileUserActivity.this, "Error"+t, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
