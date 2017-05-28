package com.immo.realestatelistingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.immo.realestatelistingapp.R;
import com.immo.realestatelistingapp.models.Advertisement;
import com.immo.realestatelistingapp.models.ListItem;
import com.immo.realestatelistingapp.models.RealEstate;
import com.immo.realestatelistingapp.tasks.DownloadImageTask;

import java.util.ArrayList;

public class RealEstateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TYPE_REAL_ESTATE = 0;
    private final int TYPE_ADVERTISEMENT = 1;

    private ArrayList<ListItem> mListItems;

    public RealEstateAdapter(ArrayList<ListItem> listItems) {
        this.mListItems = listItems;
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
                return new AdvertisementHolder(view2);
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

                RealEstate realEstate = (RealEstate) mListItems.get(position);

                if(realEstate.getTitle() != null) {
                    realEstateHolder.title.setText(realEstate.getTitle());
                } else {
                    realEstateHolder.title.setText(R.string.unknown_title);
                }

                if(realEstate.getPrice() > 0.0) {
                    String price = String.valueOf(realEstate.getPrice()) + "€";

                    realEstateHolder.price.setText(price);
                    realEstateHolder.price.setVisibility(View.VISIBLE);
                } else {
                    realEstateHolder.price.setVisibility(View.GONE);
                }

                if(realEstate.getLocation() != null && realEstate.getLocation().getAddress() != null) {
                    realEstateHolder.address.setText(realEstate.getLocation().getAddress());
                    realEstateHolder.address.setVisibility(View.VISIBLE);
                } else {
                    realEstateHolder.address.setVisibility(View.GONE);
                }

                if(realEstate.getImages() != null && realEstate.getImages().size() > 0) {
                    // TODO: keep bitmap persisted, do not download twice the same image
                    new DownloadImageTask(realEstateHolder.image)
                            .execute(realEstate.getImages().get(0));
                } else {
                    realEstateHolder.image.setImageBitmap(null);
                }
                break;

            case TYPE_ADVERTISEMENT:
                AdvertisementHolder advertisementHolder = (AdvertisementHolder) holder;
                Advertisement advertisement = (Advertisement) mListItems.get(position);

                if(advertisement.getTitle() != null) {
                    advertisementHolder.title.setText(advertisement.getTitle());
                } else {
                    advertisementHolder.title.setText(R.string.unknown_title);
                }
                //
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }

    private class RealEstateHolder extends RecyclerView.ViewHolder {
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

    private class AdvertisementHolder extends RecyclerView.ViewHolder {
        TextView title;

        AdvertisementHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
        }
    }
}