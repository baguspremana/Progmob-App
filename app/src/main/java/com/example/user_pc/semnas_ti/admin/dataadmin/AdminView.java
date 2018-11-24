package com.example.user_pc.semnas_ti.admin.dataadmin;

import com.example.user_pc.semnas_ti.model.DataAdmin;

import java.util.List;

public interface AdminView {
    void showLoading();
    void hideLoading();
    void onSuccess(List<DataAdmin> dataAdminList);
    void onError();
    void onFailure(Throwable t);
}
