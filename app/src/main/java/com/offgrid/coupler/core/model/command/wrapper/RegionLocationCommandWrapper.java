package com.offgrid.coupler.core.model.command.wrapper;

import android.os.Bundle;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.offgrid.coupler.core.model.command.RegionLocationCommand;
import com.offgrid.coupler.data.entity.Region;

public class RegionLocationCommandWrapper {

    public static Bundle convertAndWrap(Region region) {
        int zoom = region.getMinZoom() / 4 + 3 * region.getMaxZoom() / 4;
        LatLng location = new LatLngBounds.Builder()
                .include(region.getNorthEastLocation())
                .include(region.getSouthWestLocation())
                .build()
                .getCenter();

        return new RegionLocationCommand.BundleBuilder()
                .withZoom(zoom)
                .withLocation(location)
                .build();
    }

}
