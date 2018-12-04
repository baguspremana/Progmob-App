package com.example.user_pc.semnas_ti.admin.dashboardadmin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.api.ApiClient;
import com.example.user_pc.semnas_ti.bantuan.CurrencyFormated;
import com.example.user_pc.semnas_ti.model.DashboardAdminResponse;

public class DashboardFragment extends Fragment implements DashboardView {
    private TextView tvBelum, tvVerif, tvTiket, tvVeget, tvPeserta, tvDatang, tvPenjualan;
    private DashboardPresenter presenter;
    private DashboardAdminResponse dashboardAdminResponse;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        tvBelum = view.findViewById(R.id.tv_dashboard_belum_verif);
        tvVerif = view.findViewById(R.id.tv_dashboard_verif);
        tvTiket = view.findViewById(R.id.tv_dashboard_tiket);
        tvVeget = view.findViewById(R.id.tv_dashboard_veget);
        tvPeserta = view.findViewById(R.id.tv_dashboard_peserta);
        tvDatang = view.findViewById(R.id.tv_dashboard_peserta_datang);
        tvPenjualan = view.findViewById(R.id.tv_dashboard_total_penjualan);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Now Loading...");

        presenter = new DashboardPresenter(this, ApiClient.getService(getContext()));
        presenter.showDashboard();
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
    public void onSuccess(DashboardAdminResponse dashboardAdminResponse) {
        this.dashboardAdminResponse = dashboardAdminResponse;
        tvBelum.setText(String.valueOf(dashboardAdminResponse.getBelumVerif()));
        tvVerif.setText(String.valueOf(dashboardAdminResponse.getVerif()));
        tvTiket.setText(String.valueOf(dashboardAdminResponse.getJumlahTicket()));
        tvVeget.setText(String.valueOf(dashboardAdminResponse.getVeget()));
        tvPeserta.setText(String.valueOf(dashboardAdminResponse.getVerif()));
        tvDatang.setText(String.valueOf(dashboardAdminResponse.getHadir()));
        tvPenjualan.setText(CurrencyFormated.toRupiah(Integer.parseInt(dashboardAdminResponse.getTotalPenjualan())));
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
