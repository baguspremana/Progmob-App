package com.example.user_pc.semnas_ti.admin.datafaq;

import com.example.user_pc.semnas_ti.model.FaqResponse;

import java.util.List;

public interface FaqAdminView {
    void showLoading();
    void hideLoading();
    void onSuccess(List<FaqResponse> faqResponses);
    void onError();
    void onFailure(Throwable t);
}
