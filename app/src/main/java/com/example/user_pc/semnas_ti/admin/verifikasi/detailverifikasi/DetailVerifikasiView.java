package com.example.user_pc.semnas_ti.admin.verifikasi.detailverifikasi;

import com.example.user_pc.semnas_ti.model.Response;

public interface DetailVerifikasiView {
    void onSuccess(Response response);
    void onError();
    void onFailure(Throwable t);
}
