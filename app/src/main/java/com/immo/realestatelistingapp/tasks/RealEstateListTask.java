package com.immo.realestatelistingapp.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.immo.realestatelistingapp.R;
import com.immo.realestatelistingapp.adapters.RealEstateAdapter;
import com.immo.realestatelistingapp.core.App;
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
    private ArrayList<RealEstate> mRealEstateList;
    private RealEstateAdapter mRealEstateAdapter;
    private RecyclerView mRecyclerView;
    private ProgressDialog mProgressDialog;

    public RealEstateListTask(Context context,
                              ArrayList<RealEstate> realEstateList,
                              RealEstateAdapter adapter,
                              RecyclerView recyclerView,
                              ProgressDialog progressDialog) {
        mContext = context;
        mRealEstateList = realEstateList;
        mRealEstateAdapter = adapter;
        mRecyclerView = recyclerView;
        mProgressDialog = progressDialog;
    }

    @Override
    protected void onPreExecute() {
        showProgressDialog();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String tag = "RealEstateListTask.doInBackground - ";

        HttpClient httpClient = new HttpClient();

        // Get list of items from API
        Response response = httpClient.execute(new Request("GET"));
        if(response == null) {
            showToast("Error: The app was not able to fetch list of items.");
            hideProgressDialog();
            return null;
        }

        Log.i(App.TAG, tag + "Response: "+response.getContent());

        mRealEstateList = RealEstateParser.parse(mRealEstateList, response);
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        String tag = "RealEstateListTask.onPostExecute - ";

        if(mRealEstateList == null) {
            showToast("Error: The RealEstateParser was not able to return a valid list of items.");
            hideProgressDialog();
            return;
        }

        if(mRealEstateList.size() == 0) {
            showToast("The list is empty :(");
            hideProgressDialog();
            return;
        }

        Log.i(App.TAG, tag + "Items: "+ mRealEstateList.size());

        if(mRealEstateAdapter != null) {
            mRealEstateAdapter.notifyDataSetChanged();
        }
        hideProgressDialog();
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

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog.setMessage(mContext.getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.show();
        }
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

}