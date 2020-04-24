package com.offgrid.coupler.core.holder;

import android.view.View;
import android.widget.TextView;

import com.mapbox.geojson.Feature;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.offgrid.coupler.R;
import com.offgrid.coupler.core.model.dto.UserDto;

public class ContactDetailsViewHolder {

    private final TextView fullName;
    private final TextView gid;
    private final TextView location;
    private final TextView walkTime;
    private final TextView walkDistance;

    private UserDto userDto;


    public ContactDetailsViewHolder(View itemView) {
        fullName = itemView.findViewById(R.id.contact_full_name);
        gid = itemView.findViewById(R.id.contact_gid);
        location = itemView.findViewById(R.id.contact_location);
        walkTime = itemView.findViewById(R.id.walk_time);
        walkDistance = itemView.findViewById(R.id.walk_distance);
    }

    public void update(UserDto dto) {
        userDto = dto;

        fullName.setText(dto.getFullName());
        gid.setText(dto.getGid());

        LatLng latLng = dto.getLocation();
        location.setText(String.format("%.6f %.6f", latLng.getLatitude(), latLng.getLongitude()));
    }

    public UserDto get() {
        return userDto;
    }


    public void update(Feature feature) {
        update(UserDto.getInstance(feature));
    }

    public void gidVisibility(int visibility) {
        gid.setVisibility(visibility);
    }
}
