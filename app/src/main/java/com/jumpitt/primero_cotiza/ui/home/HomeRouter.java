package com.jumpitt.primero_cotiza.ui.home;

public class HomeRouter implements HomeContract.Router {

    private static final String TAG = HomeRouter.class.getSimpleName();

    private HomeContract.View mView;

    public HomeRouter(HomeActivity homeActivity){
        mView = homeActivity;
    }
}
