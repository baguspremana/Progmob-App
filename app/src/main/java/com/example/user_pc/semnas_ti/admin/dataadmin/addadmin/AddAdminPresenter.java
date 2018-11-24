package com.example.user_pc.semnas_ti.admin.dataadmin.addadmin;

import com.example.user_pc.semnas_ti.api.ApiService;
import com.example.user_pc.semnas_ti.model.Response;

import retrofit2.Call;
import retrofit2.Callback;

public class AddAdminPresenter {
    private AddAdminView view;
    private ApiService service;

    public AddAdminPresenter(AddAdminView view, ApiService service) {
        this.view = view;
        this.service = service;
    }

    public void addAdmin(String name, String email, int gender, String contact, String password){
        service.addAdmin(name, email, gender, contact, password)
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
