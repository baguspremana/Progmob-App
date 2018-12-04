package com.example.user_pc.semnas_ti.user.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.api.ApiClient;
import com.example.user_pc.semnas_ti.bantuan.DateFormated;
import com.example.user_pc.semnas_ti.bantuan.DbHelper;
import com.example.user_pc.semnas_ti.model.InfoSeminarResponse;

public class HomeUserFragment extends Fragment implements HomeUserView {
    TextView tvWaktu, tvTiket, tvTema, tvTanggal, tvLokasi;
    InfoSeminarResponse infoSeminarResponse;
    private HomeUserPresenter presenter;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home_user, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        tvWaktu = view.findViewById(R.id.tv_home_waktu);
        tvTiket = view.findViewById(R.id.tv_home_tiket);
        tvTema = view.findViewById(R.id.tv_home_tema);
        tvTanggal = view.findViewById(R.id.tv_home_tanggal);
        tvLokasi = view.findViewById(R.id.tv_home_lokasi);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Now Loading...");

        callInfoLocal();
        presenter = new HomeUserPresenter(this, ApiClient.getService(getContext()));
        presenter.infoSeminar();
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
        DbHelper dbHelper = new DbHelper(getContext());

        dbHelper.deleteInfo();
        this.infoSeminarResponse = infoSeminarResponse;
        dbHelper.insertInfo(infoSeminarResponse.getId(), infoSeminarResponse.getSeminarName(), infoSeminarResponse.getSeminarTheme(),
                infoSeminarResponse.getSeminarDescription(), infoSeminarResponse.getSeminarSchedule(), infoSeminarResponse.getSeminarLocation(),
                infoSeminarResponse.getTicketAvailable());

        String waktu = DateFormated.setWaktu(infoSeminarResponse.getSeminarSchedule());
        String tanggal = DateFormated.setPelaksanaan(infoSeminarResponse.getSeminarSchedule());
        tvTema.setText(infoSeminarResponse.getSeminarTheme());
        tvLokasi.setText(infoSeminarResponse.getSeminarLocation());
        tvTiket.setText(String.valueOf(infoSeminarResponse.getTicketAvailable()));
        tvTanggal.setText(tanggal);
        tvWaktu.setText(waktu);
    }

    @Override
    public void onError() {
        Toast.makeText(getContext(), "Response Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(Throwable t) {
        Toast.makeText(getContext(), "Anda Sedang Offline", Toast.LENGTH_SHORT).show();
    }

    private void callInfoLocal(){
        DbHelper dbHelper=new DbHelper(getContext());
        infoSeminarResponse=dbHelper.selectInfo();

        Log.d("seminar_name", infoSeminarResponse.getSeminarName());
        String waktu = DateFormated.setWaktu(infoSeminarResponse.getSeminarSchedule());
        String tanggal = DateFormated.setPelaksanaan(infoSeminarResponse.getSeminarSchedule());
        tvTema.setText(infoSeminarResponse.getSeminarTheme());
        tvLokasi.setText(infoSeminarResponse.getSeminarLocation());
        tvTiket.setText(String.valueOf(infoSeminarResponse.getTicketAvailable()));
        tvTanggal.setText(tanggal);
        tvWaktu.setText(waktu);
    }
}
