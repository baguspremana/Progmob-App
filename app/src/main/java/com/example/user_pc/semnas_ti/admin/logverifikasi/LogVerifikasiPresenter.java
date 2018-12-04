package com.example.user_pc.semnas_ti.admin.logverifikasi;

import com.example.user_pc.semnas_ti.api.ApiService;
import com.example.user_pc.semnas_ti.model.LogVerifikasiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogVerifikasiPresenter {
    private LogVerifikasiView view;
    private ApiService service;

    public LogVerifikasiPresenter(LogVerifikasiView view, ApiService service) {
        this.view = view;
        this.service = service;
    }

    public void showLog(){
        view.showLoading();
        service.showLog()
                .enqueue(new Callback<List<LogVerifikasiResponse>>() {
                    @Override
                    public void onResponse(Call<List<LogVerifikasiResponse>> call, Response<List<LogVerifikasiResponse>> response) {
                        if (response.isSuccessful()){
                            view.onSuccess(response.body());
                        }else {
                            view.onError();
                        }
                        view.hideLoading();
                    }

                    @Override
                    public void onFailure(Call<List<LogVerifikasiResponse>> call, Throwable t) {
                        view.onFailure(t);
                        view.hideLoading();
                    }
                });
    }
}
