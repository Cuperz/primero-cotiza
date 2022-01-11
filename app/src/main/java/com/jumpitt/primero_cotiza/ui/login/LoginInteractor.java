package com.jumpitt.primero_cotiza.ui.login;

import com.jumpitt.primero_cotiza.App;
import com.jumpitt.primero_cotiza.R;
import com.jumpitt.primero_cotiza.manager.NetworkManager;
import com.jumpitt.primero_cotiza.network.response.BaseResponse;
import com.jumpitt.primero_cotiza.network.response.LeadCheckResponse;
import com.jumpitt.primero_cotiza.network.response.LoginResponse;
import com.jumpitt.primero_cotiza.network.response.RefreshTkResponse;
import com.jumpitt.primero_cotiza.utils.DeviceRegister;
import com.orhanobut.hawk.Hawk;

import static com.jumpitt.primero_cotiza.utils.Constants.HAWK_FIREBASE_TOKEN;
import static com.jumpitt.primero_cotiza.utils.Constants.HAWK_REFRESH_TOKEN;
import static com.jumpitt.primero_cotiza.utils.Constants.HAWK_TOKEN;
import static com.jumpitt.primero_cotiza.utils.Constants.HAWK_USER_BODY;
import static com.jumpitt.primero_cotiza.utils.Constants.HAWK_USER_EMAIL;
public class LoginInteractor implements LoginContract.Interactor {

    private static final String TAG = LoginInteractor.class.getSimpleName();

    private LoginContract.InteractorOutput mIntrOut;


    public LoginInteractor(LoginContract.InteractorOutput presenter) {
        mIntrOut = presenter;
    }

    @Override
    public void doLogin(String email, String password) {

        NetworkManager mNetworkManager = new NetworkManager();
        mNetworkManager.doLogin(email, password, new NetworkManager.Listener<LoginResponse>() {

            @Override
            public void onSuccess(LoginResponse response) {

                Hawk.put(HAWK_TOKEN, response.getToken());
                Hawk.put(HAWK_REFRESH_TOKEN, response.getRefreshToken());
                Hawk.put(HAWK_USER_EMAIL, email);
                mIntrOut.loginSuccess();
            }

            @Override
            public void onError(int code, String message, Throwable t) {

                if (code == -1)
                    mIntrOut.loginFailed(App.getContext().getResources().getString(R.string.conection_error));
                else
                    mIntrOut.loginFailed(message);
            }

            @Override
            public void onSesionExpired() {
                mIntrOut.loginFailed(App.getContext().getResources().getString(R.string.login_invalid_credentials));
            }


        });
    }

    @Override
    public void isLogged() {
        if (Hawk.contains(HAWK_REFRESH_TOKEN))
            mIntrOut.hasToken();
        else
            mIntrOut.hasNoToken();
    }

    @Override
    public void sendFbToken() {

        DeviceRegister.getInstance().registerDevice();

        NetworkManager mNetworkManager = new NetworkManager();
        mNetworkManager.sendFbToken(new NetworkManager.Listener<BaseResponse>() {

            @Override
            public void onSuccess(BaseResponse response) {
            }

            @Override
            public void onError(int code, String message, Throwable t) {
                mIntrOut.refreshLogin();

            }

            @Override
            public void onSesionExpired() {
                mNetworkManager.refreshTk(new NetworkManager.RefreshListener<RefreshTkResponse>() {
                    @Override
                    public void onSuccess(RefreshTkResponse response) {
                        sendFbToken();
                    }

                    @Override
                    public void onError(int code, String message, Throwable t) {
                        mIntrOut.refreshLogin();
                    }
                });
            }

        });
    }

    @Override
    public void checkLead() {
        NetworkManager mNetworkManager = new NetworkManager();

        mNetworkManager.getLeadId(new NetworkManager.Listener<LeadCheckResponse>() {
            @Override
            public void onSuccess(LeadCheckResponse response) {
                if (!response.getData().isEmpty() && response.getData().get(0).getTimeLeft() > 0) {
                    mIntrOut.leadResult(true);
                } else {
                    mIntrOut.leadResult(false);
                }

            }

            @Override
            public void onError(int code, String message, Throwable t) {

            }

            @Override
            public void onSesionExpired() {
            }
        });


    }

    @Override
    public void finishSesion() {
        Hawk.delete(HAWK_USER_BODY);
        Hawk.delete(HAWK_TOKEN);
        Hawk.delete(HAWK_REFRESH_TOKEN);
        Hawk.delete(HAWK_FIREBASE_TOKEN);
    }

    @Override
    public String getEmail() {
        if (Hawk.contains(HAWK_USER_EMAIL)) {
            return Hawk.get(HAWK_USER_EMAIL);
        }
        else return "";
    }
}


