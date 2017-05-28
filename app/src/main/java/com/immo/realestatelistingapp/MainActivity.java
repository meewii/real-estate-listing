package com.immo.realestatelistingapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.immo.realestatelistingapp.core.App;
import com.immo.realestatelistingapp.network.HttpClient;
import com.immo.realestatelistingapp.network.models.Request;
import com.immo.realestatelistingapp.network.models.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new GenerateList().execute();
    }

    private class GenerateList extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            HttpClient httpClient = new HttpClient();
            Response response = httpClient.execute(new Request("GET"));
            if(response != null) {
                Log.i(App.TAG, "MainActivity.doInBackground - response: "+response.getContent());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {

        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... voids) {

        }
    }
}
