package com.jumpitt.primero_cotiza.ui.clients;


import com.jumpitt.primero_cotiza.App;
import com.jumpitt.primero_cotiza.R;
import com.jumpitt.primero_cotiza.manager.NetworkManager;
import com.jumpitt.primero_cotiza.network.response.ClientsResponse;
import com.jumpitt.primero_cotiza.network.response.RefreshTkResponse;
import com.orhanobut.hawk.Hawk;

import static com.jumpitt.primero_cotiza.utils.Constants.HAWK_FIREBASE_TOKEN;
import static com.jumpitt.primero_cotiza.utils.Constants.HAWK_REFRESH_TOKEN;
import static com.jumpitt.primero_cotiza.utils.Constants.HAWK_TOKEN;
import static com.jumpitt.primero_cotiza.utils.Constants.HAWK_USER_BODY;

public class ClientsInteractor implements ClientsContract.Interactor {

    private static final String TAG = ClientsInteractor.class.getSimpleName();

    private ClientsContract.InteractorOutput mInteractorOutput;
    NetworkManager mNetworkManager = new NetworkManager();

    public ClientsInteractor(ClientsContract.InteractorOutput output) {
        this.mInteractorOutput = output;
    }

    @Override
    public void getClients() {
        mNetworkManager.getClients(new NetworkManager.Listener<ClientsResponse>() {
            @Override
            public void onSuccess(ClientsResponse response) {

                mInteractorOutput.setClients(response.getData().getClientList());

            }

            @Override
            public void onError(int code, String message, Throwable t) {
                if (code == -1)
                    mInteractorOutput.clientsFailed(App.getContext().getResources().getString(R.string.conection_error));
                else
                    mInteractorOutput.clientsFailed(message);
            }

            @Override
            public void onSesionExpired() {
                mNetworkManager.refreshTk(new NetworkManager.RefreshListener<RefreshTkResponse>() {
                    @Override
                    public void onSuccess(RefreshTkResponse response) {
                        getClients();
                    }

                    @Override
                    public void onError(int code, String message, Throwable t) {
                        mInteractorOutput.refreshToLogin();
                    }
                });
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
}
