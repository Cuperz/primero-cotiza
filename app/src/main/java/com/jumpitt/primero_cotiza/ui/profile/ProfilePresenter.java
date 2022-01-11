package com.jumpitt.primero_cotiza.ui.profile;

import com.jumpitt.primero_cotiza.App;
import com.jumpitt.primero_cotiza.R;

public class ProfilePresenter implements ProfileContract.Presenter, ProfileContract.InteractorOutput {

    private static final String TAG = ProfilePresenter.class.getSimpleName();

    private ProfileContract.View mFragment;
    private ProfileContract.Interactor mInteractor;
    private ProfileContract.Router mRouter;

    public ProfilePresenter(ProfileFragment fragment) {
        mInteractor = new ProfileInteractor(this);
        this.mFragment = fragment;
        this.mRouter = new ProfileRouter(fragment);

    }

    @Override
    public void getProfileData() {
        mInteractor.getProfileData();
    }



    @Override
    public void doLogout() {
        mInteractor.doLogout();
    }


    //-----------------------INTERACTOR-OUTPUT---------------------------//

    @Override
    public void profileFailed(String message) {
        mFragment.profileFailed(message);
    }

    @Override
    public void profileSuccess(){
        mFragment.setUser();
    }

    @Override
    public void refreshToLogin() {
        mFragment.profileFailed(App.getContext().getResources().getString(R.string.sesion_expired));
        mInteractor.deleteUserData();
    }

    @Override
    public void onLogoutSuccess() {
        mRouter.doLogout();
    }

    @Override
    public void logOutError() {
        mFragment.onLogOutError();
    }

}
