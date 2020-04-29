package com.offgrid.coupler.core.model.dto;

import android.os.Bundle;

import static com.offgrid.coupler.core.model.Constants.KEY_COUNTRY_ID;
import static com.offgrid.coupler.core.model.Constants.KEY_COUNTRY_NAME;

public class CountryDto {
    private Long id;
    private String name;

    private CountryDto() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static CountryDto getInstance(Bundle bundle) {
        CountryDto dto = new CountryDto();
        dto.id =  bundle.containsKey(KEY_COUNTRY_ID) ? bundle.getLong(KEY_COUNTRY_ID) : null;
        dto.name = bundle.getString(KEY_COUNTRY_NAME);
        return dto;
    }


    public static class BundleBuilder {
        private Long id;
        private String name;

        public BundleBuilder() {
        }

        public CountryDto.BundleBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public CountryDto.BundleBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public Bundle build() {
            Bundle bundle = new Bundle();
            if (id != null) {
                bundle.putLong(KEY_COUNTRY_ID, id);
            }

            bundle.putString(KEY_COUNTRY_NAME, name);
            return bundle;
        }
    }
}
