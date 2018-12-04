package com.example.user_pc.semnas_ti.user.home;

import com.example.user_pc.semnas_ti.model.InfoSeminarResponse;

public interface HomeUserView {
    void showLoading();
    void hideLoading();
    void onSuccess(InfoSeminarResponse infoSeminarResponse);
    void onError();
    void onFailure(Throwable t);
}
