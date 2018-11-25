package com.example.user_pc.semnas_ti.admin.datafaq;

import com.example.user_pc.semnas_ti.api.ApiService;
import com.example.user_pc.semnas_ti.model.FaqResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FaqAdminPresenter {
    private FaqAdminView view;
    private ApiService service;

    public FaqAdminPresenter(FaqAdminView view, ApiService service) {
        this.view = view;
        this.service = service;
    }

    public void dataFAQ(){
        view.showLoading();
        service.showFAQ()
                .enqueue(new Callback<List<FaqResponse>>() {
                    @Override
                    public void onResponse(Call<List<FaqResponse>> call, Response<List<FaqResponse>> response) {
                        if (response.isSuccessful()){
                            view.onSuccess(response.body());
                        }else {
                            view.onError();
                        }
                        view.hideLoading();
                    }

                    @Override
                    public void onFailure(Call<List<FaqResponse>> call, Throwable t) {
                        view.onFailure(t);
                    }
                });
    }
}
