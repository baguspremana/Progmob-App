package com.example.user_pc.semnas_ti.admin.scanticket;

import com.example.user_pc.semnas_ti.model.PesertaResponse;

import java.util.List;

public interface ScanView {
    void showLoading();
    void hideLoading();
    void onSuccess(List<PesertaResponse> pesertaResponseList);
    void onError();
    void onFailure(Throwable t);
}
