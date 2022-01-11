package com.jumpitt.primero_cotiza.ui.profile;

import com.jumpitt.primero_cotiza.manager.NetworkManager;
import com.jumpitt.primero_cotiza.model.UserProfile;
import com.jumpitt.primero_cotiza.network.response.BaseResponse;
import com.jumpitt.primero_cotiza.network.response.ProfileResponse;
import com.jumpitt.primero_cotiza.network.response.RefreshTkResponse;
import com.orhanobut.hawk.Hawk;

import static com.jumpitt.primero_cotiza.utils.Constants.HAWK_FIREBASE_TOKEN;
import static com.jumpitt.primero_cotiza.utils.Constants.HAWK_REFRESH_TOKEN;
import static com.jumpitt.primero_cotiza.utils.Constants.HAWK_TOKEN;
import static com.jumpitt.primero_cotiza.utils.Constants.HAWK_USER_BODY;

public class ProfileInteractor implements ProfileContract.Interactor{

    private static final String TAG = ProfileInteractor.class.getSimpleName();

    private ProfileContract.InteractorOutput mInteractorOutput;

    public ProfileInteractor(ProfileContract.InteractorOutput mIntOut) {
        this.mInteractorOutput = mIntOut;
    }


    @Override
    public void getProfileData() {

        if(Hawk.contains(HAWK_USER_BODY))
            mInteractorOutput.profileSuccess();

        else {

            NetworkManager mNetworkManager = new NetworkManager();
            mNetworkManager.getProfile(new NetworkManager.Listener<ProfileResponse>() {
                @Override
                public void onSuccess(ProfileResponse response) {

                    UserProfile user = response.getData().get(0);
                    Hawk.put(HAWK_USER_BODY, user);

                    mInteractorOutput.profileSuccess();
                }

                @Override
                public void onError(int code, String message, Throwable t) {
                    if (code == -1)
                        mInteractorOutput.profileFailed(t.getMessage());
                    else
                        mInteractorOutput.profileFailed(message);
                }

                @Override
                public void onSesionExpired() {
                    mNetworkManager.refreshTk(new NetworkManager.RefreshListener<RefreshTkResponse>() {
                        @Override
                        public void onSuccess(RefreshTkResponse response) {
                            getProfileData();
                        }

                        @Override
                        public void onError(int code, String message, Throwable t) {
                            mInteractorOutput.refreshToLogin();
                        }
                    });
                }
            });
        }

    }

    @Override
    public void doLogout() {

        NetworkManager mNetworkManager = new NetworkManager();
        mNetworkManager.logOut(new NetworkManager.Listener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse response) {
                deleteUserData();
            }

            @Override
            public void onError(int code, String message, Throwable t) {
                mInteractorOutput.logOutError();
            }

            @Override
            public void onSesionExpired() {}
        });

    }

    public void deleteUserData(){
        Hawk.delete(HAWK_USER_BODY);
        Hawk.delete(HAWK_TOKEN);
        Hawk.delete(HAWK_REFRESH_TOKEN);
        Hawk.delete(HAWK_FIREBASE_TOKEN);
        mInteractorOutput.onLogoutSuccess();
    }

}
