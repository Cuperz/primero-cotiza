package com.jumpitt.primero_cotiza.model;

import com.google.gson.annotations.SerializedName;

public class Company {

    private int id;
    private String name;
    private String parent;
    @SerializedName("is_online")
    private boolean isOnline;
    @SerializedName("account_id")
    private int accountId;
    @SerializedName("area_id")
    private int areaId;

    public Company(){}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGlosa() {
        return parent;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getAreaId() {return areaId; }
}
