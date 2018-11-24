package com.example.user_pc.semnas_ti.admin.infoseminar;

import com.example.user_pc.semnas_ti.model.InfoSeminarResponse;

public interface InfoSeminarView {
    void showLoading();
    void hideLoading();
    void onSuccess(InfoSeminarResponse infoSeminarResponse);
    void onError();
    void onFailure(Throwable t);
}
