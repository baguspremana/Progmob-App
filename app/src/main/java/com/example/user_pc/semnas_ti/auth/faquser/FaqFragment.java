package com.example.user_pc.semnas_ti.auth.faquser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user_pc.semnas_ti.R;
import com.example.user_pc.semnas_ti.api.ApiClient;
import com.example.user_pc.semnas_ti.auth.faquser.detailfaquser.DetailFaqUserActivity;
import com.example.user_pc.semnas_ti.bantuan.DbHelper;
import com.example.user_pc.semnas_ti.model.FaqUserResponse;

import java.util.ArrayList;
import java.util.List;

public class FaqFragment extends Fragment implements FaqAdapter.OnClickListener, FaqView{
    List<FaqUserResponse> faqUserResponses=new ArrayList<>();
    private RecyclerView rvFaq;
    private TextView tvKosong;
    private FaqAdapter adapter;
    private FaqPresenter presenter;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faq, container, false);
        hideKeyboard(((Activity) getActivity()));
        rvFaq = view.findViewById(R.id.rv_faq_user);
        tvKosong = view.findViewById(R.id.tv_faq_user_kosong);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Now Loading...");

        callFAQLocal();
        presenter = new FaqPresenter(this, ApiClient.getService(getContext()));
        presenter.showFaqUser();
    }

    public static void hideKeyboard(Activity activity){
        try{
           InputMethodManager inputManager = (InputMethodManager) activity
                   .getSystemService(Context.INPUT_METHOD_SERVICE);
           View currentFocusedView = activity.getCurrentFocus();
           if (currentFocusedView != null){
               inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
           }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(int position) {
        FaqUserResponse faqUserResponse = faqUserResponses.get(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable(DetailFaqUserActivity.KEY_FAQ, faqUserResponse);
        Intent intent = new Intent(getContext(), DetailFaqUserActivity.class);
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
    public void onSuccess(List<FaqUserResponse> faqUserResponses) {
        if (faqUserResponses.isEmpty()){
            tvKosong.setVisibility(View.VISIBLE);
            rvFaq.setVisibility(View.GONE);
        }else {
            tvKosong.setVisibility(View.GONE);
            rvFaq.setVisibility(View.VISIBLE);
            DbHelper dbHelper = new DbHelper(getContext());

            dbHelper.deleteFAQ();
            this.faqUserResponses = faqUserResponses;

            for (FaqUserResponse faqUserResponse:faqUserResponses){
                dbHelper.insertFAQ(faqUserResponse.getId(), faqUserResponse.getUserId(), faqUserResponse.getQuestion(),
                        faqUserResponse.getAnswer(), faqUserResponse.getUpdatedAt());
            }
            adapter = new FaqAdapter(getContext(), faqUserResponses);
            adapter.setOnClickListener(this);
            rvFaq.setLayoutManager(new LinearLayoutManager(getContext()));
            rvFaq.setAdapter(adapter);

        }
    }

    @Override
    public void onError() {
        Toast.makeText(getContext(), "Response Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure() {
        Toast.makeText(getContext(), "Anda Sedang Offline", Toast.LENGTH_SHORT).show();
    }

    private void callFAQLocal(){
        DbHelper dbHelper=new DbHelper(getContext());
        faqUserResponses=dbHelper.selectFAQ();

        tvKosong.setVisibility(View.GONE);
        adapter = new FaqAdapter(getContext(), faqUserResponses);
        adapter.setOnClickListener(this);
        rvFaq.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFaq.setAdapter(adapter);
    }
}
