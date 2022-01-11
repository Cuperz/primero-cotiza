package com.jumpitt.primero_cotiza.ui.lead_alert;

import android.content.Intent;

import com.jumpitt.primero_cotiza.ui.home.HomeActivity;
import com.jumpitt.primero_cotiza.ui.lead.LeadActivity;
import com.jumpitt.primero_cotiza.ui.lost_lead.LostLeadActivity;

import static com.jumpitt.primero_cotiza.ui.lead.LeadActivity.FROM_ALERT;

public class LeadAlertRouter implements LeadAlertContract.Router {

    private static final String TAG = LeadAlertRouter.class.getSimpleName();
    private LeadAlertActivity mView;

    public LeadAlertRouter(LeadAlertActivity activity) {
        mView = activity;
    }

    @Override
    public void toLostLead() {
        Intent i = new Intent(mView , LostLeadActivity.class);
        mView.startActivity(i);
        mView.finishAffinity();
    }

    @Override
    public void toAcceptedLead(){
        Intent i = new Intent(mView, LeadActivity.class);
        i.putExtra("mode",FROM_ALERT);
        mView.startActivity(i);
        mView.finishAffinity();
    }

    @Override
    public void leadRejected() {
        Intent i = new Intent(mView, HomeActivity.class);
        mView.startActivity(i);
        mView.finishAffinity();
    }
}
