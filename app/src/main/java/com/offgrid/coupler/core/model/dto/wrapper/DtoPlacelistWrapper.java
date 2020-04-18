package com.offgrid.coupler.core.model.dto.wrapper;

import android.os.Bundle;

import com.offgrid.coupler.core.model.dto.PlacelistDto;
import com.offgrid.coupler.data.entity.Placelist;

public class DtoPlacelistWrapper {

    public static Bundle convertAndWrap(Placelist placelist) {
        return new PlacelistDto
                .BundleBuilder()
                .withId(placelist.getId())
                .withName(placelist.getName())
                .build();
    }
}
