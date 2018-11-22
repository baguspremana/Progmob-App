package com.example.user_pc.semnas_ti.user.addticket;

import com.example.user_pc.semnas_ti.model.Response;

public interface AddTicketView {
    void OnSuccess(Response response);
    void OnError();
    void OnFailure(Throwable t);

}
