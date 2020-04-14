package com.offgrid.coupler.core.holder;

import android.view.View;
import android.widget.TextView;

import com.offgrid.coupler.R;

public class PlaceDetailsViewHolder {

    private final TextView placeName;
    private final TextView placeInfo;
    private final TextView placeLocation;
    private final TextView walkTime;
    private final TextView walkDistance;

    public PlaceDetailsViewHolder(View itemView) {
        placeName = itemView.findViewById(R.id.place_name);
        placeInfo = itemView.findViewById(R.id.place_info);
        placeLocation = itemView.findViewById(R.id.place_location);
        walkTime = itemView.findViewById(R.id.walk_time);
        walkDistance = itemView.findViewById(R.id.walk_distance);
    }

}
