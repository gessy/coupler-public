package com.offgrid.coupler.core.holder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.offgrid.coupler.R;
import com.offgrid.coupler.data.entity.Place;

public class PlaceItemViewHolder extends RecyclerView.ViewHolder {

    private final TextView placeName;
    private final TextView placeInfo;
    private final TextView distance;

    public PlaceItemViewHolder(View itemView) {
        super(itemView);
        placeName = itemView.findViewById(R.id.place_name);
        placeInfo = itemView.findViewById(R.id.place_info);
        distance = itemView.findViewById(R.id.distance);
    }

    public void update(Place place) {
        placeName.setText(place.getName());
        placeInfo.setText(place.getInfo());
        distance.setText("1.6 km");
    }

}
