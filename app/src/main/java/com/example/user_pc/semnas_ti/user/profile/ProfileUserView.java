package com.example.user_pc.semnas_ti.user.profile;

import com.example.user_pc.semnas_ti.model.Profile;

public interface ProfileUserView {
    void showLoading();
    void hideLoading();
    void onSuccess(Profile profile);
    void onError();
    void onFailure(Throwable t);
}
