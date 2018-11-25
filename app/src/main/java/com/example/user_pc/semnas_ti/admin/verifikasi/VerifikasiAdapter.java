package com.example.user_pc.semnas_ti.admin.verifikasi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.bantuan.CurrencyFormated;
import com.example.user_pc.semnas_ti.bantuan.DateFormated;
import com.example.user_pc.semnas_ti.model.TicketPaymentAdmin;

import java.util.List;

public class VerifikasiAdapter extends RecyclerView.Adapter<VerifikasiAdapter.ViewHolder> {
    private Context context;
    private OnClickListener onClickListener;
    private List<TicketPaymentAdmin> ticketPaymentAdmins;

    public VerifikasiAdapter(Context context, List<TicketPaymentAdmin> ticketPaymentAdmins) {
        this.context = context;
        this.ticketPaymentAdmins = ticketPaymentAdmins;
    }

    public interface OnClickListener{
        void onClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_verifikasi, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        TicketPaymentAdmin ticketPaymentAdmin = ticketPaymentAdmins.get(i);
        viewHolder.bind(ticketPaymentAdmin);
    }

    @Override
    public int getItemCount() {
        return ticketPaymentAdmins.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvJumlah, tvNama, tvEmail, tvTotal, tvUpdate, tvStatus;
        ImageView imgStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            tvJumlah = itemView.findViewById(R.id.tv_list_ticket_verifikasi);
            tvNama = itemView.findViewById(R.id.tv_verifikasi_name);
            tvEmail = itemView.findViewById(R.id.tv_verifikasi_email);
            tvTotal = itemView.findViewById(R.id.tv_verifikasi_price);
            tvUpdate = itemView.findViewById(R.id.tv_verifikasi_tgl);
            tvStatus = itemView.findViewById(R.id.tv_verifikasi_status);
            imgStatus = itemView.findViewById(R.id.icon_verifikasi_status);

            if (onClickListener != null){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickListener.onClick(getAdapterPosition());
                    }
                });
            }
        }

        public void bind(TicketPaymentAdmin ticketPaymentAdmin) {
            String total = CurrencyFormated.toRupiah(Integer.parseInt(ticketPaymentAdmin.getTotalHarga()));
            String update = DateFormated.formatDate(ticketPaymentAdmin.getUpdatedAt());
            tvJumlah.setText(String.valueOf(ticketPaymentAdmin.getJumlahTicket()));
            tvNama.setText(ticketPaymentAdmin.getName());
            tvEmail.setText(ticketPaymentAdmin.getEmail());
            tvTotal.setText(total);
            tvUpdate.setText(update);
            if (ticketPaymentAdmin.getStatus()==2){
                tvStatus.setText("Verif");
                imgStatus.setImageResource(R.drawable.ic_check);
            }else {
                tvStatus.setText("Belum");
                imgStatus.setImageResource(R.drawable.ic_close);
            }
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
