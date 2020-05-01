package com.offgrid.coupler.core.model.command;

import com.mapbox.mapboxsdk.geometry.LatLng;

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
}
