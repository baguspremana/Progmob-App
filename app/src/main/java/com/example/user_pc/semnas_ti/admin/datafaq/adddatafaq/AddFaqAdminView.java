package com.example.user_pc.semnas_ti.admin.datafaq.adddatafaq;

import com.example.user_pc.semnas_ti.model.Response;

public interface AddFaqAdminView {
    void onSuccess(Response response);
    void onError();
    void onFailure(Throwable t);
}
