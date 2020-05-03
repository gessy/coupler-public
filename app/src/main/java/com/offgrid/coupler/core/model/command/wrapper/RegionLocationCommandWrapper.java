package com.offgrid.coupler.core.model.command.wrapper;

import android.os.Bundle;

import com.offgrid.coupler.core.model.command.RegionLocationCommand;
import com.offgrid.coupler.data.entity.Region;

public class RegionLocationCommandWrapper {

    public static Bundle convertAndWrap(Region region) {
        return new RegionLocationCommand.BundleBuilder()
                .withZoom(region.getTargetZoom())
                .withLocation(region.getCenter())
                .build();
    }

}
