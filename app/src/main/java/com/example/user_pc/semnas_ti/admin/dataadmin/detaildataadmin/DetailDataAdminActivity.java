package com.example.user_pc.semnas_ti.admin.dataadmin.detaildataadmin;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.bantuan.ConstantURL;
import com.example.user_pc.semnas_ti.model.DataAdmin;

public class DetailDataAdminActivity extends AppCompatActivity {
    public static final String KEY_ADMIN = "dataAdmin";

    TextView tvNameDetail, tvEmailDetail, tvContactDetail, tvGenderDetail;
    ImageView imAdminDetail;

    DataAdmin dataAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_data_admin);

        getSupportActionBar().hide();

        init();
        setView();
    }

    private void init() {
        dataAdmin = getIntent().getParcelableExtra(KEY_ADMIN);

        imAdminDetail = findViewById(R.id.image_detail_data_admin);
        tvNameDetail = findViewById(R.id.tv_detail_admin_name);
        tvEmailDetail = findViewById(R.id.tv_detail_data_admin_email);
        tvContactDetail = findViewById(R.id.tv_detail_data_admin_kontak);
        tvGenderDetail = findViewById(R.id.tv_detail_data_admin_gender);
    }

    private void setView(){
        tvNameDetail.setText(dataAdmin.getName());
        tvEmailDetail.setText(dataAdmin.getEmail());
        tvContactDetail.setText(String.valueOf(dataAdmin.getContact()));
        if (dataAdmin.getGender()==1){
            tvGenderDetail.setText("Laki-laki");
        }else if (dataAdmin.getGender()==0){
            tvGenderDetail.setText("Perempuan");
        }

        if (dataAdmin.getPhotoProfile()==null){
            imAdminDetail.setImageResource(R.drawable.user_icon);
        }else {
            Glide.with(this).load(ConstantURL.URL.imgUser(dataAdmin.getPhotoProfile())).into(imAdminDetail);
        }
    }
}
