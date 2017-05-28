package com.immo.realestatelistingapp.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * {
 *  "id": 1,
 *  "title": "title of the real estate",
 *  "price": 500,
 *  "location": {
 *      //
 *  },
 *  "images": [
 *      {
 *          "id": 1,
 *          "url": "https://..."
 *      }
 *   ]
 * }
 */
public class RealEstate {

    private int mId;
    private String mTitle;
    private double mPrice;
    private Location mLocation;
    private ArrayList<String> mImages;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        mPrice = price;
    }

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location location) {
        mLocation = location;
    }

    public ArrayList<String> getImages() {
        return mImages;
    }

    public void setImages(ArrayList<String> images) {
        mImages = images;
    }

    public static RealEstate fromJsonObject(JSONObject jsonObject) throws JSONException {
        if(jsonObject == null) return null;

        RealEstate realEstate = new RealEstate();
        if(jsonObject.has("id")) realEstate.setId(jsonObject.optInt("id"));
        if(jsonObject.has("title")) realEstate.setTitle(jsonObject.optString("title"));
        if(jsonObject.has("price")) realEstate.setPrice(jsonObject.optDouble("price"));

        if(jsonObject.has("location")) {
            Location location = Location.fromJsonObject(jsonObject.optJSONObject("location"));
            realEstate.setLocation(location);
        }

        if(jsonObject.has("images")) {
            ArrayList<String> images = new ArrayList<>();

            JSONArray imageArr = jsonObject.getJSONArray("images");
            if(imageArr != null) {
                for(int i=0; i<imageArr.length(); i++) {
                    JSONObject image = imageArr.getJSONObject(i);
                    if(image.has("url")) {
                        images.add(image.optString("url"));
                    }
                }
            }

            realEstate.setImages(images);
        }

        return realEstate;
    }
}
