package com.offgrid.coupler.core.model.dto;

import android.os.Bundle;

import com.google.gson.Gson;
import com.mapbox.geojson.Feature;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.offgrid.coupler.core.model.SourceActivity;

import static com.offgrid.coupler.core.model.Constants.*;


public class UserDto {
    private Long id;
    private String firstName;
    private String fullName;
    private String lastName;
    private LatLng location;
    private String gid;
    private SourceActivity sourceActivity;
    private String sourceDto;

    private UserDto() {
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public LatLng getLocation() {
        return location;
    }

    public String getGid() {
        return gid;
    }

    public SourceActivity getSourceActivity() {
        return sourceActivity;
    }

    public <T extends Object> T getSourceDto(Class<T> modelClass) {
        return sourceDto != null ? new Gson().fromJson(sourceDto, modelClass) : null;
    }

    public static UserDto getInstance(Bundle bundle) {
        UserDto dto = new UserDto();
        dto.id = bundle.getLong(KEY_CONTACT_ID);
        dto.fullName = bundle.getString(KEY_CONTACT_FULL_NAME);
        dto.firstName = bundle.getString(KEY_CONTACT_FIRST_NAME);
        dto.lastName = bundle.getString(KEY_CONTACT_LAST_NAME);
        dto.gid = bundle.getString(KEY_CONTACT_GID);

        if (bundle.containsKey(KEY_CONTACT_LATITUDE) && bundle.containsKey(KEY_CONTACT_LONGITUDE)) {
            dto.location = new LatLng(bundle.getDouble(KEY_CONTACT_LATITUDE), bundle.getDouble(KEY_CONTACT_LONGITUDE));
        }

        dto.sourceActivity = bundle.containsKey(KEY_CONTACT_SOURCE_ACTIVITY)
                ? SourceActivity.valueOf(bundle.getString(KEY_CONTACT_SOURCE_ACTIVITY))
                : SourceActivity.NOT_DEFINED;

        dto.sourceDto = bundle.containsKey(KEY_CONTACT_SOURCE_DTO)
                ? bundle.getString(KEY_CONTACT_SOURCE_DTO)
                : null;

        return dto;
    }


    public static UserDto getInstance(Feature feature) {
        UserDto dto = new UserDto();

        dto.id = feature.getNumberProperty(KEY_CONTACT_ID).longValue();
        dto.fullName = feature.getStringProperty(KEY_CONTACT_FULL_NAME);
        dto.firstName = feature.getStringProperty(KEY_CONTACT_FIRST_NAME);
        dto.lastName = feature.getStringProperty(KEY_CONTACT_LAST_NAME);
        dto.gid = feature.getStringProperty(KEY_CONTACT_GID);

        dto.location = new LatLng(
                feature.getNumberProperty(KEY_CONTACT_LATITUDE).doubleValue(),
                feature.getNumberProperty(KEY_CONTACT_LONGITUDE).doubleValue()
        );

        return dto;
    }


    public static class BundleBuilder {
        private Long id = 0L;
        private String fullName = "";
        private String firstName = "";
        private String lastName = "";
        private String gid = "";
        private LatLng location = null;
        private SourceActivity sourceActivity = SourceActivity.NOT_DEFINED;
        private String sourceDto = null;

        public BundleBuilder() {
        }

        public UserDto.BundleBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public UserDto.BundleBuilder withFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public UserDto.BundleBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserDto.BundleBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserDto.BundleBuilder withGid(String gid) {
            this.gid = gid;
            return this;
        }

        public UserDto.BundleBuilder withSourceActivity(SourceActivity sourceActivity) {
            this.sourceActivity = sourceActivity;
            return this;
        }

        public UserDto.BundleBuilder withSourceDto(Object source) {
            this.sourceDto = new Gson().toJson(source);
            return this;
        }


        public UserDto.BundleBuilder withLocation(LatLng location) {
            this.location = location;
            return this;
        }


        public Bundle build() {
            Bundle bundle = new Bundle();
            bundle.putLong(KEY_CONTACT_ID, id);
            bundle.putString(KEY_CONTACT_FULL_NAME, fullName);
            bundle.putString(KEY_CONTACT_FIRST_NAME, firstName);
            bundle.putString(KEY_CONTACT_LAST_NAME, lastName);
            bundle.putString(KEY_CONTACT_GID, gid);

            if (location != null) {
                bundle.putDouble(KEY_CONTACT_LATITUDE, location.getLatitude());
                bundle.putDouble(KEY_CONTACT_LONGITUDE, location.getLongitude());
            }

            if (!SourceActivity.NOT_DEFINED.equals(sourceActivity)) {
                bundle.putString(KEY_CONTACT_SOURCE_ACTIVITY, sourceActivity.toString());
            }

            if (sourceDto != null) {
                bundle.putString(KEY_CONTACT_SOURCE_DTO, sourceDto);
            }

            return bundle;
        }
    }
}
