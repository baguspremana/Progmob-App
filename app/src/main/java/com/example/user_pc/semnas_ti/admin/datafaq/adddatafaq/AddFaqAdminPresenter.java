package com.example.user_pc.semnas_ti.admin.datafaq.adddatafaq;

import com.example.user_pc.semnas_ti.api.ApiService;
import com.example.user_pc.semnas_ti.model.Response;

import retrofit2.Call;
import retrofit2.Callback;

public class AddFaqAdminPresenter {
    private AddFaqAdminView view;
    private ApiService service;

    public AddFaqAdminPresenter(AddFaqAdminView view, ApiService service) {
        this.view = view;
        this.service = service;
    }

    public void addFAQ(String question, String answer){
        service.addFAQ(question, answer)
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
