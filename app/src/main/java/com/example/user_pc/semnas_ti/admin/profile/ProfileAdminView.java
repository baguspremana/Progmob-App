package com.example.user_pc.semnas_ti.admin.profile;

import com.example.user_pc.semnas_ti.model.Profile;

public interface ProfileAdminView {
    void showLoading();
    void hideLoading();
    void onSuccess(Profile profile);
    void onError();
    void onFailure(Throwable t);
}
