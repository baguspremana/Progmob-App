package com.example.user_pc.semnas_ti.admin.logverifikasi;

import com.example.user_pc.semnas_ti.model.LogVerifikasiResponse;

import java.util.List;

public interface LogVerifikasiView {
    void showLoading();
    void hideLoading();
    void onSuccess(List<LogVerifikasiResponse> logVerifikasiResponses);
    void onError();
    void onFailure(Throwable t);
}
