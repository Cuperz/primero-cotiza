package com.jumpitt.primero_cotiza.network.response;

import com.jumpitt.primero_cotiza.model.UserProfile;

import java.util.List;

public class ProfileResponse extends BaseResponse{

    private List<UserProfile> data;

    public ProfileResponse(){}

    public List<UserProfile> getData() {
        return data;
    }
}
