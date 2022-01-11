package com.jumpitt.primero_cotiza.network.request;

import com.google.gson.annotations.SerializedName;

import static com.jumpitt.primero_cotiza.utils.Constants.CLIENT_ID;
import static com.jumpitt.primero_cotiza.utils.Constants.CLIENT_SECRET;

public class RefreshTkRequest {

    @SerializedName("refresh_token")
    private String refreshTk;
    @SerializedName("grant_type")
    private String grantType = "refresh_token";
    @SerializedName("client_id")
    private int client_id = CLIENT_ID;
    @SerializedName("client_secret")
    private String clientSecret = CLIENT_SECRET;

    public RefreshTkRequest(String refreshTk) {
        this.refreshTk = refreshTk;
    }

    public void setRefreshTk(String refreshTk) {
        this.refreshTk = refreshTk;
    }
}
