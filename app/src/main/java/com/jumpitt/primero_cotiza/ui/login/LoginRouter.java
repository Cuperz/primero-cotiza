package com.jumpitt.primero_cotiza.ui.login;

import android.content.Intent;
import android.net.Uri;

import com.jumpitt.primero_cotiza.ui.home.HomeActivity;
import com.jumpitt.primero_cotiza.ui.lead_alert.LeadAlertActivity;
import com.jumpitt.primero_cotiza.ui.lost_lead.LostLeadActivity;

public class LoginRouter implements LoginContract.Router {

    private static final String TAG = LoginRouter.class.getSimpleName();
    private static final String URL = "https://app.primerocotiza.cl/password/reset";
    private int ACTIVITY_RESULT = 999;
    private LoginActivity mView;

    public LoginRouter(LoginActivity activity){
        mView = activity;
    }

    @Override
    public void toHome() {
        Intent i = new Intent(mView, HomeActivity.class);
        mView.startActivity(i);
        mView.finishAffinity();

    }

    @Override
    public void toLeadAlert(){
        Intent i = new Intent(mView,LeadAlertActivity.class);
        mView.startActivity(i);
        mView.finishAffinity();

    }

    @Override
    public void toLostLead() {
        Intent i = new Intent(mView, LostLeadActivity.class);
        mView.startActivity(i);
        mView.finishAffinity();

    }

    @Override
    public void toRecoverPass() {
        final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(URL));
        mView.startActivityForResult(intent,ACTIVITY_RESULT);
    }

    @Override
    public void toLogin() {
        Intent i = new Intent(mView, LoginActivity.class);
        mView.startActivity(i);
        mView.finishAffinity();
    }




}
