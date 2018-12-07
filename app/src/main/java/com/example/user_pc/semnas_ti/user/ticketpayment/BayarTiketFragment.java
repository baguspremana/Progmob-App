package com.example.user_pc.semnas_ti.user.ticketpayment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.api.ApiClient;
import com.example.user_pc.semnas_ti.bantuan.DbHelper;
import com.example.user_pc.semnas_ti.model.TicketPayment;
import com.example.user_pc.semnas_ti.user.detailticketpayment.DetailTicketPaymentActivity;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class BayarTiketFragment extends Fragment implements BayarTicketAdapter.OnClickListener, BayarTicketView {
    List<TicketPayment> ticketPayments;
    private RecyclerView rvBayarTiket;
    private BayarTicketAdapter adapter;
    private BayarTicketPresenter presenter;
    ProgressDialog progressDialog;
    FloatingActionMenu floatingActionMenu;
    FloatingActionButton floatingActionButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_bayar_tiket, container, false);
//        rvBayarTiket = view.findViewById(R.id.rv_bayar_tiket);

        return inflater.inflate(R.layout.fragment_bayar_tiket, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvBayarTiket = view.findViewById(R.id.rv_bayar_tiket);
        floatingActionMenu = view.findViewById(R.id.floatingMenuBayar);
        floatingActionButton = view.findViewById(R.id.fab_bayar);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Now Loading...");

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(), "Coba", Toast.LENGTH_SHORT).show();
                showDialog();
            }
        });

        callPaymentTicketLocal();
        presenter = new BayarTicketPresenter(this, ApiClient.getService(getContext()));
        presenter.getPaymentTickets();
    }

    private void showDialog() {
        final TextView tvBayar;

        AlertDialog.Builder dialog;
        dialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_bayar, null);

        tvBayar = dialogView.findViewById(R.id.tv_cara_bayar);
        tvBayar.setText("Pembayaran dapat dilakukan dengan transfer ke rekening BNI Atas Nama Bendahara Nomor Rekening " +
                "xxxx-xxxxxxxx, kemudian upload bukti pembayaran pada menu bayar dengan terlebih dahulu memilih " +
                "list tiket yang akan dibayar");
        dialog.setView(dialogView);
        dialog.show();
    }

    @Override
    public void onClick(int position) {
        TicketPayment ticketPayment = ticketPayments.get(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable(DetailTicketPaymentActivity.KEY_TICKET_PAYMENT, ticketPayment);
        Intent intent = new Intent(getContext(), DetailTicketPaymentActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        progressDialog.hide();
    }

    @Override
    public void onSuccess(List<TicketPayment> ticketPayments) {
        DbHelper dbHelper=new DbHelper(getContext());
        dbHelper.deleteTicket();

        this.ticketPayments = ticketPayments;

        for (TicketPayment ticketPayment:ticketPayments){
            dbHelper.insertTicket(ticketPayment.getId(), ticketPayment.getUserId(), ticketPayment.getStatus(),
                    ticketPayment.getPhoto(), ticketPayment.getEtc(), ticketPayment.getJumlahTicket(), ticketPayment.getTotalHarga(),
                    ticketPayment.getCreatedAt(), ticketPayment.getUpdatedAt());
        }
        adapter = new BayarTicketAdapter(getContext(), ticketPayments);
        adapter.setOnClickListener(this);
        rvBayarTiket.setLayoutManager(new LinearLayoutManager(getContext()));
        rvBayarTiket.setAdapter(adapter);
    }

    @Override
    public void onError() {
        Toasty.warning(getContext(), "Response Failed", Toast.LENGTH_SHORT, true).show();

    }

    @Override
    public void onFailure(Throwable t) {
        Toasty.error(getContext(), "Anda Sedang Offline", Toast.LENGTH_SHORT, true).show();

    }

    private void callPaymentTicketLocal(){
        DbHelper dbHelper = new DbHelper(getContext());
        ticketPayments = dbHelper.selectTicket();

        adapter = new BayarTicketAdapter(getContext(), ticketPayments);
        adapter.setOnClickListener(this);
        rvBayarTiket.setLayoutManager(new LinearLayoutManager(getContext()));
        rvBayarTiket.setAdapter(adapter);
    }
}
