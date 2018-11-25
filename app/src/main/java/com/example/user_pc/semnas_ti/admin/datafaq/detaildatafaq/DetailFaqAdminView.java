package com.example.user_pc.semnas_ti.admin.datafaq.detaildatafaq;

import com.example.user_pc.semnas_ti.model.Response;

public interface DetailFaqAdminView {
    void onSuccess(Response response);
    void onError();
    void onFailure(Throwable t);
}
