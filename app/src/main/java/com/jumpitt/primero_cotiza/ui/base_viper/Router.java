package com.jumpitt.primero_cotiza.ui.base_viper;

public class Router implements Contract.Router {

    private Contract.View mView;
    private static final String TAG = Router.class.getSimpleName();

    public Router(Activity activity) {
        mView = activity;
    }

}
