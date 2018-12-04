package com.example.user_pc.semnas_ti.admin.verifikasi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.admin.verifikasi.detailverifikasi.DetailVerifikasiActivity;
import com.example.user_pc.semnas_ti.api.ApiClient;
import com.example.user_pc.semnas_ti.model.TicketPaymentAdmin;

import java.util.List;

public class VerifikasiFragment extends Fragment implements VerifikasiAdapter.OnClickListener, VerifikasiView {
    List<TicketPaymentAdmin> ticketPaymentAdmins;
    private RecyclerView rvDataVerif;
    private TextView tvKosong;
    private VerifikasiAdapter adapter;
    private VerifikasiPresenter presenter;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_verifikasi, container, false);
        rvDataVerif = view.findViewById(R.id.rv_verifikasi);
        tvKosong = view.findViewById(R.id.tv_verifikasi_kosong);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Now Loading...");

        presenter = new VerifikasiPresenter(this, ApiClient.getService(getContext()));
        presenter.dataVerifikasi();
    }

    @Override
    public void onClick(int position) {
        TicketPaymentAdmin ticketPaymentAdmin = ticketPaymentAdmins.get(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable(DetailVerifikasiActivity.KEY_TICKET, ticketPaymentAdmin);
        Intent intent = new Intent(getContext(), DetailVerifikasiActivity.class);
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
    public void onSuccess(List<TicketPaymentAdmin> ticketPaymentAdmins) {
        if (ticketPaymentAdmins.isEmpty()){
            tvKosong.setVisibility(View.VISIBLE);
            rvDataVerif.setVisibility(View.GONE);
        }else {
            tvKosong.setVisibility(View.GONE);
            rvDataVerif.setVisibility(View.VISIBLE);
            this.ticketPaymentAdmins = ticketPaymentAdmins;
            adapter = new VerifikasiAdapter(getContext(), ticketPaymentAdmins);
            adapter.setOnClickListener(this);
            rvDataVerif.setLayoutManager(new LinearLayoutManager(getContext()));
            rvDataVerif.setAdapter(adapter);
        }
    }

    @Override
    public void onError() {
        Toast.makeText(getContext(), "Response Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(Throwable t) {
        Toast.makeText(getContext(), "Error"+t, Toast.LENGTH_SHORT).show();
    }
}
