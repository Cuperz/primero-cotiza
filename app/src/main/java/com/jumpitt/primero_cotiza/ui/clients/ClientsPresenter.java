package com.jumpitt.primero_cotiza.ui.clients;

import com.jumpitt.primero_cotiza.model.Client;

import java.util.List;

public class ClientsPresenter implements ClientsContract.Presenter, ClientsContract.InteractorOutput {

    private static final String TAG = ClientsPresenter.class.getSimpleName();

    private ClientsContract.View mFragment;
    private ClientsContract.Interactor mInteractor;
    private ClientsContract.Router mRouter;

    public ClientsPresenter(ClientsFragment fragment) {
        mInteractor = new ClientsInteractor(this);
        this.mFragment = fragment;
        this.mRouter = new ClientsRouter(fragment);

    }

    @Override
    public void getClients() {
        mInteractor.getClients();
    }

    @Override
    public void toClient(int clientId) {
        mRouter.toClient(clientId);
    }


    //---------------------IntOut--------------------------

    @Override
    public void setClients(List<Client> clientList){
        mFragment.setClients(clientList);

    }

    @Override
    public void clientsFailed(String message){
        mFragment.clientsFailed(message);

    }

    @Override
    public void refreshToLogin() {
        mInteractor.finishSesion();
        mRouter.toLogin();
    }

}
