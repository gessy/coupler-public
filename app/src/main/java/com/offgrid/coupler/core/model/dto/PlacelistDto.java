package com.offgrid.coupler.core.model.dto;

import android.os.Bundle;

import static com.offgrid.coupler.core.model.Constants.KEY_PLACELIST_ID;
import static com.offgrid.coupler.core.model.Constants.KEY_PLACELIST_NAME;

public class PlacelistDto {
    private Long id;
    private String name;

    private PlacelistDto() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static PlacelistDto getInstance(Bundle bundle) {
        PlacelistDto dto = new PlacelistDto();
        dto.id =  bundle.containsKey(KEY_PLACELIST_ID) ? bundle.getLong(KEY_PLACELIST_ID) : null;
        dto.name = bundle.getString(KEY_PLACELIST_NAME);
        return dto;
    }


    public static class BundleBuilder {
        private Long id;
        private String name;

        public BundleBuilder() {
        }

        public PlacelistDto.BundleBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public PlacelistDto.BundleBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public Bundle build() {
            Bundle bundle = new Bundle();
            if (id != null) {
                bundle.putLong(KEY_PLACELIST_ID, id);
            }

            bundle.putString(KEY_PLACELIST_NAME, name);
            return bundle;
        }
    }
}
