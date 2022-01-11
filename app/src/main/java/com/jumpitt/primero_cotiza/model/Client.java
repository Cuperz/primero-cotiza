package com.jumpitt.primero_cotiza.model;

import com.google.gson.annotations.SerializedName;

public class Client {

    @SerializedName("attention_date")
    private String attDate;
    private String forenames;
    private String surnames;
    @SerializedName("area_name")
    private String service;
    @SerializedName("offer_id")
    private int offerId;
    @SerializedName("lead_name")
    private String leadName;
    @SerializedName("offer_status")
    private int offerStatus;


    public Client(){

    }

    public String getAttDate() {
        return attDate;
    }

    public String getForenames() {
        return forenames;
    }

    public String getSurnames() { return surnames; }

    public String getService() {
        return service;
    }

    public int getOfferID() {return offerId;}

    public String getLeadName() {
        return leadName;
    }

    public int getOfferStatus() {
        return offerStatus;
    }
}
