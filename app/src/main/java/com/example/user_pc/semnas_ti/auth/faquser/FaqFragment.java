package com.example.user_pc.semnas_ti.auth.faquser;

import android.app.Activity;
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
import com.example.user_pc.semnas_ti.model.FaqUserResponse;

import java.util.List;

public class FaqFragment extends Fragment implements FaqAdapter.OnClickListener, FaqView{
    List<FaqUserResponse> faqUserResponses;
    private RecyclerView rvFaq;
    private TextView tvKosong;
    private FaqAdapter adapter;
    private FaqPresenter presenter;

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
        Toast.makeText(getContext(), "Now Loading", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {
        Toast.makeText(getContext(), "Loaded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(List<FaqUserResponse> faqUserResponses) {
        if (faqUserResponses.isEmpty()){
            tvKosong.setVisibility(View.VISIBLE);
            rvFaq.setVisibility(View.GONE);
        }else {
            tvKosong.setVisibility(View.GONE);
            rvFaq.setVisibility(View.VISIBLE);
            this.faqUserResponses = faqUserResponses;
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
    public void onFailure(Throwable t) {
        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
    }
}
