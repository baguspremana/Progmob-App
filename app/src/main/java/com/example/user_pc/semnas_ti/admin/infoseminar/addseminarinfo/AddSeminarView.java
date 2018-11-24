package com.example.user_pc.semnas_ti.admin.infoseminar.addseminarinfo;

import com.example.user_pc.semnas_ti.model.Response;

public interface AddSeminarView {
    void onSuccess(Response response);
    void onError();
    void onFailure(Throwable t);
}
