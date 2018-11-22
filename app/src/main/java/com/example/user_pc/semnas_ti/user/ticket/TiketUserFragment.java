package com.example.user_pc.semnas_ti.user.ticket;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.api.ApiClient;
import com.example.user_pc.semnas_ti.model.Ticket;
import com.example.user_pc.semnas_ti.user.addticket.AddTicketAcitivity;
import com.example.user_pc.semnas_ti.user.detailticket.DetailTicketActivity;

import java.util.List;

public class TiketUserFragment extends Fragment implements TicketAdapter.OnClickListener, TicketView{
    List<Ticket> ticketList;
    private RecyclerView rvTicket;
    private TicketAdapter adapter;
    private TicketPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_tiket_user, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Daftar Tiket");
        rvTicket=view.findViewById(R.id.rv_tiket);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        presenter = new TicketPresenter(this, ApiClient.getService(getContext()));
        presenter.getTickets();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_ticket_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_ticket:
                Intent intent = new Intent(getContext(), AddTicketAcitivity.class);
                startActivity(intent);

            default:
                return super.onOptionsItemSelected(item);
        }

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
    public void onSuccess(List<Ticket> ticketList) {
        this.ticketList=ticketList;
        adapter = new TicketAdapter(getContext(), ticketList);
        adapter.setOnClickListener(this);
        rvTicket.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTicket.setAdapter(adapter);
    }

    @Override
    public void onError() {

    }

    @Override
    public void onFailure(Throwable t) {

    }
}
