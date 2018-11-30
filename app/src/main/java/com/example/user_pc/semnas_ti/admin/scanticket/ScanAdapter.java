package com.example.user_pc.semnas_ti.admin.scanticket;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.model.PesertaResponse;

import java.util.List;

public class ScanAdapter extends RecyclerView.Adapter<ScanAdapter.ViewHolder> {
    private Context context;
    private OnClickListener onClickListener;
    private List<PesertaResponse> pesertaResponses;

    public ScanAdapter(Context context, List<PesertaResponse> pesertaResponses) {
        this.context = context;
        this.pesertaResponses = pesertaResponses;
    }

    public interface OnClickListener{
        void onClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_data_peserta, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        PesertaResponse pesertaResponse = pesertaResponses.get(i);
        viewHolder.bind(pesertaResponse);
    }

    @Override
    public int getItemCount() {
        return pesertaResponses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvEmail, tvKontak, tvKonsumsi;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_nama_peserta);
            tvEmail = itemView.findViewById(R.id.tv_email_peserta);
            tvKontak = itemView.findViewById(R.id.tv_kontak_peserta);
            tvKonsumsi = itemView.findViewById(R.id.tv_konsum_peserta);
            imageView = itemView.findViewById(R.id.iv_data_peserta);

            if (onClickListener != null){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickListener.onClick(getAdapterPosition());
                    }
                });
            }
        }

        public void bind(PesertaResponse pesertaResponse) {
            tvName.setText(pesertaResponse.getBookingName());
            tvEmail.setText(pesertaResponse.getBookingEmail());
            tvKontak.setText(pesertaResponse.getBookingContact());
            if (pesertaResponse.getBookingVeget()==0){
                tvKonsumsi.setText("Non Veget");
            }else {
                tvKonsumsi.setText("Veget");
            }
            imageView.setImageResource(R.drawable.user_icon);
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
