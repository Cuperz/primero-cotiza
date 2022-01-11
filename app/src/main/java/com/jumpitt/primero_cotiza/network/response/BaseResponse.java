package com.jumpitt.primero_cotiza.network.response;

public class BaseResponse {

    private String error;
    private String message;
    private int code;

    public String getMessage() {
        return message;
    }
    public String getError() {
        return error;
    }
    public int getCode() { return code; }
}
