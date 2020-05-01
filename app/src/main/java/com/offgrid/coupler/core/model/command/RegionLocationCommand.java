package com.offgrid.coupler.core.model.command;

import android.os.Bundle;

import com.mapbox.mapboxsdk.geometry.LatLng;

import static com.offgrid.coupler.core.model.command.Constant.KEY_REGION_LOCATION_COMMAND_LATITUDE;
import static com.offgrid.coupler.core.model.command.Constant.KEY_REGION_LOCATION_COMMAND_LONGITUDE;
import static com.offgrid.coupler.core.model.command.Constant.KEY_REGION_LOCATION_COMMAND_ZOOM;

public class RegionLocationCommand extends BaseCommand {

    private LatLng location;
    private int zoom;

    public RegionLocationCommand() {
        super(BaseCommand.REGION_LOCATION);
    }

    public RegionLocationCommand(LatLng location, int zoom) {
        this();
        this.location = location;
        this.zoom = zoom;
    }

    public LatLng getLocation() {
        return location;
    }

    public int getZoom() {
        return zoom;
    }

    public static RegionLocationCommand getInstance(Bundle bundle) {
        LatLng location = new LatLng(
                bundle.getDouble(KEY_REGION_LOCATION_COMMAND_LATITUDE),
                bundle.getDouble(KEY_REGION_LOCATION_COMMAND_LONGITUDE)
        );
        int zoom = bundle.getInt(KEY_REGION_LOCATION_COMMAND_ZOOM);

        return new RegionLocationCommand(location, zoom);
    }


    public static class BundleBuilder {
        private LatLng location;
        private int zoom;

        public BundleBuilder() {
        }

        public BundleBuilder withLocation(LatLng location) {
            this.location = location;
            return this;
        }

        public BundleBuilder withZoom(int zoom) {
            this.zoom = zoom;
            return this;
        }

        public Bundle build() {
            Bundle bundle = new Bundle();

            bundle.putDouble(KEY_REGION_LOCATION_COMMAND_LATITUDE, location.getLatitude());
            bundle.putDouble(KEY_REGION_LOCATION_COMMAND_LONGITUDE, location.getLongitude());
            bundle.putInt(KEY_REGION_LOCATION_COMMAND_ZOOM, zoom);

            return bundle;
        }
    }

}
