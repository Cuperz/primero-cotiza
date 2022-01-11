package com.jumpitt.primero_cotiza.network.request;

import com.google.gson.annotations.SerializedName;

import static com.jumpitt.primero_cotiza.utils.Constants.CLIENT_ID;
import static com.jumpitt.primero_cotiza.utils.Constants.CLIENT_SECRET;

public class LoginRequest {

    private String username;
    private String password;
    @SerializedName("client_id")
    private int clientId = CLIENT_ID;
    @SerializedName("client_secret")
    private String clientSecret = CLIENT_SECRET;
    @SerializedName("grant_type")
    private String grantType = "password";

    public LoginRequest(String email, String password){
        this.username = email;
        this.password = password;
    }

    public void setEmail(String email) {
        this.username = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
