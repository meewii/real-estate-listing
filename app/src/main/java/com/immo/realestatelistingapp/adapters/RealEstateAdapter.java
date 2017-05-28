package com.immo.realestatelistingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.immo.realestatelistingapp.R;
import com.immo.realestatelistingapp.core.App;
import com.immo.realestatelistingapp.models.Advertisement;
import com.immo.realestatelistingapp.models.ListItem;
import com.immo.realestatelistingapp.models.RealEstate;

import java.util.ArrayList;

public class RealEstateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TYPE_REAL_ESTATE = 0;
    private final int TYPE_ADVERTISEMENT = 1;

    private ArrayList<ListItem> mListItems;
    private Context mContext;

    public RealEstateAdapter(Context context, ArrayList<ListItem> listItems) {
        mContext = context;
        mListItems = listItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_REAL_ESTATE:
            default:
                View view1 = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_real_estate, parent, false);
                return new RealEstateHolder(view1);

            case TYPE_ADVERTISEMENT:
                View view2 = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_advertisment, parent, false);
                return new AdvertHolder(view2);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(mListItems.get(position) instanceof RealEstate) {
            return TYPE_REAL_ESTATE;
        } else {
            return TYPE_ADVERTISEMENT;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_REAL_ESTATE:
                RealEstateHolder realEstateHolder = (RealEstateHolder) holder;
                final RealEstate realEstate = (RealEstate) mListItems.get(position);
                realEstateHolder.bindToRealEstate(mContext, realEstate, new View.OnClickListener() {
                    @Override
                    public void onClick(View starView) {
                        Log.d(App.TAG, "RealEstateAdapter.onBindViewHolder:onClick id: "+realEstate.getId());
                    }
                });
                break;

            case TYPE_ADVERTISEMENT:
                AdvertHolder advertHolder = (AdvertHolder) holder;
                Advertisement advertisement = (Advertisement) mListItems.get(position);
                advertHolder.bindToAdvert(mContext, advertisement);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }

}