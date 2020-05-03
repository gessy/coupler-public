package com.offgrid.coupler.core.model.dto.wrapper;

import android.os.Bundle;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.offgrid.coupler.core.model.dto.RegionDto;
import com.offgrid.coupler.data.entity.Region;

public class DtoRegionWrapper {

    public static Bundle convertAndWrap(Region region) {
        return new RegionDto
                .BundleBuilder()
                .withId(region.getId())
                .withName(region.getName())
                .withMaxZoom(region.getMaxZoom())
                .withMinZoom(region.getMinZoom())
                .withNorthEast(new LatLng(region.getNorthEastLatitude(), region.getNorthEastLongitude()))
                .withSouthWest(new LatLng(region.getSouthWestLatitude(), region.getSouthWestLongitude()))
                .build();
    }
}
