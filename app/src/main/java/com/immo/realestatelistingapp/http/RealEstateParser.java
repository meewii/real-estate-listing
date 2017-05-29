package com.immo.realestatelistingapp.http;

import android.support.annotation.NonNull;
import android.util.Log;

import com.immo.realestatelistingapp.core.App;
import com.immo.realestatelistingapp.models.RealEstate;
import com.immo.realestatelistingapp.http.models.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The RealEstateParser takes the Http response and transforms it into Java object.
 */
public class RealEstateParser {

    public static ArrayList<RealEstate> parse(@NonNull Response response) {
        String tag = "RealEstateParser.parse - ";

        if(response.getCode() < 200 && response.getCode() > 300) {
            Log.e(App.TAG, tag + "Response code is not OK");
            return null;
        }

        // Edge case: if response is OK, content should be set
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

        ArrayList<RealEstate> realEstateList = new ArrayList<>();

        for(int i=0; i<items.length(); i++) {
            RealEstate realEstate;
            try {
                realEstate = RealEstate.fromJsonObject(items.getJSONObject(i));
            } catch (JSONException e) {
                Log.e(App.TAG, tag + "Error while parsing JSON: " + e.getMessage());
                continue;
            }

            realEstateList.add(realEstate);
        }

        return realEstateList;
    }

}
