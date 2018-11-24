package com.example.user_pc.semnas_ti.auth;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.example.user_pc.semnas_ti.R;

public class FaqFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faq, container, false);
        hideKeyboard(((Activity) getActivity()));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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

}
