package com.jumpitt.primero_cotiza.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LeadAnswer {


    public LeadAnswer() {
    }

    @SerializedName("answers")
    private String answerList;


    public List<LeadInfo> getAnswerList() {

        if (answerList == null || answerList.isEmpty()) return new ArrayList<>();
        Gson gson = new Gson();
        Type type = new TypeToken<List<LeadInfo>>() {
        }.getType();

        return gson.fromJson(answerList, type);
    }
}
