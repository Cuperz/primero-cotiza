package com.jumpitt.primero_cotiza.ui.home;

public class HomeInteractor implements HomeContract.Interactor {

    private static final String TAG = HomeInteractor.class.getSimpleName();

    private HomeContract.InteractorOutput mInteractorOutput;

    public HomeInteractor(HomePresenter presenter){
        mInteractorOutput = presenter;
    }
}
