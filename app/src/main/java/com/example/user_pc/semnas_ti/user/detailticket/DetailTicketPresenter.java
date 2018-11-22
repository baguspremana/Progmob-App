package com.example.user_pc.semnas_ti.user.detailticket;

import android.util.Log;

import com.example.user_pc.semnas_ti.api.ApiService;
import com.example.user_pc.semnas_ti.model.Response;

import retrofit2.Call;
import retrofit2.Callback;

public class DetailTicketPresenter {
    private ApiService service;
    private DetailTicketView view;

    public DetailTicketPresenter(ApiService service, DetailTicketView view) {
        this.service = service;
        this.view = view;
    }

    public void updateBooking(int id, String bookingName, String bookingEmail, String bookingContact, int bookingVeget, String bookingInstitution){
        service.updateBooking(id, bookingName, bookingEmail, bookingContact, bookingVeget, bookingInstitution)
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

    public void deleteBooking(int id){
        service.deleteBooking(id)
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
