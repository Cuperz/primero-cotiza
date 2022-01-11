package com.jumpitt.primero_cotiza.ui.lost_lead;

public class LostLeadInteractor implements LostLeadContract.Interactor {

    private LostLeadContract.InteractorOutput mInteractorOutput;
    private static final String TAG = LostLeadInteractor.class.getSimpleName();

    public LostLeadInteractor(LostLeadPresenter presenter) {
        mInteractorOutput = presenter;
    }

}
