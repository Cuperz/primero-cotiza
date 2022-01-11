package com.jumpitt.primero_cotiza.ui.lost_lead;

public class LostLeadPresenter implements LostLeadContract.Presenter, LostLeadContract.InteractorOutput {

    private LostLeadContract.View mView;
    private LostLeadContract.Interactor mInteractor;
    private LostLeadContract.Router mRouter;

    private static final String TAG = LostLeadPresenter.class.getSimpleName();

    public LostLeadPresenter(LostLeadActivity lostLeadActivity) {
        mView = lostLeadActivity;
        mInteractor = new LostLeadInteractor(this);
        mRouter = new LostLeadRouter(lostLeadActivity);

    }

    @Override
    public void toHome() {
        mRouter.toHome();
    }
}
