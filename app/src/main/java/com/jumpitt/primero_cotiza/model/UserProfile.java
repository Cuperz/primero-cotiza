package com.jumpitt.primero_cotiza.model;

import com.google.gson.annotations.SerializedName;

public class UserProfile {

    private String rut;
    private String forenames;
    private String surnames;
    private String email;
    private String phone;
    @SerializedName("current_image")
    private String image;

    public UserProfile(){}

    public String getRut() {
        return rut;
    }
    public String getForenames() {
        return forenames;
    }
    public String getSurnames() {
        return surnames;
    }
    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phone;
    }
    public String getImage() {
        return image;
    }

}
