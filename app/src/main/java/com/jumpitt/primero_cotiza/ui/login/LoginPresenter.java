package com.jumpitt.primero_cotiza.ui.login;

import android.os.Bundle;
import android.util.Patterns;

import com.jumpitt.primero_cotiza.App;
import com.jumpitt.primero_cotiza.R;

import static com.jumpitt.primero_cotiza.ui.login.LoginActivity.ORIGIN_PUSH;
import static com.jumpitt.primero_cotiza.ui.login.LoginActivity.SESION_EXPIRED;

public class LoginPresenter implements LoginContract.Presenter, LoginContract.InteractorOutput {

    private static final String TAG = LoginPresenter.class.getSimpleName();

    private LoginContract.View mView;
    private LoginContract.Interactor mInteractor;
    private LoginContract.Router mRouter;

    public LoginPresenter(LoginActivity activity) {
        mView = activity;
        mInteractor = new LoginInteractor(this);
        mRouter = new LoginRouter(activity);

    }

    @Override
    public void userSubmit(String email, String password) {
        boolean verifyEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        boolean verifyPassword = password.length() > 0;
        if (verifyEmail && verifyPassword)
            mInteractor.doLogin(email, password);
        else {
            mView.dismissDialog();
            mView.showInputError();
        }
    }

    @Override
    public void isLogged() {
        mInteractor.isLogged();
    }

    @Override
    public boolean validateActivity(Bundle extras) {
        if (extras == null || !extras.containsKey("from")) {
            return false;
        }
        int from = extras.getInt("from");
        if (from == ORIGIN_PUSH) {
            mInteractor.checkLead();
            return true;
        } else{
                if (extras.containsKey("from") && extras.getInt("from")==SESION_EXPIRED){
                    mView.showError(App.getContext().getResources().getString(R.string.sesion_expired));
                }
            return false;
        }
    }

    @Override
    public void torRecoverPass() {
        mRouter.toRecoverPass();

    }

    @Override
    public String getEmail() {
        return mInteractor.getEmail();
    }

    //------------------InteractorOutput Methods------------------------

    @Override
    public void loginSuccess() {
        mView.dismissDialog();
        mInteractor.sendFbToken();
        mRouter.toHome();
    }

    @Override
    public void loginFailed(String message) {
        mView.dismissDialog();
        mView.showError(message);
    }

    @Override
    public void hasToken() {
        mRouter.toHome();
    }

    @Override
    public void hasNoToken() {
        mView.animate();
    }

    @Override
    public void refreshLogin() {
        mView.showError(App.getContext().getResources().getString(R.string.sesion_expired));
        mInteractor.finishSesion();
        mRouter.toLogin();
    }

    @Override
    public void leadResult(boolean leadResult) {
        if (leadResult){
            mRouter.toLeadAlert();
        }
        else {
            mRouter.toLostLead();
        }
    }

}
