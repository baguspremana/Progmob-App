package com.example.user_pc.semnas_ti.admin.infoseminar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.admin.infoseminar.addseminarinfo.AddSeminarActivity;
import com.example.user_pc.semnas_ti.admin.infoseminar.updateseminarinfo.UpdateSeminarActivity;
import com.example.user_pc.semnas_ti.api.ApiClient;
import com.example.user_pc.semnas_ti.bantuan.DateFormated;
import com.example.user_pc.semnas_ti.model.InfoSeminarResponse;

public class InfoSeminarFragment extends Fragment implements InfoSeminarView, View.OnClickListener {
    private Button btnAdd, btnUpdate;
    private LinearLayout infoSeminar;
    private InfoSeminarPresenter presenter;
    InfoSeminarResponse seminar;
    private TextView tvSeminarName, tvSeminarTheme, tvSeminarSchedule, tvSeminarLocation, tvTicket;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_seminar, container, false);
        btnAdd = view.findViewById(R.id.add_seminar_info);
        btnUpdate = view.findViewById(R.id.edit_seminar);
        infoSeminar = view.findViewById(R.id.info_seminar);
        tvSeminarName = view.findViewById(R.id.seminar_name);
        tvSeminarTheme = view.findViewById(R.id.seminar_theme);
        tvSeminarSchedule = view.findViewById(R.id.seminar_schedule);
        tvSeminarLocation = view.findViewById(R.id.seminar_location);
        tvTicket = view.findViewById(R.id.seminar_ticket);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Now Loading");

        presenter = new InfoSeminarPresenter(this, ApiClient.getService(getContext()));
        presenter.infoSeminar();

        btnAdd.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_seminar_info:
                Intent intent = new Intent(getContext(), AddSeminarActivity.class);
                startActivity(intent);
                break;
            case R.id.edit_seminar:
                Bundle bundle = new Bundle();
                bundle.putParcelable(UpdateSeminarActivity.KEY_SEMINAR, seminar);
                Intent intent1 = new Intent(getContext(), UpdateSeminarActivity.class);
                intent1.putExtras(bundle);
                startActivity(intent1);
                break;
        }
    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        progressDialog.hide();
    }

    @Override
    public void onSuccess(InfoSeminarResponse infoSeminarResponse) {
        if (infoSeminarResponse.getSeminarName()==null){
            btnAdd.setVisibility(View.VISIBLE);
            infoSeminar.setVisibility(View.GONE);
        }else {
            btnAdd.setVisibility(View.GONE);
            infoSeminar.setVisibility(View.VISIBLE);
            this.seminar = infoSeminarResponse;
            tvSeminarName.setText(infoSeminarResponse.getSeminarName());
            tvSeminarTheme.setText(infoSeminarResponse.getSeminarTheme());
            tvSeminarSchedule.setText(DateFormated.setDate(infoSeminarResponse.getSeminarSchedule()));
            tvSeminarLocation.setText(infoSeminarResponse.getSeminarLocation());
            tvTicket.setText(String.valueOf(infoSeminarResponse.getTicketAvailable()));
        }
    }

    @Override
    public void onError() {
        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(Throwable t) {
        Toast.makeText(getContext(), "Failed"+t, Toast.LENGTH_SHORT).show();
    }
}
