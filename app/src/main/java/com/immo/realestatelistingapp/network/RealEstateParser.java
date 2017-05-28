package com.immo.realestatelistingapp.network;

import android.util.Log;

import com.immo.realestatelistingapp.core.App;
import com.immo.realestatelistingapp.models.RealEstate;
import com.immo.realestatelistingapp.network.models.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RealEstateParser {

    public static ArrayList<RealEstate> parse(Response response) {
        String tag = "RealEstateParser.parse - ";

        JSONArray items = null;
        try {
            JSONObject json = new JSONObject(response.getContent());
            items = json.getJSONArray("items");
        } catch (JSONException e) {
            Log.e(App.TAG, tag + "Error while parsing JSON: " + e.getMessage());
        }

        if(items == null) {
            return null;
        }

        ArrayList<RealEstate> realEstates = new ArrayList<>();

        for(int i=0; i<items.length(); i++) {
            RealEstate realEstate;
            try {
                realEstate = RealEstate.fromJsonObject(items.getJSONObject(i));
            } catch (JSONException e) {
                Log.e(App.TAG, tag + "Error while parsing JSON: " + e.getMessage());
                continue;
            }

            realEstates.add(realEstate);
        }

        return realEstates;
    }

}
