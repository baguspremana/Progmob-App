package com.example.user_pc.semnas_ti.admin.infoseminar.updateseminarinfo;

import com.example.user_pc.semnas_ti.api.ApiService;
import com.example.user_pc.semnas_ti.model.Response;

import retrofit2.Call;
import retrofit2.Callback;

public class UpdateSeminarPresenter {
    private UpdateSeminarView view;
    private ApiService service;

    public UpdateSeminarPresenter(UpdateSeminarView view, ApiService service) {
        this.view = view;
        this.service = service;
    }

    public void updateSeminar(int id, String seminarName, String seminarTheme, String seminarDesc, String seminarSche, String seminarLoc, int jumlahTiket){
        service.updateSeminar(id, seminarName, seminarTheme, seminarDesc, seminarSche, seminarLoc, jumlahTiket)
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
