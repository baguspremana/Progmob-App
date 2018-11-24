package com.example.user_pc.semnas_ti.admin.verifikasi;

import com.example.user_pc.semnas_ti.model.TicketPaymentAdmin;

import java.util.List;

public interface VerifikasiView {
    void onSuccess(List<TicketPaymentAdmin> ticketPaymentAdmins);
    void onError();
    void onFailure(Throwable t);
}
