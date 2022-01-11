package com.jumpitt.primero_cotiza.ui.login;

import android.os.Bundle;

public interface LoginContract {

    interface View {

        void beginTransformation();

        void animate();

        void showInputError();

        void showError(String message);

        void dismissDialog();
    }

    interface Presenter {

        void userSubmit(String email, String password);

        void isLogged();

        boolean validateActivity(Bundle extras);

        void torRecoverPass();

        String getEmail();
    }

    interface Interactor {

        void doLogin(String email, String password);

        void isLogged();

        void sendFbToken();

        void  checkLead();

        void finishSesion();

        String getEmail();
    }

    interface InteractorOutput {

        void loginSuccess();

        void loginFailed(String message);

        void hasToken();

        void hasNoToken();

        void refreshLogin();

        void leadResult(boolean leadResult);
    }

    interface Router {

        void toHome();

        void toLeadAlert();

        void toRecoverPass();

        void toLogin();

        void toLostLead();
    }

}
