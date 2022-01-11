package com.jumpitt.primero_cotiza.ui.lead;


import com.jumpitt.primero_cotiza.App;
import com.jumpitt.primero_cotiza.R;
import com.jumpitt.primero_cotiza.model.LeadInfo;

import java.util.ArrayList;
import java.util.List;

public class LeadPresenter implements LeadContract.Presenter, LeadContract.InteractorOutput {

    private LeadContract.View mView;
    private LeadContract.Interactor mInteractor;
    private LeadContract.Router mRouter;
    public ArrayList<LeadInfo> mInfoList = new ArrayList<>();

    private static final String TAG = LeadPresenter.class.getSimpleName();

    public LeadPresenter(LeadActivity leadActivity) {
        mView = leadActivity;
        mInteractor = new LeadInteractor(this);
        mRouter = new LeadRouter(leadActivity);

    }

    @Override
    public void getInfo() {
        mInteractor.getOfferId();
    }

    @Override
    public void getClient(int clientId) {
        if (clientId != -1) {
            mInteractor.getOffer(clientId);
        } else {
            toHome();
        }

    }

    @Override
    public void toHome() {
        mRouter.toHome();
    }

    @Override
    public void callClient(String number, int clientId) {
        mInteractor.acceptClient(clientId);
        if (number!=null && number.length() > 7) {
            mRouter.makeCall(number);
        }else {
            mView.onError(App.getContext().getResources().getString(R.string.lead_invalid_number));
        }
    }

    @Override
    public void makeCall(String number) {
        mInteractor.acceptLead();
        if (number!=null && number.length() > 7) {
            mRouter.makeCall(number);
        }else {
            mView.onError(App.getContext().getResources().getString(R.string.lead_accepted_invalid_number));
        }
    }

    @Override
    public void sendWsp(String number) {
        mRouter.sendWsp(number);
    }

    @Override
    public void sendEmail(String email) {
        ArrayList<String> recipes = new ArrayList<>();
        recipes.add(email);
        mRouter.sendEmail(email);
    }


    //--------------------IntOut----------------------

    @Override
    public void setInfo(List<LeadInfo> leadInfos) {
        String name = "";
        String email = "";
        String phone = "";

        for (LeadInfo lead : leadInfos) {
            if (lead.getTittle() != null && lead.getTittle().equals("Nombre")) {
                name = lead.getSubTittle() != null ? lead.getSubTittle() : "";
            } else if (lead.getTittle() != null && lead.getTittle().equals("Email")) {
                email = lead.getSubTittle() != null ? lead.getSubTittle() : "";
            }
            else if (lead.getTittle() != null && lead.getTittle().equals("Teléfono")) {
                phone = lead.getSubTittle() != null ? lead.getSubTittle() : "";
            }else {
                mInfoList.add(lead);
            }
        }
        mView.setInfo(name, email, phone, mInfoList);
    }

    @Override
    public void refreshLogin() {
        mInteractor.finishSesion();
        mRouter.toLogin();
    }

    @Override
    public void onError() {
        mView.onError(App.getContext().getResources().getString(R.string.lead_error));
        mRouter.toHome();
    }

    @Override
    public void onSuccess(){
        mView.onSuccess("Lead aceptado correctamente");
    }

    @Override
    public void callResult(String message){
        mView.onError(message);
    }

    @Override
    public void onAccepted() {
        mView.onAccepted();
    }

}
