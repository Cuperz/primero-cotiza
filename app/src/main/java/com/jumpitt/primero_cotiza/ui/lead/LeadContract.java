package com.jumpitt.primero_cotiza.ui.lead;

import com.jumpitt.primero_cotiza.model.LeadInfo;

import java.util.ArrayList;
import java.util.List;

public interface LeadContract {

    interface View {



        void setInfo(String name, String email, String phone, List<LeadInfo> leadInfos);

        void onError(String message);

        void onSuccess(String message);

        void onAccepted();
    }

    interface Presenter {

        void getInfo();

        void getClient(int clientId);

        void toHome();

        void callClient(String number, int clientId);

        void makeCall(String numbern);

        void sendWsp(String number);

        void sendEmail(String email);
    }

    interface Interactor {

        void getOfferId();

        void getOffer(int offerId);

        void leadAccepted(int leadId);

        void acceptClient(int offerId);

        void finishSesion();

        void acceptLead();
    }

    interface InteractorOutput {

        void setInfo(List<LeadInfo> leadInfos);

        void refreshLogin();

        void onError();

        void onSuccess();

        void callResult(String message);

        void onAccepted();

    }

    interface Router {

        void toHome();

        void makeCall(String number);

        void toLogin();

        void sendWsp(String number);

        void sendEmail(String email);
    }

}
