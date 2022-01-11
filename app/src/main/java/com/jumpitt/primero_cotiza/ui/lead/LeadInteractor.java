package com.jumpitt.primero_cotiza.ui.lead;

import com.jumpitt.primero_cotiza.manager.NetworkManager;
import com.jumpitt.primero_cotiza.network.response.BaseResponse;
import com.jumpitt.primero_cotiza.network.response.LeadCheckResponse;
import com.jumpitt.primero_cotiza.network.response.OfferResponse;
import com.jumpitt.primero_cotiza.network.response.RefreshTkResponse;
import com.orhanobut.hawk.Hawk;

import static com.jumpitt.primero_cotiza.utils.Constants.HAWK_FIREBASE_TOKEN;
import static com.jumpitt.primero_cotiza.utils.Constants.HAWK_REFRESH_TOKEN;
import static com.jumpitt.primero_cotiza.utils.Constants.HAWK_TOKEN;
import static com.jumpitt.primero_cotiza.utils.Constants.HAWK_USER_BODY;

public class LeadInteractor implements LeadContract.Interactor {

    private LeadContract.InteractorOutput mInteractorOutput;
    private static final String TAG = LeadInteractor.class.getSimpleName();

    public LeadInteractor(LeadPresenter leadPresenter) {
        mInteractorOutput = leadPresenter;
    }


    //---------------------------FROM CLIENT -----------------------------

    @Override
    public void getOfferId() {

        NetworkManager mNetworkManager = new NetworkManager();

        mNetworkManager.getLeadId(new NetworkManager.Listener<LeadCheckResponse>() {

            @Override
            public void onSuccess(LeadCheckResponse response) {
                getOffer(response.getData().get(0).getOfferId());

            }

            @Override
            public void onError(int code, String message, Throwable t) {
                mInteractorOutput.onError();

            }

            @Override
            public void onSesionExpired() {
                mInteractorOutput.refreshLogin();

            }
        });

    }

    @Override
    public void getOffer(int offerId) {
        NetworkManager mNetworkManager = new NetworkManager();

        mNetworkManager.getOffer(offerId, new NetworkManager.Listener<OfferResponse>() {
            @Override
            public void onSuccess(OfferResponse response) {
                if (response.getData().get(0).getLead() != null) {
                    mInteractorOutput.setInfo(response.getData().get(0).getLead().getAnswerList());
                }
            }

            @Override
            public void onError(int code, String message, Throwable t) {
                mInteractorOutput.onError();
            }

            @Override
            public void onSesionExpired() {
                mNetworkManager.refreshTk(new NetworkManager.RefreshListener<RefreshTkResponse>() {
                    @Override
                    public void onSuccess(RefreshTkResponse response) {
                        getOffer(offerId);
                    }

                    @Override
                    public void onError(int code, String message, Throwable t) {
                        mInteractorOutput.refreshLogin();
                    }
                });


            }
        });

    }

    //-------------------------FROM LEAD ALERT------------------------------------

    @Override
    public void acceptLead() {

        NetworkManager mNetworkManager = new NetworkManager();

        mNetworkManager.getLeadId(new NetworkManager.Listener<LeadCheckResponse>() {


            @Override
            public void onSuccess(LeadCheckResponse response) {
                leadAccepted(response.getData().get(0).getOfferId());
            }

            @Override
            public void onError(int code, String message, Throwable t) {
                mInteractorOutput.onError();

            }

            @Override
            public void onSesionExpired() {
                mInteractorOutput.refreshLogin();

            }
        });

    }

    @Override
    public void leadAccepted(int leadId){

        NetworkManager mNetworkManager = new NetworkManager();

        mNetworkManager.acceptLead(leadId, new NetworkManager.Listener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                mInteractorOutput.onSuccess();
            }

            @Override
            public void onError(int code, String message, Throwable t) {
                mInteractorOutput.callResult(message);
            }

            @Override
            public void onSesionExpired() { }
        });

    }

    @Override
    public void acceptClient(int offerId){

        NetworkManager mNetworkManager = new NetworkManager();

        mNetworkManager.acceptLead(offerId, new NetworkManager.Listener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                mInteractorOutput.onAccepted();
            }

            @Override
            public void onError(int code, String message, Throwable t) {
                mInteractorOutput.callResult(message);
            }

            @Override
            public void onSesionExpired() { }
        });


    }

    @Override
    public void finishSesion() {
        Hawk.delete(HAWK_USER_BODY);
        Hawk.delete(HAWK_TOKEN);
        Hawk.delete(HAWK_REFRESH_TOKEN);
        Hawk.delete(HAWK_FIREBASE_TOKEN);
    }

}

