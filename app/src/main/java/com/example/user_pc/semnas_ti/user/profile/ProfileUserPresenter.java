package com.example.user_pc.semnas_ti.user.profile;

import com.example.user_pc.semnas_ti.api.ApiService;
import com.example.user_pc.semnas_ti.model.Profile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileUserPresenter {
    private ProfileUserView view;
    private ApiService service;

    public ProfileUserPresenter(ProfileUserView view, ApiService service) {
        this.view = view;
        this.service = service;
    }

    public void showProfile(){
        view.showLoading();
        service.showProfile()
                .enqueue(new Callback<Profile>() {
                    @Override
                    public void onResponse(Call<Profile> call, Response<Profile> response) {
                        if (response.isSuccessful()){
                            view.onSuccess(response.body());
                        }else {
                            view.onError();
                        }
                        view.hideLoading();
                    }

                    @Override
                    public void onFailure(Call<Profile> call, Throwable t) {
                        view.onFailure(t);
                        view.hideLoading();
                    }
                });
    }
}
