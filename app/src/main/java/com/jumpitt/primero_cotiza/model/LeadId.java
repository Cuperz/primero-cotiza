package com.jumpitt.primero_cotiza.model;

import com.google.gson.annotations.SerializedName;

public class LeadId {

    @SerializedName("offer_id")
    private int offerId;
    @SerializedName("company_name")
    private String companyName;
    @SerializedName("area_name")
    private String areaName;
    @SerializedName("time_left")
    private int timeLeft;

    public int getOfferId() {
        return offerId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getAreaName() {
        return areaName;
    }

    public int getTimeLeft() {
        return timeLeft;
    }
}
