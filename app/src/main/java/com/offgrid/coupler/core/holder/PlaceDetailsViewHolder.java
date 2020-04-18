package com.offgrid.coupler.core.holder;

import android.view.View;
import android.widget.TextView;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.offgrid.coupler.R;
import com.offgrid.coupler.data.entity.Place;
import com.offgrid.coupler.data.entity.Placelist;

public class PlaceDetailsViewHolder {

    private final TextView placeName;
    private final TextView placeInfo;
    private final TextView placeLocation;
    private final TextView walkTime;
    private final TextView walkDistance;

    private LatLng location;
    private Placelist placelist;

    public PlaceDetailsViewHolder(View itemView) {
        placeName = itemView.findViewById(R.id.place_name);
        placeInfo = itemView.findViewById(R.id.place_info);
        placeLocation = itemView.findViewById(R.id.place_location);
        walkTime = itemView.findViewById(R.id.walk_time);
        walkDistance = itemView.findViewById(R.id.walk_distance);
    }

    public void cleanUp() {
        placeName.setVisibility(View.INVISIBLE);
        placeName.setText("");

        placeInfo.setVisibility(View.INVISIBLE);
        placeInfo.setText("");

        placeLocation.setText("");
    }


    public String getPlaceName() {
        return placeName.getText().toString();
    }

    public void setPlaceName(String placeName) {
        this.placeName.setText(placeName);
        this.placeName.setVisibility(View.VISIBLE);
    }

    public String getPlaceInfo() {
        return placeInfo.getText().toString();
    }

    public LatLng getPlaceLocation() {
        return location;
    }

    public void setPlaceLocation(LatLng location) {
        this.placeLocation.setText(String.format("%.6f %.6f", location.getLatitude(), location.getLongitude()));
        this.location = location;
    }

    public Placelist getPlacelist() {
        return placelist;
    }

    public void setPlacelist(Placelist placelist) {
        this.placelist = placelist;
    }

    public Place get() {
        return new Place(
                placeName.getText().toString(),
                placeInfo.getText().toString(),
                location.getLatitude(),
                location.getLongitude(),
                placelist != null ? placelist.getId() : null
        );
    }


}
