package com.example.user_pc.semnas_ti.user.ticketpayment;

import com.example.user_pc.semnas_ti.api.ApiService;
import com.example.user_pc.semnas_ti.model.TicketPayment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BayarTicketPresenter {
    private BayarTicketView view;
    private ApiService service;

    public BayarTicketPresenter(BayarTicketView view, ApiService service) {
        this.view = view;
        this.service = service;
    }

    public void getPaymentTickets(){
        view.showLoading();
        service.paymentTicket()
                .enqueue(new Callback<List<TicketPayment>>() {
                    @Override
                    public void onResponse(Call<List<TicketPayment>> call, Response<List<TicketPayment>> response) {
                        if (response.isSuccessful()){
                            view.onSuccess(response.body());
                        }else {
                            view.onError();
                        }
                        view.hideLoading();
                    }

                    @Override
                    public void onFailure(Call<List<TicketPayment>> call, Throwable t) {
                        view.onFailure(t);
                        view.hideLoading();
                    }
                });
    }
}
