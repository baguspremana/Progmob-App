package com.example.user_pc.semnas_ti.user.ticket;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.api.ApiClient;
import com.example.user_pc.semnas_ti.bantuan.DbHelper;
import com.example.user_pc.semnas_ti.model.InfoSeminarResponse;
import com.example.user_pc.semnas_ti.model.Ticket;
import com.example.user_pc.semnas_ti.user.addticket.AddTicketAcitivity;
import com.example.user_pc.semnas_ti.user.detailticket.CaraPesanTicketActivity;
import com.example.user_pc.semnas_ti.user.detailticket.DetailTicketActivity;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TiketUserFragment extends Fragment implements TicketAdapter.OnClickListener, TicketView{
    List<Ticket> ticketList;
    private RecyclerView rvTicket;
    private TicketAdapter adapter;
    private TicketPresenter presenter;
    private InfoSeminarResponse infoSeminarResponse;
    ProgressDialog progressDialog;
    FloatingActionMenu floatingActionMenu;
    FloatingActionButton fab1, fab2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tiket_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvTicket=view.findViewById(R.id.rv_tiket);
        floatingActionMenu=view.findViewById(R.id.floatingMenu);
        fab1=view.findViewById(R.id.fab_action1);
        fab2=view.findViewById(R.id.fab_action2);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Now Loading...");

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddTicketAcitivity.class);
                startActivity(intent);
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CaraPesanTicketActivity.class);
                startActivity(intent);
            }
        });

        presenter = new TicketPresenter(this, ApiClient.getService(getContext()));
        presenter.getTickets();

        ApiClient.getService(getContext())
                .infoSeminar()
                .enqueue(new Callback<InfoSeminarResponse>() {
                    @Override
                    public void onResponse(Call<InfoSeminarResponse> call, Response<InfoSeminarResponse> response) {
                        if (response.isSuccessful()){
                            infoSeminarResponse = response.body();
                            if (infoSeminarResponse.getTicketAvailable()==0){
                                fab1.setVisibility(View.GONE);
                            }
                        }else {

                        }
                    }

                    @Override
                    public void onFailure(Call<InfoSeminarResponse> call, Throwable t) {
                        DbHelper dbHelper = new DbHelper(getContext());
                        infoSeminarResponse = dbHelper.selectInfo();
                        if (infoSeminarResponse.getTicketAvailable()==0){
                            fab1.setVisibility(View.GONE);
                        }
                    }
                });

    }

    @Override
    public void onClick(int position) {
        Ticket ticket = ticketList.get(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable(DetailTicketActivity.KEY_TICKET, ticket);
        Intent intent = new Intent(getContext(), DetailTicketActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
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
    public void onSuccess(List<Ticket> ticketList) {
        if (ticketList.isEmpty()){
            Toast.makeText(getContext(), "Kosong", Toast.LENGTH_SHORT).show();
        }else {
            DbHelper dbHelper = new DbHelper(getContext());
            dbHelper.deleteTicketDetail();

            this.ticketList=ticketList;

            for (Ticket ticket:ticketList){
                dbHelper.insertTicketDetail(ticket.getId(), ticket.getQrcodePhoto(), ticket.getBookingName(), ticket.getBookingEmail(),
                        ticket.getBookingContact(), ticket.getBookingVeget(), ticket.getBookingInstitution(), ticket.getBookingPrice(),
                        ticket.getCreatedAt(), ticket.getStatus());
            }
            adapter = new TicketAdapter(getContext(), ticketList);
            adapter.setOnClickListener(this);
            rvTicket.setLayoutManager(new LinearLayoutManager(getContext()));
            rvTicket.setAdapter(adapter);
        }

    }

    @Override
    public void onError() {
        Toasty.warning(getContext(), "Response Failed", Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void onFailure() {
        Toasty.error(getContext(), "Anda Sedang Offline", Toast.LENGTH_SHORT, true).show();
        callTicketLocal();
        fab1.setVisibility(View.GONE);
    }

    private void callTicketLocal(){
        DbHelper dbHelper=new DbHelper(getContext());
        ticketList=dbHelper.selectTicketDetail();

        adapter = new TicketAdapter(getContext(), ticketList);
        adapter.setOnClickListener(this);
        rvTicket.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTicket.setAdapter(adapter);
    }
}
