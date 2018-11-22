package com.example.user_pc.semnas_ti.user.ticket;

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
import com.example.user_pc.semnas_ti.model.Ticket;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder>{
    private Context context;
    private OnClickListener onClickListener;
    private List<Ticket> ticketList;

    public TicketAdapter(Context context, List<Ticket> ticketList) {
        this.context = context;
        this.ticketList = ticketList;
    }

    public interface OnClickListener{
        void onClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_tiket_user,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Ticket ticket = ticketList.get(i);
        viewHolder.bind(ticket);
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvEmail, tvPrice, tvTanggal, tvStatus;
        ImageView imgStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_booking_name);
            tvEmail = itemView.findViewById(R.id.tv_booking_email);
            tvPrice = itemView.findViewById(R.id.tv_booking_price);
            tvTanggal = itemView.findViewById(R.id.tv_tgl);
            imgStatus = itemView.findViewById(R.id.icon_status);
            tvStatus = itemView.findViewById(R.id.tv_status);
            if (onClickListener!=null){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickListener.onClick(getAdapterPosition());
                    }
                });
            }
        }

        public void bind(Ticket ticket) {
            String bookingPrice = CurrencyFormated.toRupiah(ticket.getBookingPrice());
            tvName.setText(ticket.getBookingName());
            tvEmail.setText(ticket.getBookingEmail());
            tvPrice.setText(bookingPrice);
            tvTanggal.setText(ticket.getCreatedAt());
            if (ticket.getStatus()== 2) {
                imgStatus.setImageResource(R.drawable.ic_check);
                tvStatus.setText("Verif");
            }else {
                imgStatus.setImageResource(R.drawable.ic_close);
                tvStatus.setText("Belum");
            }
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
