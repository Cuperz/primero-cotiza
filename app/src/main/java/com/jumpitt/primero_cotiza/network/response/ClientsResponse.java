package com.jumpitt.primero_cotiza.network.response;

import com.google.gson.annotations.SerializedName;
import com.jumpitt.primero_cotiza.model.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientsResponse extends BaseResponse {

    private ClientsData data;


    public ClientsData getData() {
            return data;
        }

    public class ClientsData {


        @SerializedName("data")
        private List<Client> clientList;

        public List<Client> getClientList() {
            return clientList;
        }


    }

}
