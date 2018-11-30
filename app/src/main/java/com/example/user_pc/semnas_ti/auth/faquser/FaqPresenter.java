package com.example.user_pc.semnas_ti.auth.faquser;

import com.example.user_pc.semnas_ti.api.ApiService;
import com.example.user_pc.semnas_ti.model.FaqUserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FaqPresenter {
    private FaqView view;
    private ApiService service;

    public FaqPresenter(FaqView view, ApiService service) {
        this.view = view;
        this.service = service;
    }

    public void showFaqUser(){
        view.showLoading();
        service.faqUser()
                .enqueue(new Callback<List<FaqUserResponse>>() {
                    @Override
                    public void onResponse(Call<List<FaqUserResponse>> call, Response<List<FaqUserResponse>> response) {
                        if (response.isSuccessful()){
                            view.onSuccess(response.body());
                        }else {
                            view.onError();
                        }
                        view.hideLoading();
                    }

                    @Override
                    public void onFailure(Call<List<FaqUserResponse>> call, Throwable t) {
                        view.onFailure(t);
                    }
                });
    }
}
