package com.example.user_pc.semnas_ti.user.ticketpayment;

import com.example.user_pc.semnas_ti.model.TicketPayment;

import java.util.List;

public interface BayarTicketView {
    void showLoading();
    void hideLoading();
    void onSuccess(List<TicketPayment> ticketPayments);
    void onError();
    void onFailure(Throwable t);
}
