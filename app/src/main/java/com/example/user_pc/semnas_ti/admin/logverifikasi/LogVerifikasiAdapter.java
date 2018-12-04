package com.example.user_pc.semnas_ti.admin.logverifikasi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.bantuan.DateFormated;
import com.example.user_pc.semnas_ti.model.LogVerifikasiResponse;

import java.util.List;

public class LogVerifikasiAdapter extends RecyclerView.Adapter<LogVerifikasiAdapter.ViewHolder> {
    private Context context;
    private List<LogVerifikasiResponse> logVerifikasiResponses;

    public LogVerifikasiAdapter(Context context, List<LogVerifikasiResponse> logVerifikasiResponses) {
        this.context = context;
        this.logVerifikasiResponses = logVerifikasiResponses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_log_verif, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        LogVerifikasiResponse logVerifikasiResponse = logVerifikasiResponses.get(i);
        viewHolder.bind(logVerifikasiResponse);
    }

    @Override
    public int getItemCount() {
        return logVerifikasiResponses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvEmail, tvTanggal, tvStatus;
        ImageView imStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_log_verif_admin);
            tvEmail = itemView.findViewById(R.id.tv_log_verif_email);
            tvTanggal = itemView.findViewById(R.id.tv_log_verif_created);
            tvStatus = itemView.findViewById(R.id.tv_log_verif_status);
            imStatus = itemView.findViewById(R.id.icon_log_verif_status);
        }

        public void bind(LogVerifikasiResponse logVerifikasiResponse) {
            String tanggal = DateFormated.setDate(logVerifikasiResponse.getUpdatedAt());
            tvName.setText(logVerifikasiResponse.getName());
            tvEmail.setText(logVerifikasiResponse.getBookingEmail());
            tvTanggal.setText(tanggal);
            if (logVerifikasiResponse.getStatus()==1){
                tvStatus.setText("Terkirim");
                imStatus.setImageResource(R.drawable.ic_check);
            }else {
                tvStatus.setText("Belum");
                imStatus.setImageResource(R.drawable.ic_close);
            }
        }
    }
}
