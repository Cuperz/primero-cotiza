package com.jumpitt.primero_cotiza.ui.base_viper;

public class Presenter implements Contract.Presenter, Contract.InteractorOutput {

    private Contract.View view;
    private Contract.Interactor interactor;
    private Contract.Router router;

    private static final String TAG = Presenter.class.getSimpleName();

    public Presenter(Activity activity) {
        view = activity;
        interactor = new Interactor(this);
        router = new Router(activity);

    }

}
