package com.jumpitt.primero_cotiza.network.response;

import com.google.gson.annotations.SerializedName;

public class RefreshTkResponse extends BaseResponse {

    @SerializedName("access_token")
    private String accToken;
    @SerializedName("refresh_token")
    private String refreshToken;

    public String getAccToken() {
        return accToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
