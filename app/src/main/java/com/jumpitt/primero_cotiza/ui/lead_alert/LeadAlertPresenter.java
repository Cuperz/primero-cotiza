package com.jumpitt.primero_cotiza.ui.lead_alert;

public class LeadAlertPresenter implements LeadAlertContract.Presenter, LeadAlertContract.InteractorOutput {

    private LeadAlertContract.View mView;
    private LeadAlertContract.Interactor mInteractor;
    private LeadAlertContract.Router mRouter;

    private static final String TAG = LeadAlertPresenter.class.getSimpleName();

    public LeadAlertPresenter(LeadAlertActivity activity) {
        mView = activity;
        mInteractor = new LeadAlertInteractor(this);
        mRouter = new LeadAlertRouter(activity);

    }

    @Override
    public void acceptLead(){
        mInteractor.acceptLead();
    }

    @Override
    public void leadRejected(){
        mInteractor.rejectId();
        mRouter.leadRejected();
    }

    @Override
    public void getTimer() {
        mInteractor.getTimer();
    }

    @Override
    public void toLostLead() {
        mRouter.toLostLead();
    }

    //-------------------INT OUT--------------------

    @Override
    public void toAcceptedLead(){
        mRouter.toAcceptedLead();
    }

    @Override
    public void setTimer(int timeLeft){
        mView.timerBegin(timeLeft);
    }

}
