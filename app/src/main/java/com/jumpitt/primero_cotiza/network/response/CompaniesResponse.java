package com.jumpitt.primero_cotiza.network.response;

import com.jumpitt.primero_cotiza.model.Company;
import com.jumpitt.primero_cotiza.ui.companies.CompaniesInteractor;

import java.security.PrivateKey;
import java.util.List;

public class CompaniesResponse extends BaseResponse{

    private List<Company> data;

    public CompaniesResponse() {

    }

    public List<Company> getData() {
        return data;
    }
}
