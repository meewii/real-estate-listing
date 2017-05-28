package com.immo.realestatelistingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.immo.realestatelistingapp.tasks.RealEstateListTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start AsyncTask to load and display list of real estates
        new RealEstateListTask(getApplicationContext()).execute();
    }

}
