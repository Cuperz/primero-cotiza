package com.jumpitt.primero_cotiza.ui.profile;

public interface ProfileContract {

    interface View {

        void profileFailed(String message);
        void setUser();

        void onLogOutError();
    }

    interface Presenter {

        void getProfileData();

        void doLogout();
    }

    interface Interactor {

        void getProfileData();

        void doLogout();

        void deleteUserData();
    }

    interface InteractorOutput {

        void profileFailed(String message);

        void profileSuccess();

        void refreshToLogin();

        void onLogoutSuccess();

        void logOutError();
    }

    interface Router {

        void doLogout();
    }
}
