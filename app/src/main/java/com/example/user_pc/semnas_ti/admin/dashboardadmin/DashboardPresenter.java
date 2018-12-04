package com.example.user_pc.semnas_ti.admin.dashboardadmin;

import com.example.user_pc.semnas_ti.api.ApiService;
import com.example.user_pc.semnas_ti.model.DashboardAdminResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardPresenter {
    private DashboardView view;
    private ApiService service;

    public DashboardPresenter(DashboardView view, ApiService service) {
        this.view = view;
        this.service = service;
    }

    public void showDashboard(){
        view.showLoading();
        service.showDashboard()
                .enqueue(new Callback<DashboardAdminResponse>() {
                    @Override
                    public void onResponse(Call<DashboardAdminResponse> call, Response<DashboardAdminResponse> response) {
                        if (response.isSuccessful()){
                            view.onSuccess(response.body());
                        }else {
                            view.onError();
                        }
                        view.hideLoading();
                    }

                    @Override
                    public void onFailure(Call<DashboardAdminResponse> call, Throwable t) {
                        view.onFailure(t);
                    }
                });
    }
}
