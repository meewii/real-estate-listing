package com.immo.realestatelistingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.immo.realestatelistingapp.adapters.RealEstateAdapter;
import com.immo.realestatelistingapp.models.ListItem;
import com.immo.realestatelistingapp.tasks.RealEstateListTask;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<ListItem> realEstateList = new ArrayList<>();
        RealEstateAdapter adapter = new RealEstateAdapter(getApplicationContext(), realEstateList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listView);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        // TODO: fetch and persist data before displaying this Activity.

        // Start AsyncTask that loads and displays list of real estates
        new RealEstateListTask(
                getApplicationContext(),
                realEstateList,
                adapter
        ).execute();
    }

}
