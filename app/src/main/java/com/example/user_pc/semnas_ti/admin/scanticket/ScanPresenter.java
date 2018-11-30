package com.example.user_pc.semnas_ti.admin.scanticket;

import com.example.user_pc.semnas_ti.api.ApiService;
import com.example.user_pc.semnas_ti.model.PesertaResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanPresenter {
    private ScanView view;
    private ApiService service;

    public ScanPresenter(ScanView view, ApiService service) {
        this.view = view;
        this.service = service;
    }

    public void showPeserta(){
        view.showLoading();
        service.showPeserta()
                .enqueue(new Callback<List<PesertaResponse>>() {
                    @Override
                    public void onResponse(Call<List<PesertaResponse>> call, Response<List<PesertaResponse>> response) {
                        if (response.isSuccessful()){
                            view.onSuccess(response.body());
                        }else {
                            view.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<PesertaResponse>> call, Throwable t) {
                        view.onFailure(t);
                    }
                });
    }
}
