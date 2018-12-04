package com.example.user_pc.semnas_ti.admin.logverifikasi;

import android.app.ProgressDialog;
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
import com.example.user_pc.semnas_ti.api.ApiClient;
import com.example.user_pc.semnas_ti.model.LogVerifikasiResponse;

import java.util.List;

public class LogVerifikasiFragment extends Fragment implements LogVerifikasiView{
    List<LogVerifikasiResponse> logVerifikasiResponses;
    private RecyclerView rvLog;
    private TextView tvKosong;
    private LogVerifikasiAdapter adapter;
    private LogVerifikasiPresenter presenter;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_verifikasi, container, false);
        rvLog = view.findViewById(R.id.rv_log);
        tvKosong = view.findViewById(R.id.tv_log_kosong);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Now Loading...");

        presenter = new LogVerifikasiPresenter(this, ApiClient.getService(getContext()));
        presenter.showLog();
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
    public void onSuccess(List<LogVerifikasiResponse> logVerifikasiResponses) {
        if (logVerifikasiResponses.isEmpty()){
            tvKosong.setVisibility(View.VISIBLE);
            rvLog.setVisibility(View.GONE);
        }else {
            tvKosong.setVisibility(View.GONE);
            rvLog.setVisibility(View.VISIBLE);
            this.logVerifikasiResponses = logVerifikasiResponses;
            adapter = new LogVerifikasiAdapter(getContext(), logVerifikasiResponses);
            rvLog.setLayoutManager(new LinearLayoutManager(getContext()));
            rvLog.setAdapter(adapter);
        }
    }

    @Override
    public void onError() {
        Toast.makeText(getContext(), "Response Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(Throwable t) {
        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
    }
}
