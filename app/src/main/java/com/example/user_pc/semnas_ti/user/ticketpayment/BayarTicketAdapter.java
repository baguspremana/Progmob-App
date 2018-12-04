package com.example.user_pc.semnas_ti.user.ticketpayment;

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
import com.example.user_pc.semnas_ti.bantuan.CurrencyFormated;
import com.example.user_pc.semnas_ti.bantuan.DateFormated;
import com.example.user_pc.semnas_ti.model.TicketPayment;

import java.util.Date;
import java.util.List;

public class BayarTicketAdapter extends RecyclerView.Adapter<BayarTicketAdapter.ViewHolder> {
    Context context;
    private OnClickListener onClickListener;
    private List<TicketPayment> ticketPayments;

    public BayarTicketAdapter(Context context, List<TicketPayment> ticketPayments) {
        this.context = context;
        this.ticketPayments = ticketPayments;
    }

    public interface OnClickListener{
        void onClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_bayar_tiket_user,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        TicketPayment ticketPayment = ticketPayments.get(i);
        viewHolder.bind(ticketPayment);
    }

    @Override
    public int getItemCount() {
        return ticketPayments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvJumlahTiket, tvTotalHarga, tvTanggalPesan, tvTanggalUpdate, tvPaymentStatus;
        ImageView imgStatus, imgPhoto;

        public ViewHolder(View itemView) {
            super(itemView);
            tvJumlahTiket = itemView.findViewById(R.id.tv_jumlah_ticket);
            tvTotalHarga = itemView.findViewById(R.id.tv_total_harga);
            tvTanggalPesan = itemView.findViewById(R.id.tv_tanggal_pesan);
            tvTanggalUpdate = itemView.findViewById(R.id.tv_tanggal_update);
            tvPaymentStatus = itemView.findViewById(R.id.tv_payment_status);
            imgStatus = itemView.findViewById(R.id.icon_payment_status);
            imgPhoto = itemView.findViewById(R.id.iv_detail_payment_photo);
            if (onClickListener != null){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickListener.onClick(getAdapterPosition());
                    }
                });
            }
        }

        public void bind(TicketPayment ticketPayment) {
            String totalHarga = CurrencyFormated.toRupiah(Integer.parseInt(ticketPayment.getTotalHarga()));
            String tanggalPesan = DateFormated.formatDate(ticketPayment.getCreatedAt());
            String tanggalUpdate = DateFormated.formatDate(ticketPayment.getUpdatedAt());
            tvJumlahTiket.setText(String.valueOf(ticketPayment.getJumlahTicket()));
            tvTotalHarga.setText(totalHarga);
            tvTanggalPesan.setText(tanggalPesan);
            tvTanggalUpdate.setText(tanggalUpdate);
            if (ticketPayment.getStatus()== 2){
                imgStatus.setImageResource(R.drawable.ic_check);
                tvPaymentStatus.setText("Verif");
            }else {
                imgStatus.setImageResource(R.drawable.ic_close);
                tvPaymentStatus.setText("Belum");
            }

            imgPhoto.setImageResource(R.drawable.invoice);

        }
    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

}
