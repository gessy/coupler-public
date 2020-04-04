package com.offgrid.coupler.core.model.dto;

import android.os.Bundle;

import com.google.gson.Gson;
import com.offgrid.coupler.core.model.SourceActivity;


public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
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
        dto.id = bundle.getLong("id");
        dto.firstName = bundle.getString("firstName");
        dto.lastName = bundle.getString("lastName");
        dto.gid = bundle.getString("gid");

        dto.sourceActivity = bundle.containsKey("sourceActivity")
                ? SourceActivity.valueOf(bundle.getString("sourceActivity"))
                : SourceActivity.NOT_DEFINED;

        dto.sourceDto = bundle.containsKey("sourceDto")
                ? bundle.getString("sourceDto")
                : null;

        return dto;
    }


    public static class BundleBuilder {
        private Long id = 0L;
        private String firstName = "";
        private String lastName = "";
        private String gid = "";
        private SourceActivity sourceActivity = SourceActivity.NOT_DEFINED;
        private String sourceDto = null;

        public BundleBuilder() {
        }

        public UserDto.BundleBuilder withId(Long id) {
            this.id = id;
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


        public Bundle build() {
            Bundle bundle = new Bundle();
            bundle.putLong("id", id);
            bundle.putString("firstName", firstName);
            bundle.putString("lastName", lastName);
            bundle.putString("gid", gid);

            if (!SourceActivity.NOT_DEFINED.equals(sourceActivity)) {
                bundle.putString("sourceActivity", sourceActivity.toString());
            }

            if (sourceDto != null) {
                bundle.putString("sourceDto", sourceDto);
            }

            return bundle;
        }
    }
}
