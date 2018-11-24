package com.example.user_pc.semnas_ti.admin.dataadmin;

import com.example.user_pc.semnas_ti.api.ApiService;
import com.example.user_pc.semnas_ti.model.DataAdmin;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminPresenter {
    private AdminView view;
    private ApiService service;

    public AdminPresenter(AdminView view, ApiService service) {
        this.view = view;
        this.service = service;
    }

    public void dataAdmin(){
        view.showLoading();
        service.dataAdmin()
                .enqueue(new Callback<List<DataAdmin>>() {
                    @Override
                    public void onResponse(Call<List<DataAdmin>> call, Response<List<DataAdmin>> response) {
                        if (response.isSuccessful()){
                            view.onSuccess(response.body());
                        }else {
                            view.onError();
                        }
                        view.hideLoading();
                    }

                    @Override
                    public void onFailure(Call<List<DataAdmin>> call, Throwable t) {
                        view.onFailure(t);
                    }
                });
    }
}
