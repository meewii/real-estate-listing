package com.immo.realestatelistingapp.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.immo.realestatelistingapp.core.App;
import com.immo.realestatelistingapp.models.RealEstate;
import com.immo.realestatelistingapp.network.models.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RealEstateParser {

    public static ArrayList<RealEstate> parse(@NonNull Response response) {
        String tag = "RealEstateParser.parse - ";

        if(response.getCode() < 200 && response.getCode() > 300) {
            Log.e(App.TAG, tag + "Response code is not OK");
            return null;
        }

        if(response.getContent() == null) {
            Log.e(App.TAG, tag + "Error: Couldn't parse null content!");
            return null;
        }

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
