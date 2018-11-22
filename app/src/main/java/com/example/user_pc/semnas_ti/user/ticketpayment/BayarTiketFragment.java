package com.example.user_pc.semnas_ti.user.ticketpayment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.api.ApiClient;
import com.example.user_pc.semnas_ti.model.TicketPayment;
import com.example.user_pc.semnas_ti.user.detailticketpayment.DetailTicketPaymentActivity;

import java.util.List;

public class BayarTiketFragment extends Fragment implements BayarTicketAdapter.OnClickListener, BayarTicketView {
    List<TicketPayment> ticketPayments;
    private RecyclerView rvBayarTiket;
    private BayarTicketAdapter adapter;
    private BayarTicketPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bayar_tiket, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Bayar Tiket");
        rvBayarTiket = view.findViewById(R.id.rv_bayar_tiket);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        presenter = new BayarTicketPresenter(this, ApiClient.getService(getContext()));
        presenter.getPaymentTickets();
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
    public void onSuccess(List<TicketPayment> ticketPayments) {
        this.ticketPayments = ticketPayments;
        adapter = new BayarTicketAdapter(getContext(), ticketPayments);
        adapter.setOnClickListener(this);
        rvBayarTiket.setLayoutManager(new LinearLayoutManager(getContext()));
        rvBayarTiket.setAdapter(adapter);
    }

    @Override
    public void onError() {

    }

    @Override
    public void onFailure(Throwable t) {

    }
}
