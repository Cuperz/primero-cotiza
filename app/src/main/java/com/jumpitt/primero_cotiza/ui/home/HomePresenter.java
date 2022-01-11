package com.jumpitt.primero_cotiza.ui.home;

public class HomePresenter implements HomeContract.Presenter, HomeContract.InteractorOutput {

    private static final String TAG = HomePresenter.class.getSimpleName();

    private HomeContract.View mView;
    private HomeContract.Interactor mInteractor;
    private HomeContract.Router mRouter;

    public HomePresenter(HomeActivity homeActivity) {
        mView = homeActivity;
        mInteractor = new HomeInteractor(this);
        mRouter = new HomeRouter(homeActivity);
    }

    @Override
    public void onDestroy() {
        mInteractor = null;
        mRouter = null;
    }
}
