package com.jumpitt.primero_cotiza.network.response;

import com.google.gson.annotations.SerializedName;

public class LoginResponse extends BaseResponse{

    @SerializedName("token_type")
    private String tokenType;
    @SerializedName("expires_in")
    private int expiresIn;
    @SerializedName("access_token")
    private String token;
    @SerializedName("refresh_token")
    private String refreshToken;



    public String getTokenType() {
        return tokenType;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
