package com.example.user_pc.semnas_ti.admin.dataadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.admin.dataadmin.addadmin.AddAdminActivity;
import com.example.user_pc.semnas_ti.admin.dataadmin.detaildataadmin.DetailDataAdminActivity;
import com.example.user_pc.semnas_ti.api.ApiClient;
import com.example.user_pc.semnas_ti.model.DataAdmin;

import java.util.List;

public class AdminFragment extends Fragment implements AdminAdapter.OnClickListener, AdminView {
    List<DataAdmin> dataAdmins;
    private RecyclerView rvDataAdmin;
    private TextView tvKosong;
    private AdminAdapter adapter;
    private AdminPresenter presenter;
    ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin, container, false);
        rvDataAdmin = view.findViewById(R.id.rv_data_admin);
        tvKosong = view.findViewById(R.id.tv_admin_null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Now Loading...");

        presenter = new AdminPresenter(this, ApiClient.getService(getContext()));
        presenter.dataAdmin();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_add_admin_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_admin:
                Intent intent = new Intent(getContext(), AddAdminActivity.class);
                startActivity(intent);

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onClick(int position) {
        DataAdmin dataAdmin = dataAdmins.get(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable(DetailDataAdminActivity.KEY_ADMIN, dataAdmin);
        Intent intent = new Intent(getContext(), DetailDataAdminActivity.class);
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
    public void onSuccess(List<DataAdmin> dataAdminList) {
        if (dataAdminList.isEmpty()){
            tvKosong.setVisibility(View.VISIBLE);
            rvDataAdmin.setVisibility(View.GONE);
        }else {
            tvKosong.setVisibility(View.GONE);
            rvDataAdmin.setVisibility(View.VISIBLE);
            this.dataAdmins = dataAdminList;
            adapter = new AdminAdapter(getContext(), dataAdminList);
            adapter.setOnClickListener(this);
            rvDataAdmin.setLayoutManager(new LinearLayoutManager(getContext()));
            rvDataAdmin.setAdapter(adapter);
        }
    }

    @Override
    public void onError() {

    }

    @Override
    public void onFailure(Throwable t) {

    }
}
