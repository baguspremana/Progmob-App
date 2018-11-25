package com.example.user_pc.semnas_ti.admin.datafaq.detaildatafaq;

import com.example.user_pc.semnas_ti.api.ApiService;
import com.example.user_pc.semnas_ti.model.Response;

import retrofit2.Call;
import retrofit2.Callback;

public class DetailFaqAdminPresenter {
    private DetailFaqAdminView view;
    private ApiService service;

    public DetailFaqAdminPresenter(DetailFaqAdminView view, ApiService service) {
        this.view = view;
        this.service = service;
    }

    public void updateFaq(int id, String question, String answer){
        service.updateFAQ(id, question, answer)
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        if (response.isSuccessful()){
                            view.onSuccess(response.body());
                        }else {
                            view.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        view.onFailure(t);
                    }
                });
    }

    public void deleteFAQ(int id){
        service.deleteFAQ(id)
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        if (response.isSuccessful()){
                            view.onSuccess(response.body());
                        }else {
                            view.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        view.onFailure(t);
                    }
                });
    }
}
