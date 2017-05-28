package com.immo.realestatelistingapp.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.immo.realestatelistingapp.core.App;
import com.immo.realestatelistingapp.models.RealEstate;
import com.immo.realestatelistingapp.network.HttpClient;
import com.immo.realestatelistingapp.network.RealEstateParser;
import com.immo.realestatelistingapp.network.models.Request;
import com.immo.realestatelistingapp.network.models.Response;

import java.util.ArrayList;

public class RealEstateListTask extends AsyncTask<Object, Object, ArrayList<RealEstate>> {

    private Context mContext;

    public RealEstateListTask(Context context) {
        mContext = context;
    }

    @Override
    protected ArrayList<RealEstate> doInBackground(Object... voids) {
        String tag = "MainActivity.doInBackground - ";

        HttpClient httpClient = new HttpClient();

        // Get list of items from API
        Response response = httpClient.execute(new Request("GET"));
        if(response == null) {
            showToast("Error: The app was not able to fetch list of items.");
            return null;
        }

        Log.i(App.TAG, tag + "Response: "+response.getContent());

        // There should be a parser factory that return the right parser per request but
        // here we know we only get one type of items
        ArrayList<RealEstate> realEstates = RealEstateParser.parse(response);

        if(realEstates == null) {
            showToast("Error: The RealEstateParser was not able to return a valid list of items.");
            return null;
        }

        if(realEstates.size() == 0) {
            showToast("The list is empty :(");
            return null;
        }

        showToast("The list has '"+realEstates.size()+"' items");

        return realEstates;
    }

    @Override
    protected void onPostExecute(ArrayList<RealEstate> realEstates) {
        String tag = "MainActivity.onPostExecute - ";

        if(realEstates == null) {
            showToast(tag + "Error: the list is null.");
            return;
        }

        Log.i(App.TAG, tag + "Items: "+ realEstates.size());
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onProgressUpdate(Object... voids) {

    }

    private void showToast(final String message) {
        Log.e(App.TAG, message);

        Handler h = new Handler(Looper.getMainLooper());
        h.post(new Runnable() {
            public void run() {
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}