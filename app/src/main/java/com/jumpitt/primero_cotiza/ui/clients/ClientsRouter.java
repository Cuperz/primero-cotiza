package com.jumpitt.primero_cotiza.ui.clients;

import android.content.Intent;

import com.jumpitt.primero_cotiza.ui.lead.LeadActivity;
import com.jumpitt.primero_cotiza.ui.login.LoginActivity;

import java.util.Objects;

import static com.jumpitt.primero_cotiza.ui.lead.LeadActivity.FROM_CLIENT;
import static com.jumpitt.primero_cotiza.ui.login.LoginActivity.SESION_EXPIRED;

public class ClientsRouter implements ClientsContract.Router {

    private static final String TAG = ClientsRouter.class.getSimpleName();

    private ClientsFragment mFragment;

    public ClientsRouter(ClientsFragment fragment) {
        mFragment = fragment;
    }

    @Override
    public void toClient(int clientId) {
        Intent i = new Intent( mFragment.getActivity(), LeadActivity.class);
        i.putExtra("mode",FROM_CLIENT);
        i.putExtra("id",clientId);
        mFragment.startActivity(i);
    }

    @Override
    public void toLogin() {
        Intent i = new Intent( mFragment.getActivity(), LoginActivity.class);
        i.putExtra("from",SESION_EXPIRED);
        mFragment.startActivity(i);
        Objects.requireNonNull(mFragment.getActivity()).finishAffinity();

    }
}

