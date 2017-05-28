package com.immo.realestatelistingapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.immo.realestatelistingapp.core.App;
import com.immo.realestatelistingapp.models.RealEstate;
import com.immo.realestatelistingapp.network.HttpClient;
import com.immo.realestatelistingapp.network.RealEstateParser;
import com.immo.realestatelistingapp.network.models.Request;
import com.immo.realestatelistingapp.network.models.Response;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new GenerateList().execute();
    }

    private class GenerateList extends AsyncTask<Object, Object, ArrayList<RealEstate>> {

        @Override
        protected ArrayList<RealEstate> doInBackground(Object... voids) {
            String tag = "MainActivity.doInBackground - ";

            HttpClient httpClient = new HttpClient();
            Response response = httpClient.execute(new Request("GET"));

            if(response == null) {
                showToast("Error: The app was not able to get list from connection.");
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

            return realEstates;
        }

        @Override
        protected void onPostExecute(ArrayList<RealEstate> realEstates) {
            String tag = "MainActivity.onPostExecute - ";
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
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
