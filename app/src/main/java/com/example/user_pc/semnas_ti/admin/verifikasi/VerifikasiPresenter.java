package com.example.user_pc.semnas_ti.admin.verifikasi;

import com.example.user_pc.semnas_ti.api.ApiService;
import com.example.user_pc.semnas_ti.model.TicketPaymentAdmin;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifikasiPresenter {
    private VerifikasiView view;
    private ApiService service;

    public VerifikasiPresenter(VerifikasiView view, ApiService service) {
        this.view = view;
        this.service = service;
    }

    public void dataVerifikasi(){
        view.showLoading();
        service.verifikasi()
                .enqueue(new Callback<List<TicketPaymentAdmin>>() {
                    @Override
                    public void onResponse(Call<List<TicketPaymentAdmin>> call, Response<List<TicketPaymentAdmin>> response) {
                        if (response.isSuccessful()){
                            view.onSuccess(response.body());
                        }else {
                            view.onError();
                        }
                        view.hideLoading();
                    }

                    @Override
                    public void onFailure(Call<List<TicketPaymentAdmin>> call, Throwable t) {
                        view.onFailure(t);
                    }
                });
    }
}
