package com.offgrid.coupler.core.model.dto;

import android.os.Bundle;

import com.mapbox.mapboxsdk.geometry.LatLng;

import static com.offgrid.coupler.core.model.Constants.KEY_REGION_ID;
import static com.offgrid.coupler.core.model.Constants.KEY_REGION_MAXZOOM;
import static com.offgrid.coupler.core.model.Constants.KEY_REGION_MINZOOM;
import static com.offgrid.coupler.core.model.Constants.KEY_REGION_NAME;
import static com.offgrid.coupler.core.model.Constants.KEY_REGION_NORTHEAST_LATITUDE;
import static com.offgrid.coupler.core.model.Constants.KEY_REGION_NORTHEAST_LONGITUDE;
import static com.offgrid.coupler.core.model.Constants.KEY_REGION_SOUTHWEST_LATITUDE;
import static com.offgrid.coupler.core.model.Constants.KEY_REGION_SOUTHWEST_LONGITUDE;

public class RegionDto {
    private Long id;
    private String name;
    private LatLng northEast;
    private LatLng southWest;
    private int minZoom;
    private int maxZoom;


    private RegionDto() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public LatLng getNorthEast() {
        return northEast;
    }

    public LatLng getSouthWest() {
        return southWest;
    }

    public int getMinZoom() {
        return minZoom;
    }

    public int getMaxZoom() {
        return maxZoom;
    }

    public static RegionDto getInstance(Bundle bundle) {
        RegionDto dto = new RegionDto();

        dto.id = bundle.containsKey(KEY_REGION_ID) ? bundle.getLong(KEY_REGION_ID) : null;
        dto.name = bundle.getString(KEY_REGION_NAME);

        dto.minZoom = bundle.getInt(KEY_REGION_MINZOOM);
        dto.maxZoom = bundle.getInt(KEY_REGION_MAXZOOM);

        dto.northEast = new LatLng(
                bundle.getDouble(KEY_REGION_NORTHEAST_LATITUDE),
                bundle.getDouble(KEY_REGION_NORTHEAST_LONGITUDE)
        );

        dto.southWest = new LatLng(
                bundle.getDouble(KEY_REGION_SOUTHWEST_LATITUDE),
                bundle.getDouble(KEY_REGION_SOUTHWEST_LONGITUDE)
        );

        return dto;
    }


    public static class BundleBuilder {
        private Long id;
        private String name;
        private LatLng northEast;
        private LatLng southWest;
        private int minZoom;
        private int maxZoom;

        public BundleBuilder() {
        }

        public RegionDto.BundleBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public RegionDto.BundleBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public RegionDto.BundleBuilder withNorthEast(LatLng northEast) {
            this.northEast = northEast;
            return this;
        }

        public RegionDto.BundleBuilder withSouthWest(LatLng southWest) {
            this.southWest = southWest;
            return this;
        }

        public RegionDto.BundleBuilder withMinZoom(int minZoom) {
            this.minZoom = minZoom;
            return this;
        }

        public RegionDto.BundleBuilder withMaxZoom(int maxZoom) {
            this.maxZoom = maxZoom;
            return this;
        }


        public Bundle build() {
            Bundle bundle = new Bundle();
            if (id != null) {
                bundle.putLong(KEY_REGION_ID, id);
            }

            bundle.putString(KEY_REGION_NAME, name);

            bundle.putDouble(KEY_REGION_NORTHEAST_LATITUDE, northEast.getLatitude());
            bundle.putDouble(KEY_REGION_NORTHEAST_LONGITUDE, northEast.getLongitude());

            bundle.putDouble(KEY_REGION_SOUTHWEST_LATITUDE, southWest.getLatitude());
            bundle.putDouble(KEY_REGION_SOUTHWEST_LONGITUDE, southWest.getLongitude());

            bundle.putInt(KEY_REGION_MINZOOM, minZoom);
            bundle.putInt(KEY_REGION_MAXZOOM, maxZoom);

            return bundle;
        }
    }
}
