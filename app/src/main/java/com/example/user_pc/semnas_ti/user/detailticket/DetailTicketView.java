package com.example.user_pc.semnas_ti.user.detailticket;

import com.example.user_pc.semnas_ti.model.Response;

public interface DetailTicketView {
    void onSuccess(Response response);
    void onError();
    void onFailure(Throwable t);
}
