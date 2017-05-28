package com.immo.realestatelistingapp.network.models;

public class Request {

    private String mMethod;

    public Request(String method) {
        mMethod = method;
    }

    public String getMethod() {
        return mMethod;
    }

}
