package com.example.user_pc.semnas_ti.admin.scanticket;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.admin.scanticket.detailscanticket.DetailScanActivity;
import com.example.user_pc.semnas_ti.admin.scanticket.scanticketkode.ScanTicketActivity;
import com.example.user_pc.semnas_ti.api.ApiClient;
import com.example.user_pc.semnas_ti.model.PesertaResponse;

import java.util.List;

public class ScanFragment extends Fragment implements ScanAdapter.OnClickListener, ScanView {
    List<PesertaResponse> pesertaResponses;
    private RecyclerView rvScan;
    private TextView tvKosong;
    private ScanAdapter adapter;
    private ScanPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scan, container, false);
        rvScan = view.findViewById(R.id.rv_scan_tiket);
        tvKosong = view.findViewById(R.id.tv_scan_kosong);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        presenter = new ScanPresenter(this, ApiClient.getService(getContext()));
        presenter.showPeserta();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_scan_ticket_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.scan_ticket:
                Intent intent = new Intent(getContext(), ScanTicketActivity.class);
                startActivity(intent);

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onClick(int position) {
        PesertaResponse pesertaResponse = pesertaResponses.get(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable(DetailScanActivity.KEY_PESERTA, pesertaResponse);
        Intent intent = new Intent(getContext(), DetailScanActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void showLoading() {
        Toast.makeText(getContext(), "Now Loading", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {
        Toast.makeText(getContext(), "Loaded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(List<PesertaResponse> pesertaResponseList) {
        if (pesertaResponseList.isEmpty()){
            tvKosong.setVisibility(View.VISIBLE);
            rvScan.setVisibility(View.GONE);
        }else {
            tvKosong.setVisibility(View.GONE);
            rvScan.setVisibility(View.VISIBLE);
            this.pesertaResponses = pesertaResponseList;
            adapter = new ScanAdapter(getContext(), pesertaResponseList);
            adapter.setOnClickListener(this);
            rvScan.setLayoutManager(new LinearLayoutManager(getContext()));
            rvScan.setAdapter(adapter);
        }
    }

    @Override
    public void onError() {
        Toast.makeText(getContext(), "Response Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(Throwable t) {
        Toast.makeText(getContext(), "Failure"+t, Toast.LENGTH_SHORT).show();
    }
}
