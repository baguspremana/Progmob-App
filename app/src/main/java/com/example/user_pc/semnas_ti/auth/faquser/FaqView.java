package com.example.user_pc.semnas_ti.auth.faquser;

import com.example.user_pc.semnas_ti.model.FaqUserResponse;

import java.util.List;

public interface FaqView {
    void showLoading();
    void hideLoading();
    void onSuccess(List<FaqUserResponse> faqUserResponses);
    void onError();
    void onFailure();
}
