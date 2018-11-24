package com.example.user_pc.semnas_ti.admin.dataadmin.addadmin;

import com.example.user_pc.semnas_ti.model.Response;

public interface AddAdminView {
    void onSuccess(Response response);
    void onError();
    void onFailure(Throwable t);
}
