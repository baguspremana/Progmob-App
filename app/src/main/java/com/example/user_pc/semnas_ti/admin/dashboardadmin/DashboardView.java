package com.example.user_pc.semnas_ti.admin.dashboardadmin;

import com.example.user_pc.semnas_ti.model.DashboardAdminResponse;

public interface DashboardView {
    void showLoading();
    void hideLoading();
    void onSuccess(DashboardAdminResponse dashboardAdminResponse);
    void onError();
    void onFailure(Throwable t);
}
