package com.example.user_pc.semnas_ti.admin.dataadmin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.bantuan.ConstantURL;
import com.example.user_pc.semnas_ti.model.DataAdmin;

import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder> {
    private Context context;
    private OnClickListener onClickListener;
    private List<DataAdmin> dataAdminList;

    public AdminAdapter(Context context, List<DataAdmin> dataAdminList) {
        this.context = context;
        this.dataAdminList = dataAdminList;
    }

    public interface OnClickListener{
        void onClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_data_admin, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        DataAdmin dataAdmin = dataAdminList.get(i);
        viewHolder.bind(dataAdmin);


    }

    @Override
    public int getItemCount() {
        return dataAdminList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvEmail, tvContact, tvGender;
        ImageView imgAdmin;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_nama_admin);
            tvEmail = itemView.findViewById(R.id.tv_email_admin);
            tvContact = itemView.findViewById(R.id.tv_kontak_admin);
            tvGender = itemView.findViewById(R.id.tv_gender_admin);
            imgAdmin = itemView.findViewById(R.id.iv_data_admin);

            if (onClickListener != null){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickListener.onClick(getAdapterPosition());
                    }
                });
            }
        }

        public void bind(DataAdmin dataAdmin) {
            tvName.setText(dataAdmin.getName());
            tvEmail.setText(dataAdmin.getEmail());
            tvContact.setText(String.valueOf(dataAdmin.getContact()));
            if (dataAdmin.getGender()==0){
                tvGender.setText("Perempuan");
            }else if (dataAdmin.getGender()==1){
                tvGender.setText("Laki-laki");
            }

            if (dataAdmin.getPhotoProfile()==null){
                imgAdmin.setImageResource(R.drawable.user_icon);
            }else {
                Glide.with(context).load(ConstantURL.URL.imgUser(dataAdmin.getPhotoProfile())).into(imgAdmin);
            }
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
