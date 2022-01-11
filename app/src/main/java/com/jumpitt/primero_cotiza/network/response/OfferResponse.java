package com.jumpitt.primero_cotiza.network.response;

import com.jumpitt.primero_cotiza.model.LeadAnswer;

import java.util.List;

public class OfferResponse extends BaseResponse {

    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public class Data {

        private LeadAnswer lead;

        public LeadAnswer getLead() {
            return lead;
        }


    }

}
