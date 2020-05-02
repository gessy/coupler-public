package com.offgrid.coupler.core.model.command.wrapper;

import android.os.Bundle;

import com.offgrid.coupler.core.model.command.RegionLocationCommand;
import com.offgrid.coupler.data.entity.Region;

public class RegionLocationCommandWrapper {

    public static Bundle convertAndWrap(Region region) {
        int zoom = region.getMinZoom() / 4 + 3 * region.getMaxZoom() / 4;

        return new RegionLocationCommand.BundleBuilder()
                .withZoom(zoom)
                .withLocation(region.getCenter())
                .build();
    }

}
