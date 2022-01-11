package com.jumpitt.primero_cotiza.ui.lead_alert;

public interface LeadAlertContract {

    interface View {

        void timerBegin(int timeleft);
    }

    interface Presenter {

        void acceptLead();

        void toLostLead();

        void leadRejected();

        void getTimer();
    }

    interface Interactor {

        void acceptLead();

        void rejectId();

        void getTimer();
    }

    interface InteractorOutput {

        void toLostLead();

        void toAcceptedLead();

        void leadRejected();

        void setTimer(int timeLeft);
    }

    interface Router {

        void toLostLead();

        void toAcceptedLead();

        void leadRejected();
    }

}
