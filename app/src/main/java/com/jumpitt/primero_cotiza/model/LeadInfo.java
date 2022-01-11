package com.jumpitt.primero_cotiza.model;

import com.google.gson.annotations.SerializedName;

public class LeadInfo {

    @SerializedName("name_attribute")
    private String tittle;
    @SerializedName("response")
    private String subTittle;

    public LeadInfo(String tittle, String subTittle) {
        this.tittle = tittle;
        this.subTittle = subTittle;
    }

    public String getTittle() {
        return tittle;
    }

    public String getSubTittle() {
        return subTittle;
    }

}
