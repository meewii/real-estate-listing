package com.immo.realestatelistingapp.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.immo.realestatelistingapp.adapters.RealEstateAdapter;
import com.immo.realestatelistingapp.core.App;
import com.immo.realestatelistingapp.models.Advertisement;
import com.immo.realestatelistingapp.models.ListItem;
import com.immo.realestatelistingapp.models.RealEstate;
import com.immo.realestatelistingapp.network.HttpClient;
import com.immo.realestatelistingapp.network.RealEstateParser;
import com.immo.realestatelistingapp.network.models.Request;
import com.immo.realestatelistingapp.network.models.Response;

import java.util.ArrayList;

/**
 * AsyncTask that will allow us to fetch the list of Real Estate items in the background. It will also
 * update the list view with the received items.
 */
public class RealEstateListTask extends AsyncTask<Void, Void, Void> {

    private Context mContext;
    private ArrayList<ListItem> mListItems;
    private RealEstateAdapter mRealEstateAdapter;
    private RecyclerView mRecyclerView;

    public RealEstateListTask(Context context,
                              ArrayList<ListItem> listItems,
                              RealEstateAdapter adapter,
                              RecyclerView recyclerView) {
        mContext = context;
        mListItems = listItems;
        mRealEstateAdapter = adapter;
        mRecyclerView = recyclerView;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String tag = "RealEstateListTask.doInBackground - ";

        HttpClient httpClient = new HttpClient();

        // Get list of items from API
        Response response = httpClient.execute(new Request("GET"));
        if(response == null) {
            showToast("Error: The app was not able to fetch list of items.");
            return null;
        }

        Log.i(App.TAG, tag + "Response: "+response.getContent());

        // Parse json response to pojo
        ArrayList<RealEstate> realEstateList = RealEstateParser.parse(response);

        if(realEstateList != null) {
            mListItems = addAdvertisement(mListItems, realEstateList);
        }

        return null;
    }

    /**
     * Adds an advert in the list every 3rd position
     *
     * @param listItems reference of ListItems that was set in Adapter
     * @param realEstateList list of RealEstates
     * @return a list of RealEstates and Advertisements (both implementing the ListItem interface)
     */
    private ArrayList<ListItem> addAdvertisement(ArrayList<ListItem> listItems, ArrayList<RealEstate> realEstateList) {
        Advertisement advert = new Advertisement();
        advert.setTitle("\uD83D\uDCB8 \uD83D\uDCB8 Buy stuff \uD83D\uDCB8 \uD83D\uDCB8");

        for(int i=0; i<realEstateList.size(); i++) {
            // Every 3rd position in the list
            if(i != 0 && i%2 == 0) {
                listItems.add(advert);
            }
            listItems.add(realEstateList.get(i));
        }

        return listItems;
    }

    @Override
    protected void onPostExecute(Void v) {
        String tag = "RealEstateListTask.onPostExecute - ";

        if(mListItems == null) {
            showToast("Error: The RealEstateParser was not able to return a valid list of items.");
            return;
        }

        if(mListItems.size() == 0) {
            showToast("The list is empty :(");
            return;
        }

        Log.i(App.TAG, tag + "Items: "+ mListItems.size());

        if(mRealEstateAdapter != null) {
            mRealEstateAdapter.notifyDataSetChanged();
        }
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