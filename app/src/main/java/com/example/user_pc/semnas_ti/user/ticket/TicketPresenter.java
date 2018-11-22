package com.example.user_pc.semnas_ti.user.ticket;

import com.example.user_pc.semnas_ti.api.ApiService;
import com.example.user_pc.semnas_ti.model.Ticket;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketPresenter {
    private TicketView view;
    private ApiService service;

    public TicketPresenter(TicketView view, ApiService service) {
        this.view = view;
        this.service = service;
    }

    public void getTickets(){
        service.allTicket()
                .enqueue(new Callback<List<Ticket>>() {
                    @Override
                    public void onResponse(Call<List<Ticket>> call, Response<List<Ticket>> response) {
                        if (response.isSuccessful()){
                            view.onSuccess(response.body());
                        }else {
                            view.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Ticket>> call, Throwable t) {
                        view.onFailure(t);
                    }
                });
    }
}
