package com.jumpitt.primero_cotiza.ui.lost_lead;

import android.content.Intent;

import com.jumpitt.primero_cotiza.ui.home.HomeActivity;

public class LostLeadRouter implements LostLeadContract.Router {

    private LostLeadActivity mView;
    private static final String TAG = LostLeadRouter.class.getSimpleName();

    public LostLeadRouter(LostLeadActivity lostLeadActivity) {
        mView = lostLeadActivity;
    }

    @Override
    public void toHome() {
        Intent i = new Intent(mView, HomeActivity.class);
        mView.startActivity(i);
        mView.finishAffinity();

    }
}
