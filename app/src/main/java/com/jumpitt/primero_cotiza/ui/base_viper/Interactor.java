package com.jumpitt.primero_cotiza.ui.base_viper;

public class Interactor implements Contract.Interactor {

    private Contract.InteractorOutput interactorOutput;
    private static final String TAG = Interactor.class.getSimpleName();

    public Interactor(Presenter presenter) {
        interactorOutput = presenter;
    }

}
