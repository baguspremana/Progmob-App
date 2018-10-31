package com.example.user_pc.semnas_ti.auth;

import com.example.user_pc.semnas_ti.model.User;

public interface AuthView {
    void onSuccess(User user);
    void onError();
    void onFailure(Throwable t);
}
