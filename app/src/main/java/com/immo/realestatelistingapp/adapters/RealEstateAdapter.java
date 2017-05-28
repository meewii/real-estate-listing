package com.immo.realestatelistingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.immo.realestatelistingapp.R;
import com.immo.realestatelistingapp.core.App;
import com.immo.realestatelistingapp.models.RealEstate;
import com.immo.realestatelistingapp.tasks.DownloadImageTask;

import java.util.List;

public class RealEstateAdapter extends RecyclerView.Adapter<RealEstateAdapter.RealEstateHolder> {

    private List<RealEstate> mRealEstates;

    public RealEstateAdapter(List<RealEstate> realEstates) {
        this.mRealEstates = realEstates;
    }

    @Override
    public RealEstateHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_real_estate, parent, false);

        return new RealEstateHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RealEstateHolder holder, int position) {
        String tag = "RealEstateAdapter.onBindViewHolder - ";

        RealEstate realEstate = mRealEstates.get(position);

        if(realEstate.getTitle() != null) {
            holder.title.setText(realEstate.getTitle());
        } else {
            holder.title.setText("No title");
        }

        if(realEstate.getPrice() > 0.0) {
            String price = String.valueOf(realEstate.getPrice()) + "€";
            Log.i(App.TAG, tag + "RealEstate price "+ price);

            holder.price.setText(price);
            holder.price.setVisibility(View.VISIBLE);
        } else {
            holder.price.setVisibility(View.GONE);
        }

        if(realEstate.getLocation() != null && realEstate.getLocation().getAddress() != null) {
            holder.address.setText(realEstate.getLocation().getAddress());
            holder.address.setVisibility(View.VISIBLE);
        } else {
            holder.address.setVisibility(View.GONE);
        }

        if(realEstate.getImages() != null && realEstate.getImages().size() > 0) {
            // TODO: keep bitmap persisted, do not download twice the same image
            new DownloadImageTask(holder.image)
                    .execute(realEstate.getImages().get(0));
        } else {
            holder.image.setImageBitmap(null);
        }

    }

    @Override
    public int getItemCount() {
        return mRealEstates.size();
    }


    class RealEstateHolder extends RecyclerView.ViewHolder {
        TextView title, price, address;
        ImageView image;

        RealEstateHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            price = (TextView) view.findViewById(R.id.price);
            address = (TextView) view.findViewById(R.id.address);
            image = (ImageView) view.findViewById(R.id.image);
        }
    }
}