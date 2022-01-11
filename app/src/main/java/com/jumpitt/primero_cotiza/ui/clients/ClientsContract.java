package com.jumpitt.primero_cotiza.ui.clients;

import com.jumpitt.primero_cotiza.model.Client;

import java.util.List;

public interface ClientsContract {

    interface View {

        void setClients(List<Client> clientList);

        void clientsFailed(String message);
    }

    interface Presenter {

        void getClients();

        void toClient(int clientId);
    }

    interface Interactor {

        void getClients();

        void finishSesion();
    }

    interface InteractorOutput {

        void setClients(List<Client> clientList);

        void clientsFailed(String message);

        void refreshToLogin();
    }

    interface Router {

        void toClient(int clientId);

        void toLogin();
    }
}
