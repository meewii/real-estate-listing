package com.immo.realestatelistingapp.adapters.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.immo.realestatelistingapp.R;
import com.immo.realestatelistingapp.models.Advertisement;

public class AdvertHolder extends RecyclerView.ViewHolder {

    private TextView mTitleView;

    public AdvertHolder(View view) {
        super(view);
        mTitleView = (TextView) view.findViewById(R.id.title);
    }

    public void bindToAdvert(Context context, Advertisement advertisement) {

        if(advertisement.getTitle() != null) {
            mTitleView.setText(advertisement.getTitle());
        } else {
            mTitleView.setText(R.string.unknown_title);
        }
    }

}