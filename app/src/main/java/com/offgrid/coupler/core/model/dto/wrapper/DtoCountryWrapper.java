package com.offgrid.coupler.core.model.dto.wrapper;

import android.os.Bundle;

import com.offgrid.coupler.core.model.dto.CountryDto;
import com.offgrid.coupler.data.entity.Country;

public class DtoCountryWrapper {

    public static Bundle convertAndWrap(Country country) {
        return new CountryDto
                .BundleBuilder()
                .withId(country.getId())
                .withName(country.getName())
                .build();
    }
}
