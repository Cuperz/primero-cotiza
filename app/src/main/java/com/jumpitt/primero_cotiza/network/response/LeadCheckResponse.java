package com.jumpitt.primero_cotiza.network.response;

import com.jumpitt.primero_cotiza.model.LeadId;

import java.util.List;

public class LeadCheckResponse extends BaseResponse {

    private List<LeadId> data;

    public List<LeadId> getData() {
        return data;
    }
}
