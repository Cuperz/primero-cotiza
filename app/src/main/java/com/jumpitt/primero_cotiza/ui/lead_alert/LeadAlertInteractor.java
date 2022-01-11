package com.jumpitt.primero_cotiza.ui.lead_alert;

import com.jumpitt.primero_cotiza.manager.NetworkManager;
import com.jumpitt.primero_cotiza.network.response.BaseResponse;
import com.jumpitt.primero_cotiza.network.response.LeadCheckResponse;
import com.jumpitt.primero_cotiza.network.response.TimerResponse;

public class LeadAlertInteractor implements LeadAlertContract.Interactor {

    private LeadAlertContract.InteractorOutput mInteractorOutput;
    private static final String TAG = LeadAlertInteractor.class.getSimpleName();

    public LeadAlertInteractor(LeadAlertPresenter presenter) {
        mInteractorOutput = presenter;
    }



    @Override
    public void getTimer() {
        NetworkManager mNetworkManager = new NetworkManager();

        mNetworkManager.getTimer(new NetworkManager.Listener<TimerResponse>() {
            @Override
            public void onSuccess(TimerResponse response) {
                if (!response.getData().isEmpty()){
                    mInteractorOutput.setTimer(response.getData().get(0).getTimeLeft());
                }
                else {
                    mInteractorOutput.toLostLead();
                }
            }

            @Override
            public void onError(int code, String message, Throwable t) {
                if (code != -1){
                    mInteractorOutput.toLostLead();
                }else {
                    mInteractorOutput.leadRejected();
                }
            }

            @Override
            public void onSesionExpired() {}
        });

    }

    @Override
    public void acceptLead() {
        NetworkManager mNetworkManager = new NetworkManager();

        mNetworkManager.getLeadId(new NetworkManager.Listener<LeadCheckResponse>() {
            @Override
            public void onSuccess(LeadCheckResponse response) {
                if(response.getData().isEmpty()){
                    mInteractorOutput.toLostLead();
                }
                else{
                    mInteractorOutput.toAcceptedLead();
                }
            }

            @Override
            public void onError(int code, String message, Throwable t) {
                    mInteractorOutput.leadRejected();
            }

            @Override
            public void onSesionExpired() { }

        });
    }

    @Override
    public void rejectId(){

        NetworkManager mNetworkManager = new NetworkManager();

        mNetworkManager.getLeadId(new NetworkManager.Listener<LeadCheckResponse>() {


            @Override
            public void onSuccess(LeadCheckResponse response) {
                rejectLead(response.getData().get(0).getOfferId());
            }

            @Override
            public void onError(int code, String message, Throwable t) { }

            @Override
            public void onSesionExpired() { }
        });

    }

    private void rejectLead(int leadId){

        NetworkManager mNetworkManager = new NetworkManager();

        mNetworkManager.rejectLead(leadId , new NetworkManager.Listener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {}

            @Override
            public void onError(int code, String message, Throwable t) {}

            @Override
            public void onSesionExpired() {}
        });
    }
}
