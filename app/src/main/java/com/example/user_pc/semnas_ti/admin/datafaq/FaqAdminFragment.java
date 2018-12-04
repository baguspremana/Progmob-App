package com.example.user_pc.semnas_ti.admin.datafaq;

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
import com.example.user_pc.semnas_ti.admin.datafaq.adddatafaq.AddFaqAdminActivity;
import com.example.user_pc.semnas_ti.admin.datafaq.detaildatafaq.DetailFaqAdminActivity;
import com.example.user_pc.semnas_ti.api.ApiClient;
import com.example.user_pc.semnas_ti.model.FaqResponse;

import java.util.List;

public class FaqAdminFragment extends Fragment implements FaqAdminAdapter.OnClickListener, FaqAdminView {
    List<FaqResponse> faqResponses;
    private RecyclerView rvDataFAQ;
    private TextView tvKosong;
    private FaqAdminAdapter adapter;
    private FaqAdminPresenter presenter;
    ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faq_admin, container, false);
        rvDataFAQ = view.findViewById(R.id.rv_faq_admin);
        tvKosong = view.findViewById(R.id.tv_faq_kosong);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Now Loading...");

        presenter = new FaqAdminPresenter(this, ApiClient.getService(getContext()));
        presenter.dataFAQ();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_add_faq_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_faq:
                Intent intent = new Intent(getContext(), AddFaqAdminActivity.class);
                startActivity(intent);

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onClick(int position) {
        FaqResponse faqResponse = faqResponses.get(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable(DetailFaqAdminActivity.KEY_ADMIN, faqResponse);
        Intent intent = new Intent(getContext(), DetailFaqAdminActivity.class);
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
    public void onSuccess(List<FaqResponse> faqResponses) {
        if (faqResponses.isEmpty()){
            tvKosong.setVisibility(View.VISIBLE);
            rvDataFAQ.setVisibility(View.GONE);
        }else {
            tvKosong.setVisibility(View.GONE);
            rvDataFAQ.setVisibility(View.VISIBLE);
            this.faqResponses = faqResponses;
            adapter = new FaqAdminAdapter(getContext(), faqResponses);
            adapter.setOnClickListener(this);
            rvDataFAQ.setLayoutManager(new LinearLayoutManager(getContext()));
            rvDataFAQ.setAdapter(adapter);
        }
    }

    @Override
    public void onError() {
        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(Throwable t) {
        Toast.makeText(getContext(), "Failure", Toast.LENGTH_SHORT).show();
    }
}
