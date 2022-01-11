package com.jumpitt.primero_cotiza.ui.lead;

import android.content.Intent;
import android.net.Uri;

import com.jumpitt.primero_cotiza.ui.home.HomeActivity;
import com.jumpitt.primero_cotiza.ui.login.LoginActivity;

import static com.jumpitt.primero_cotiza.ui.login.LoginActivity.SESION_EXPIRED;

public class LeadRouter implements LeadContract.Router {

    private static final String TAG = LeadRouter.class.getSimpleName();
    private LeadActivity mView;
    private int ACTIVITY_RESULT = 999;
    private String SEND_WSP = "https://api.whatsapp.com/send?phone=";
    private String SUBJECT = "Primero Cotiza";

    public LeadRouter(LeadActivity leadActivity) {
        mView = leadActivity;
    }

    @Override
    public void toHome() {
        Intent i = new Intent(mView, HomeActivity.class);
        mView.startActivity(i);
        mView.finishAffinity();

    }

    @Override
    public void makeCall(String number) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+number));
            mView.startActivityForResult(callIntent,ACTIVITY_RESULT);

    }

    @Override
    public void toLogin() {
        Intent i = new Intent(mView, LoginActivity.class);
        i.putExtra("from",SESION_EXPIRED);
        mView.startActivity(i);
        mView.finishAffinity();
    }

    @Override
    public void sendWsp(String number) {
        String url = SEND_WSP+number;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        mView.startActivityForResult(i,ACTIVITY_RESULT);
    }

    @Override
    public void sendEmail(String email) {

        Intent i = new Intent(android.content.Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{email});
        i.putExtra(android.content.Intent.EXTRA_SUBJECT, SUBJECT);
        mView.startActivityForResult(Intent.createChooser(i, "Send mail..."),ACTIVITY_RESULT);
    }

}
