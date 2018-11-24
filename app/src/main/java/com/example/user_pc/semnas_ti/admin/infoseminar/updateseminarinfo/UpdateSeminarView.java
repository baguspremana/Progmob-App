package com.example.user_pc.semnas_ti.admin.infoseminar.updateseminarinfo;

import com.example.user_pc.semnas_ti.model.Response;

public interface UpdateSeminarView {
    void onSuccess(Response response);
    void onError();
    void onFailure(Throwable t);
}
