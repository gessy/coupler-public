package com.offgrid.coupler.core.model.dto;


import com.mapbox.geojson.Feature;
import com.mapbox.mapboxsdk.geometry.LatLng;

import static com.offgrid.coupler.core.model.Constants.KEY_PLACELIST_ID;
import static com.offgrid.coupler.core.model.Constants.KEY_PLACE_ID;
import static com.offgrid.coupler.core.model.Constants.KEY_PLACE_LATITUDE;
import static com.offgrid.coupler.core.model.Constants.KEY_PLACE_LONGITUDE;
import static com.offgrid.coupler.core.model.Constants.KEY_PLACE_NAME;

public class PlaceDto {
    private Long id;
    private String name;
    private String info;
    private LatLng location;
    private Long placelistId;


    private PlaceDto() {
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public LatLng getLocation() {
        return location;
    }

    public Long getPlacelistId() {
        return placelistId;
    }


    public static PlaceDto getInstance(Feature feature) {
        PlaceDto dto = new PlaceDto();

        dto.id = feature.getNumberProperty(KEY_PLACE_ID).longValue();
        dto.name = feature.getStringProperty(KEY_PLACE_NAME);
        dto.placelistId = feature.getNumberProperty(KEY_PLACELIST_ID).longValue();

        dto.location = new LatLng(
                feature.getNumberProperty(KEY_PLACE_LATITUDE).doubleValue(),
                feature.getNumberProperty(KEY_PLACE_LONGITUDE).doubleValue()
        );

        return dto;
    }


}
