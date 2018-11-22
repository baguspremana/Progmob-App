package com.example.user_pc.semnas_ti.user.ticket;

import com.example.user_pc.semnas_ti.model.Ticket;

import java.util.List;

public interface TicketView {
    void onSuccess(List<Ticket> ticketList);
    void onError();
    void onFailure(Throwable t);
}
