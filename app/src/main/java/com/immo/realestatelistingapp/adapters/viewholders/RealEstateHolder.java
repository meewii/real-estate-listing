package com.immo.realestatelistingapp.adapters.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.immo.realestatelistingapp.R;
import com.immo.realestatelistingapp.models.RealEstate;
import com.squareup.picasso.Picasso;

public class RealEstateHolder extends RecyclerView.ViewHolder {

    private TextView mTitleView, mPriceView, mAddressView;
    private ImageView mImageView;

    public RealEstateHolder(View view) {
        super(view);
        mTitleView = (TextView) view.findViewById(R.id.title);
        mPriceView = (TextView) view.findViewById(R.id.price);
        mAddressView = (TextView) view.findViewById(R.id.address);
        mImageView = (ImageView) view.findViewById(R.id.image);
    }

    public void bindToRealEstate(
            Context context,
            RealEstate realEstate,
            View.OnClickListener clickListener) {

        if(realEstate.getTitle() != null) {
            mTitleView.setText(realEstate.getTitle());
        } else {
            mTitleView.setText(R.string.unknown_title);
        }

        if(realEstate.getPrice() > 0.0) {
            String price = String.valueOf(realEstate.getPrice()) + "€";

            mPriceView.setText(price);
            mPriceView.setVisibility(View.VISIBLE);
        } else {
            mPriceView.setVisibility(View.GONE);
        }

        if(realEstate.getLocation() != null && realEstate.getLocation().getAddress() != null) {
            mAddressView.setText(realEstate.getLocation().getAddress());
            mAddressView.setVisibility(View.VISIBLE);
        } else {
            mAddressView.setVisibility(View.GONE);
        }

        if(realEstate.getImages() != null && realEstate.getImages().size() > 0) {
            Picasso.with(context).load(realEstate.getImages().get(0)).into(mImageView);
        } else {
            mImageView.setImageBitmap(null);
            mImageView.setBackground(context.getDrawable(R.drawable.ic_landscape));
        }

        mImageView.setOnClickListener(clickListener);
    }

}
