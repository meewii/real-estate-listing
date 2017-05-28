package com.immo.realestatelistingapp.models;

import org.json.JSONObject;

/**
 *  {
 *      "address": "Bergmannstraße 33, 10961 Berlin",
 *      "latitude": 52.488886876519175,
 *      "longitude": 13.397688051882763
 *  }
 */
public class Location {

    private String mAddress;
    private double mLatitude;
    private double mLongitude;

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public static Location fromJsonObject(JSONObject jsonObject) {
        if(jsonObject == null) return null;

        Location location = new Location();
        if(jsonObject.has("address")) location.setAddress(jsonObject.optString("address"));
        if(jsonObject.has("latitude")) location.setLatitude(jsonObject.optDouble("latitude"));
        if(jsonObject.has("longitude")) location.setLongitude(jsonObject.optDouble("longitude"));

        return location;
    }
}
