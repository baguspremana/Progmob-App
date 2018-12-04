package com.example.user_pc.semnas_ti.user.home;

import com.example.user_pc.semnas_ti.api.ApiService;
import com.example.user_pc.semnas_ti.model.InfoSeminarResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeUserPresenter {
    private HomeUserView view;
    private ApiService service;

    public HomeUserPresenter(HomeUserView view, ApiService service) {
        this.view = view;
        this.service = service;
    }

    public void infoSeminar(){
        view.showLoading();
        service.infoSeminar()
                .enqueue(new Callback<InfoSeminarResponse>() {
                    @Override
                    public void onResponse(Call<InfoSeminarResponse> call, Response<InfoSeminarResponse> response) {
                        if (response.isSuccessful()){
                            view.onSuccess(response.body());
                        }else {
                            view.onError();
                        }
                        view.hideLoading();
                    }

                    @Override
                    public void onFailure(Call<InfoSeminarResponse> call, Throwable t) {
                        view.onFailure(t);
                        view.hideLoading();
                    }
                });
    }
}
