package com.example.user_pc.semnas_ti.user.addticket;

import com.example.user_pc.semnas_ti.api.ApiService;
import com.example.user_pc.semnas_ti.model.Response;

import retrofit2.Call;
import retrofit2.Callback;

public class AddTicketPresenter {
    private ApiService service;
    private AddTicketView view;

    public AddTicketPresenter(ApiService service, AddTicketView view) {
        this.service = service;
        this.view = view;
    }

    public void addBookingTicket(String bookingName, String bookingEmail, String bookingContact, int bookingVeget, String bookingInstitution){
        service.addBookingTicket(bookingName, bookingEmail, bookingContact, bookingVeget, bookingInstitution)
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        if (response.isSuccessful()){
                            view.OnSuccess(response.body());
                        }else {
                            view.OnError();
                        }
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        view.OnFailure(t);
                    }
                });
    }
}
